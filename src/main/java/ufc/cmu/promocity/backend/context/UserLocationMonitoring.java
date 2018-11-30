package ufc.cmu.promocity.backend.context;

import java.util.LinkedList;
import java.util.List;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;
import ufc.cmu.promocity.backend.report.ReportApplication;
import ufc.cmu.promocity.backend.report.ReportCoupon;
import ufc.cmu.promocity.backend.utils.geographic.GPSPoint;
import ufc.cmu.promocity.backend.utils.geographic.GeographicArea;

/**
 * Classe responsavel por ficar monitorando a localizacao do usuario dentro da area de promocoes
 * obs: A area de promocao já deve ter sido criada com todas as lojas registradas
 * O usuário só recebe uma promoção de uma loja uma vez, ou seja, é preciso controlar o 
 *  envio de cupons para os usuários
 *  * O raio de proximidade é fixo (1 km) para todas lojas registrada
 *  s
 * @author armandosoaressousa
 *
 */
public class UserLocationMonitoring{
	private PromotionArea promotionArea;
	private CouponsSent couponsSent;
	private double radius=1;
	private List<Long> idStoreList;
	private List<Coupon> listaDeCuponsColetados = new LinkedList<Coupon>();
	private List<ReportCoupon> listaDeReportCupomColetados = new LinkedList<>();
	
	public UserLocationMonitoring(PromotionArea promotionArea) {
		this.promotionArea = promotionArea;
	}
		
	/**
	 * Checa o contexto de proximidade do usuário em relação as lojas que tenham promoções ativas
	 * @param user
	 */
	public void checkUserContext(User user) {
		List<Store> listaDeLojasComPromocoesRegistradas = this.promotionArea.getStoreAreasRegistered();
		//recupera os cupons das promocoes da loja
		List<Coupon> listaCuponsDaPromocaoDaLojaCorrente = new LinkedList<Coupon>();
		
		//1 Recebe a localização do usuário
		
		//2 Para cada loja registrada com promoções verifica a proximidade do usuário
		for (Store loja : listaDeLojasComPromocoesRegistradas) {	

			double userDistance = checkDistanceFromStore(user, loja);
			
			//2.1.1 usuário próximo -> se usuário estiver em um raio de 1km da loja 
			if (userDistance < radius) {
				//recupera as promocoes da loja
				List<Promotion> listaDePromocoesDaLojaCorrente = loja.getPromotions();				

				//percorre cada promocao para checar os cupons
				for(Promotion promocao : listaDePromocoesDaLojaCorrente) {
					List<Coupon> listaAuxiliarDeCupons = promocao.getCoupons();
					for (Coupon cupomAuxiliar : listaAuxiliarDeCupons) {												
						//2.1.1.1 Checa se o usuário já tem o cupom
						if (user.alreadyCoupon(cupomAuxiliar)) {
							// Tem cupom -> sai da loja atual e vai para próxima (continue)
							continue;
						}else {
							// Não tem cupom -> faz a coleta do cupom da loja
							listaCuponsDaPromocaoDaLojaCorrente.add(cupomAuxiliar);
							ReportCoupon cupomDetalhado = new ReportCoupon(loja, promocao, cupomAuxiliar);
							this.listaDeReportCupomColetados.add(cupomDetalhado);
						}						
					}
				}
																		
			}else {
				//2.1.2 usuário longe -> sai da loja atual e vai para a próxima (continue)
				continue;
			}								
		}				
		this.setListaDeCuponsColetados(listaCuponsDaPromocaoDaLojaCorrente);				
	}
	
   /**
    * Calcula a distancia entre a localização do usuário e o centro do raio da localização da loja passada
    * @param user
    * @param element
    * @return distancia em quilometros
    */
	public double checkDistanceFromStore(User user, Store element) {		
		double userDistance = new GeographicArea().distance(element.getLatitude(), element.getLongitude(), user.getLatitude(), user.getLongitude());
		return userDistance;
	}


	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public PromotionArea getPromotionArea() {
		return promotionArea;
	}

	public void setPromotionArea(PromotionArea promotionArea) {
		this.promotionArea = promotionArea;
	}

	public List<Long> getIdStoreList() {
		return idStoreList;
	}

	public void setIdStoreList(List<Long> idStoreList) {
		this.idStoreList = idStoreList;
	}

	public List<Coupon> getListaDeCuponsColetados() {
		return listaDeCuponsColetados;
	}

	public void setListaDeCuponsColetados(List<Coupon> listaDeCuponsColetados) {
		this.listaDeCuponsColetados = listaDeCuponsColetados;
	}

	public List<ReportCoupon> getListaDeReportCupomColetados() {
		return listaDeReportCupomColetados;
	}

	public void setListaDeReportCupomColetados(List<ReportCoupon> listaDeReportCupomColetados) {
		this.listaDeReportCupomColetados = listaDeReportCupomColetados;
	}

}
