package ufc.cmu.promocity.backend.service.interfaces;

import java.util.List;

import ufc.cmu.promocity.backend.model.Promotion;

public interface IPromotionService {
	public List<Promotion> getAllPromotions();
	public Promotion getPromotion(Long id);
	public void addPromotion(Promotion Promotion);
	public void updatePromotion(Long id, Promotion newPromotion);
	public Promotion deletePromotion(Long id);	
}
