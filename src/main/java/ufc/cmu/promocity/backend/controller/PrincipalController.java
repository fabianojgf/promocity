package ufc.cmu.promocity.backend.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ufc.cmu.promocity.backend.report.ReportApplication;

/**
 * Principal Controller
 * Lista os serviços disponíveis na aplicação
 * @author armandosoaressousa
 *
 */
@Path("/")
public class PrincipalController {
	private ReportApplication listDetalhes = new ReportApplication();
	
	String u1 = "http://localhost:8082/promocity/users";
	String u2 = "http://localhost:8082/promocity/users/{idUser}";
	String u3 = "http://localhost:8082/promocity/users/{idUser}/coupons";
	String u4 = "http://localhost:8082/promocity/users/{idUser}/monitoring/location/{latitude}/{longitude}";
	String u5 = "http://localhost:8082/promocity/users/{email}/{senha}";
	String u6 = "http://localhost:8082/promocity/users/{idUser}/add/friend/{idFriend}";
	String u7 = "http://localhost:8082/promocity/users/{idUser}/list/friends";
	String u8 = "http://localhost:8082/promocity/users/{idUser}/delete/friend/{idFriend}";
	String u9 = "http://localhost:8082/promocity/users/{idUser}/activate/coupon/{idCoupon}/store/{idStore}/friends/{idFriend1}/{idFriend2}";
	
	String s1 = "http://localhost:8082/promocity/stores";
	String s2 = "http://localhost:8082/promocity/stores/{idStore}/promotions";
	String s3 = "http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}";
	String s4 = "http://localhost:8082/promocity/stores/{idStore}/reportpromotions/{idPromotion}";
	String s5 = "http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/coupons";
	String s6 = "http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/coupons/{idCoupon}";
	String s7 = "http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/reportcoupons/{idCoupon}";

	public PrincipalController() {		
		this.listDetalhes.addDetalhe(u1);
		this.listDetalhes.addDetalhe(u2);
		this.listDetalhes.addDetalhe(u3);
		this.listDetalhes.addDetalhe(u4);		
		this.listDetalhes.addDetalhe(u5);
		this.listDetalhes.addDetalhe(u6);
		this.listDetalhes.addDetalhe(u7);
		this.listDetalhes.addDetalhe(u8);
		this.listDetalhes.addDetalhe(u9);
		this.listDetalhes.addDetalhe(s1);
		this.listDetalhes.addDetalhe(s2);
		this.listDetalhes.addDetalhe(s3);
		this.listDetalhes.addDetalhe(s4);
		this.listDetalhes.addDetalhe(s5);
		this.listDetalhes.addDetalhe(s6);
		this.listDetalhes.addDetalhe(s7);
	}
	
	/**
	 * Lista os detalhes da aplicação
	 * @return
	 */
	@GET
	@Produces("applications/json")
	public ReportApplication listApplicaqtionDetails() {
		return listDetalhes;
	}
}
