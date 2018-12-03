package ufc.cmu.promocity.backend.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public List<Store> findInRadiusOnDatabase(double latitude, double longitude) {
		return storesRepository.findInsideRadius(latitude, longitude);
	}
	
	public List<Store> findInRadiusOnCode(double latitude, double longitude) {
		List<Store> nearStores = new ArrayList<>();
		
		List<Store> stores = storesRepository.findAll();
		Iterator<Store> it = stores.iterator();
		while(it.hasNext()) {
			Store s = it.next();
			if(s.isInRangeRadius(latitude, longitude))
				nearStores.add(s);
		}
		
		return nearStores;
	}
}