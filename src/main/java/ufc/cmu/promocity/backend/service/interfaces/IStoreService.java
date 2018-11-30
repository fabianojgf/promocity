package ufc.cmu.promocity.backend.service.interfaces;

import java.util.List;

import ufc.cmu.promocity.backend.model.Store;;

public interface IStoreService {
	public List<Store> getAllStores();
	public Store getStore(Long id);
	public void addStore(Store Store);
	public void updateStore(Long id, Store newStore);
	public Store deleteStore(Long id);	
}
