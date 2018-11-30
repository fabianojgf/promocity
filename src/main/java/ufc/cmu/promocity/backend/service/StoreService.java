package ufc.cmu.promocity.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ufc.cmu.promocity.backend.context.PromotionArea;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.repository.StoresRepository;

/**
 * Class the manipulate the repository of Stores
 * @author armandosoaressousa
 *
 */
@Service
public class StoreService extends AbstractService<Store, Long>{
	@Autowired
	StoresRepository storesRepository;
	
	public PromotionArea globalPromotionArea = PromotionArea.getInstance();

	@Override
	protected JpaRepository<Store, Long> getRepository() {
		return storesRepository;
	}
	
}