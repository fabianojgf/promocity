package ufc.cmu.promocity.backend.controller;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.service.CouponsService;

import java.net.URI;
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
public class CouponController {
	private CouponsService couponService;
	
	@Autowired
	public void setCouponService(CouponsService couponServices){
		this.couponService = couponServices;
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
    
}