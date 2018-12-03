package ufc.cmu.promocity.backend.controller;

import java.net.URI;
import java.util.ArrayList;
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

import ufc.cmu.promocity.backend.context.PromotionArea;
import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;
import ufc.cmu.promocity.backend.report.ReportCoupon;
import ufc.cmu.promocity.backend.report.ReportPromotion;
import ufc.cmu.promocity.backend.service.CouponsService;
import ufc.cmu.promocity.backend.service.PromotionsService;
import ufc.cmu.promocity.backend.service.StoreService;
import ufc.cmu.promocity.backend.service.UsersService;
import ufc.cmu.promocity.backend.utils.Message;

/**
 * Stores Controller
 * @author armandosoaressousa
 */
@Component
@Path("/stores")
@Transactional
public class StoreController {
	private StoreService storeService;
	public PromotionArea globalPromotionArea;	
	private UsersService userService;
	private PromotionsService promotionService;
	private CouponsService couponService;
	
	@Autowired
	public void setPromotionService(PromotionsService promotionServices){
		this.promotionService = promotionServices;
	}
	
	@Autowired
	public void setCouponService(CouponsService couponServices){
		this.couponService = couponServices;
	}

	 @Autowired
     public void setUserService(UsersService userService) {	
     	this.userService = userService;		     	
     }		     
	
	/**
	 * Contrutor of StoreController
	 * @param StoreService
	 */
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
		this.globalPromotionArea = PromotionArea.getInstance();
	}

	 /**
     * Retorna em um JSON todos as lojas cadastrados
     * @return lista de lojas
     */
    @GET
    @Produces("application/json")
    public List<Store> getAllStores() {
        return storeService.getListAll();
    }
    
    /**
     * Dado um id de uma loja retorna em JSON dos dados da loja
     * @param id da loja
     * @return dados da loja
     */
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Store getStore(@PathParam("id") String id) {
    	return storeService.get(Long.parseLong(id));
    }
    
    /**
     * Dados os dados de uma loja faz seu registro no repositorio 
     * @param Store
     * @return codigo http
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addStore(Store Store) {
        storeService.save(Store);
        URI uri = URI.create("/" + String.valueOf(Store.getId()));
		return Response.created(uri).build();
    }
    
    /**
     * Dado um id e os dados de uma loja faz sua atualizacao no repositorio
     * @param id da loja
     * @param Store dados da loja
     * @return codigo http
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    public Response updateStore(@PathParam("id") String id, Store Store) {
        storeService.update(Store);
        return Response.noContent().build();
    }
    
    /**
     * Dado um id de uma loja faz sua remocao do repositorio
     * @param id da loja
     * @return codigo http
     */
    @DELETE
    @Path("/{id}")
    public Response deleteStore(@PathParam("id") String id) {
        storeService.delete(Long.parseLong(id));
        return Response.ok().build();
    }
    
    /**
     * Retorna em um JSON todos as lojas dentro do raio
     * @return lista de lojas
     */
    @GET
    @Produces("application/json")
    @Path("/latitude/{latitude}/longitude/{longitude}")
    public List<Store> getAllStoresInRadius(@PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
    	System.out.println("Location: " + latitude  + "," + longitude);
        return storeService.findInRadiusOnDatabase(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
    
    /**
     * Get all promotions from specific idStore
		stores/id/promotions
 
     * Retorna em um JSON todas as promocoes de uma dada lojas cadastrados
     * id da loja
     * @return lista de promocoes de uma dada loja
     */
    @GET
    @Produces("application/json")
    @Path("/{id}/promotions")
    public List<Promotion> getAllPromotionsfromStore(@PathParam("id") String id) {
        return storeService.get(Long.parseLong(id)).getPromotions();
    }

    /**
     * Get a specific idPromotion from specific idStore
	stores/id/promotions/id

     * Return data from promotion in store
     * @param idStore da loja
     * @param idPromotion da promocao
     * @return dados da promocao especifica
     */
    @GET
    @Produces("application/json")
    @Path("/{idStore}/promotions/{idPromotion}")
    public Promotion getPromotionFromStore(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion) {
    	Store store = storeService.get(Long.parseLong(idStore));
    	
    	for (Promotion element : store.getPromotions()) {
    		if (element.getId() == Long.parseLong(idPromotion)) {
    			return element;
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Get a specific idPromotion from specific idStore
	stores/id/reportpromotions/id

     * Return data from promotion in store
     * @param idStore da loja
     * @param idPromotion da promocao
     * @return dados da promocao especifica
     */
    @GET
    @Produces("application/json")
    @Path("/{idStore}/reportpromotions/{idPromotion}")
    public ReportPromotion getReportPromotionFromStore(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion) {    	    	
    	Store store = storeService.get(Long.parseLong(idStore));

    	for (Promotion element : store.getPromotions()) {
    		if (element.getId() == Long.parseLong(idPromotion)) {
    			ReportPromotion reportPromotion = new ReportPromotion(element, store);
    			return reportPromotion;
    		}
    	}
    	
    	return null;
    }

    
    /**
    Get all coupons from a specific idPromotion from specific idStore
    stores/id/promotions/id/coupons
	
     * Retorna em um JSON todos os cupons de uma data promocao em uma da loja
     * id da loja
     * id da promocao
     * @return lista de todos os cupons de uma promocao de uma loja
     */
    @GET
    @Produces("application/json")
    @Path("/{idStore}/promotions/{idPromotion}/coupons")
    public List<Coupon> getCouponsFromPromotionAndStore(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion) {
    	Store store = storeService.get(Long.parseLong(idStore));
    	Promotion promotion = null; 
    	
    	for (Promotion element : store.getPromotions()) {
    		if (element.getId() == Long.parseLong(idPromotion)) {
    			promotion = element;
    		}
    	}
    	
    	return promotion.getCoupons();
    }

    /**
    Get a specific coupon from a specific idPromotion and specific idStore
    stores/id/promotions/id/coupons/id
	
     * Retorna em um JSON todos os cupons de uma data promocao em uma da loja
     * id da loja
     * id da promoca
     * @return um cupom de uma dada promocao de uma dada loja
     */
    @GET
    @Produces("application/json")
    @Path("/{idStore}/promotions/{idPromotion}/coupons/{idCoupon}")
    public Coupon getCouponFromPromotionAndStore(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion, @PathParam("idCoupon") String idCoupon) {
    	Store store = storeService.get(Long.parseLong(idStore));
    	Promotion promotion = null; 
    	
    	for (Promotion element : store.getPromotions()) {
    		if (element.getId() == Long.parseLong(idPromotion)) {
    			promotion = element;
    		}
    	}
    	   	
    	for (Coupon element : promotion.getCoupons()) {
    		if (element.getId() == Long.parseLong(idCoupon)) {
    			return element;
    		}
    	}
    	
    	return null;
    }

    /**
    Get a specific coupon from a specific idPromotion and specific idStore
    stores/id/promotions/id/coupons/id
	
     * Retorna em um JSON todos os cupons de uma data promocao em uma da loja
     * id da loja
     * id da promoca
     * @return um cupom de uma dada promocao de uma dada loja
     */
    @GET
    @Produces("application/json")
    @Path("/{idStore}/promotions/{idPromotion}/reportcoupons/{idCoupon}")
    public ReportCoupon getReportCouponFromPromotionAndStore(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion, @PathParam("idCoupon") String idCoupon) {
    	Store store = storeService.get(Long.parseLong(idStore));
    	Promotion promotion = null; 
    	
    	for (Promotion element : store.getPromotions()) {
    		if (element.getId() == Long.parseLong(idPromotion)) {
    			promotion = element;
    		}
    	}
    	   	
    	for (Coupon element : promotion.getCoupons()) {
    		if (element.getId() == Long.parseLong(idCoupon)) {
    			ReportCoupon reportCoupon = new ReportCoupon(store, promotion, element);
    			return reportCoupon;
    		}
    	}
    	
    	return null;
    }
    
    @GET
    @Produces("application/json")
    @Path("/{idStore}/promotions/{idPromotion}/coupon/consume/{idCoupon}/users/{idUser}")   
    public Object consumeCoupon(@PathParam("idStore") String idStore, @PathParam("idPromotion") String idPromotion, 
    		@PathParam("idCoupon") String idCoupon, @PathParam("idUser") String idUser) {

    	Message message = new Message();
    	Store store = storeService.get(Long.parseLong(idStore));
    	//Promotion promotion = this.promotionService.get(Long.parseLong("idPromotion"));
    	Coupon coupon = couponService.get(Long.parseLong(idCoupon));
    	User user = userService.get(Long.parseLong(idUser));
    		
    	//checa se foi premiado por amigos
//    	if (coupon.isRequiredCoUsers()) {
//    		//checa se o amigo está dentro da lista de amigos premiados no cupom
//    		for (User usuarioPremiado : coupon.getUsers()) {
//    			if (usuarioPremiado.getId() == user.getId()) {
//    				//Pode dar o desconto
//    				message.setId(9);
//    				message.setConteudo("Cupom premiado por amigos!");
//    				coupon.incrementUse();
//    				couponService.update(coupon);
//    				return message;
//    			}
//    		}    		
//    	}
    	
    	//checa se o cupom é valido e a promocao ainda é válida...
    	if (coupon.isValidCoupon()) {
			//Pode dar o desconto
			message.setId(10);
			message.setConteudo("Este cupom tem desconto.");
			coupon.incrementUse();
			couponService.update(coupon);;
    	}else {
			message.setId(11);
			message.setConteudo("Este cupom não tem mais validade.");
    	}
    	return message;
    }
    
}