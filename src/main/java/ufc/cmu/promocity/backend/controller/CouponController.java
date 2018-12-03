package ufc.cmu.promocity.backend.controller;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.User;
import ufc.cmu.promocity.backend.service.CouponsService;
import ufc.cmu.promocity.backend.service.UsersService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Coupons Controller
 * @author armandosoaressousa
 *
 */
@Component
@Path("/coupons")
@Transactional
public class CouponController {
	private CouponsService couponService;
	private UsersService userService;
	
	@Autowired
	public void setCouponService(CouponsService couponServices){
		this.couponService = couponServices;
	}
	
	@Autowired
	public void setUsersService(UsersService userService) {
		this.userService = userService;
	}
	
	/**
	 * Contrutor of couponController
	 * @param couponService
	 */
	public CouponController() {
	}

	 /**
     * Retorna em um JSON todos os usuarios cadastrados
     * @return código http
     */
    @GET
    @Produces("application/json")
    public List<Coupon> getAllCoupons() {
       	List<Coupon> listCoupon = new LinkedList<Coupon>();
    	listCoupon = couponService.getListAll();
    	return listCoupon;
    }
    
    /**
     * Dado um id retorna o JSON dos dados do usuario
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Coupon getCoupon(@PathParam("id") String id) {
    	return couponService.get(Long.parseLong(id));
    }
    
    /**
     * Dados os dados de um usuario adiciona um usuario no repositorio
     * @param coupon
     * @return código http
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addCoupon(Coupon coupon) {
        couponService.save(coupon);
        coupon.updateQrCode();
        couponService.save(coupon);
        
        URI uri = URI.create("/" + String.valueOf(coupon.getId()));
		return Response.created(uri).build();
    }
    
    /**
     * Dado um id e os dados do coupon faz sua atualizacao
     * @param id
     * @param coupon
     * @return código http
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    public Response updateCoupon(@PathParam("id") String id, Coupon coupon) {
       couponService.save(coupon);
       return Response.noContent().build();
    }
    
    /**
     * Dado um id de um usuario faz sua remocao do repositorio
     * @param id
     * @return código http
     */
    @DELETE
    @Path("/{id}")
    public Response deletecoupon(@PathParam("id") String id) {
        couponService.delete(Long.parseLong(id));
        return Response.ok().build();
    }
    	
	/**
     * Obtem a imagem do qrcode através de um serviço externo.
     * https://api.qrserver.com/v1/create-qr-code/?size=150x150&data={qrCode}
     * 
     * @param idCoupon
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{idCoupon}/qrCodeImage")
    public Response getCouponQrCodeImage(@PathParam("idCoupon") String idCoupon) {
    	Coupon coupon = couponService.get(Long.parseLong(idCoupon));
    	
    	String address = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + coupon.getQrCode();
    	URI uri;
		try {
			uri = new URI(address);
			return Response.temporaryRedirect(uri).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	
		return Response.ok("Não foi possivel obter o QRCode do cupom.").build();
    }
    
    /**
     * Marca um cupom simples como ativado.
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{idCoupon}/activate")
    public Response activateCoupon(@PathParam("idCoupon") String idCoupon) {
    	Coupon coupon = couponService.get(Long.parseLong(idCoupon));
    	
    	if(coupon.isRequiredCoUsers()) {
    		System.out.println("Esse cupom necessita de " + coupon.getNumActivedCoUsers() + " usuários para ativar.");
    		return Response.ok("Esse cupom necessita de " + coupon.getNumActivedCoUsers() + " usuários para ativar.").build();
    	}
    	
    	if(coupon.isActivated()) {
    		System.out.println("Cupom já foi ativado.");
    		return Response.ok("Cupom já foi ativado.").build();
    	}
    	else {
    		coupon.setActivated(true);
    		couponService.save(coupon);
    		return Response.ok("Cupom ativado com sucesso.").build();
    	}	
    }
    
    /**
     * Marca um cupom coletivo como ativado.
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{idCoupon}/activateByUserFriend/{idUser}")
    public Response activateFriendCoupon(@PathParam("idCoupon") String idCoupon, @PathParam("idUser") String idUser) {
    	Coupon coupon = couponService.get(Long.parseLong(idCoupon));
    	
    	User user = userService.get(Long.parseLong(idUser));
    	List<Coupon> couponsUserPromotion = couponService.findByUserAndPromotion(user, coupon.getPromotion());
    	
    	if(!coupon.isRequiredCoUsers()) {
    		System.out.println("Esse cupom não necessita de outros usuários para ativar.");
    		return Response.ok("Esse cupom não necessita de outros usuários para ativar.").build();
    	}
    	
    	if(coupon.getUser().getId() == user.getId()) {
    		System.out.println("Esse tipo de cupom não ser ativado pelo proprietário.");
    		return Response.ok("Esse tipo de cupom não ser ativado pelo proprietário.").build();
    	}
    	
    	if(!user.alreadyFriend(coupon.getUser())) {
    		System.out.println("O proprietário desse cupom não é seu amigo.");
    		return Response.ok("O proprietário desse cupom não é seu amigo.").build();
    	}
    	
    	if(couponsUserPromotion.isEmpty()) {
    		System.out.println("O usuário não possui um cupom dessa mesma promoção.");
    		return Response.ok("O usuário não possui um cupom dessa mesma promoção.").build();
    	}
    	
    	if(coupon.isActivated()) {
    		System.out.println("Cupom já foi ativado.");
    		return Response.ok("Cupom já foi ativado.").build();
    	}
    	else {
    		coupon.setNumActivedCoUsers(coupon.getNumActivedCoUsers() + 1);
    		if(coupon.getNumActivedCoUsers() >= coupon.getNumRequiredCoUsers())
    			coupon.setActivated(true);
    		couponService.save(coupon);
    		
    		Coupon sameCouponUser = couponsUserPromotion.get(0);
    		sameCouponUser.setNumActivedCoUsers(sameCouponUser.getNumActivedCoUsers() + 1);
    		if(sameCouponUser.getNumActivedCoUsers() >= sameCouponUser.getNumRequiredCoUsers())
    			sameCouponUser.setActivated(true);
    		couponService.save(sameCouponUser);
    		
    		return Response.ok("Cupom ativado com sucesso.").build();
    	}	
    }
    
    /**
     * Marca um cupom coletivo como ativado.
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/qrCode/{qrCode}/activateByUserFriend/{idUser}")
    public Response activateFriendCouponByQrcode(@PathParam("qrCode") String qrCode, @PathParam("idUser") String idUser) {
    	Coupon coupon = couponService.findByQrCode(qrCode);
    	
    	User user = userService.get(Long.parseLong(idUser));
    	List<Coupon> couponsUserPromotion = couponService.findByUserAndPromotion(user, coupon.getPromotion());
    	
    	if(!coupon.isRequiredCoUsers()) {
    		System.out.println("Esse cupom não necessita de outros usuários para ativar.");
    		return Response.ok("Esse cupom não necessita de outros usuários para ativar.").build();
    	}
    	
    	if(coupon.getUser().getId() == user.getId()) {
    		System.out.println("Esse tipo de cupom não ser ativado pelo proprietário.");
    		return Response.ok("Esse tipo de cupom não ser ativado pelo proprietário.").build();
    	}
    	
    	if(!user.alreadyFriend(coupon.getUser())) {
    		System.out.println("O proprietário desse cupom não é seu amigo.");
    		return Response.ok("O proprietário desse cupom não é seu amigo.").build();
    	}
    	
    	if(couponsUserPromotion.isEmpty()) {
    		System.out.println("O usuário não possui um cupom dessa mesma promoção.");
    		return Response.ok("O usuário não possui um cupom dessa mesma promoção.").build();
    	}
    	
    	if(coupon.isActivated()) {
    		System.out.println("Cupom já foi ativado.");
    		return Response.ok("Cupom já foi ativado.").build();
    	}
    	else {
    		coupon.setNumActivedCoUsers(coupon.getNumActivedCoUsers() + 1);
    		if(coupon.getNumActivedCoUsers() >= coupon.getNumRequiredCoUsers())
    			coupon.setActivated(true);
    		couponService.save(coupon);
    		
    		Coupon sameCouponUser = couponsUserPromotion.get(0);
    		sameCouponUser.setNumActivedCoUsers(sameCouponUser.getNumActivedCoUsers() + 1);
    		if(sameCouponUser.getNumActivedCoUsers() >= sameCouponUser.getNumRequiredCoUsers())
    			sameCouponUser.setActivated(true);
    		couponService.save(sameCouponUser);
    		
    		return Response.ok("Cupom ativado com sucesso.").build();
    	}	
    }
    
    /**
     * Marca um cupom como utilizado/consumido.
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{idCoupon}/consume")
    public Response consumeCoupon(@PathParam("idCoupon") String idCoupon) {
    	Coupon coupon = couponService.get(Long.parseLong(idCoupon));
    	if(coupon.isConsumed()) {
    		System.out.println("Cupom já foi consumido.");
    		return Response.notModified("Cupom já foi consumido.").build();
    	}
    	else {
    		coupon.setConsumed(true);
    		couponService.save(coupon);
    		return Response.noContent().build();
    	}
    }
    
}