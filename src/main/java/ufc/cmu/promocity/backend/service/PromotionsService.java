package ufc.cmu.promocity.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.repository.PromotionsRepository;

/**
 * Class the manipulate the repository of Promotions
 * @author armandosoaressousa
 *
 */
@Service
public class PromotionsService extends AbstractService<Promotion, Long>{
	
	@Autowired
	private PromotionsRepository promotionsRepository;
	
	@Override
	protected JpaRepository<Promotion, Long> getRepository() {
		return promotionsRepository;
	}
	
	public List<Promotion> findInsideRadius(double latitude, double longitude) {
		return promotionsRepository.findInsideRadius(latitude, longitude);
	}
	
}