package ufc.cmu.promocity.backend;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import ufc.cmu.promocity.backend.context.PromotionArea;
import ufc.cmu.promocity.backend.controller.BookController;
import ufc.cmu.promocity.backend.controller.CouponController;
import ufc.cmu.promocity.backend.controller.PrincipalController;
import ufc.cmu.promocity.backend.controller.PromotionController;
import ufc.cmu.promocity.backend.controller.StoreController;
import ufc.cmu.promocity.backend.controller.UserController;
import ufc.cmu.promocity.backend.report.ReportApplication;

/**
 * Faz a configuracao do Servico Jersey
 * @author armandosoaressousa
 * Agradecimento a https://dzone.com/articles/spring-boot-building-restful-web-services-with-jersey
 */
@Configuration
@ApplicationPath("promocity")
public class JerseyConfiguration extends ResourceConfig {
	public PromotionArea globalPromotionArea;
	
	public JerseyConfiguration() {
		this.globalPromotionArea = new PromotionArea();
	}
	
	/**
	 * Faz os registros dos controllers
	 */
	@PostConstruct
	public void setUp() {
		//Create a global instance of promotionalArea
		this.globalPromotionArea = PromotionArea.getInstance();
		
		register(BookController.class);
		register(StoreController.class);
		register(UserController.class);		
		register(CouponController.class);
		register(PromotionController.class);
		register(PrincipalController.class);
		register(GenericExceptionMapper.class);
	}
}