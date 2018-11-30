package ufc.cmu.promocity.backend.context;

import java.util.LinkedList;
import java.util.List;

import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;

/**
 * Classe que registra em uma lista uma area de promocao contendo todas as lojas registradas no sistema
 * @author armandosoaressousa
 *
 */
public class PromotionArea {
	private static PromotionArea instance = null;
	private List<Store> storeAreasRegistered; 
	
	public PromotionArea() {		
		this.storeAreasRegistered = new LinkedList<Store>();
	}
		
	public PromotionArea(List<Store> list) {
		this.storeAreasRegistered = list;
	}
	
	/**
	 * Create a singleton to share this class with all application members
	 * @return instância Global do PromotionArea
	 */
	public static PromotionArea getInstance() {
		if (instance == null) {
			instance = new PromotionArea();
		}
		return instance;
	}
	
	/**
	 * Add a StoreArea list
	 * @param sa
	 */
	public void addStoresAreas(Store sa) {
		this.storeAreasRegistered.add(sa);
	}
	
	/**
	 * Remove a storeArea from list of storeArea
	 * @param sa storeArea of store unregistered
	 */
	public void removeStoreFromStoresAreas(Store sa) {
		this.storeAreasRegistered.remove(sa);
	}

	/**
	 * Retorna todas as areas de promocao das lojas registradas
	 * @return
	 */
	public List<Store> getStoreAreasRegistered() {
		return this.storeAreasRegistered;
	}

	public void setStoreAreasRegistered(List<Store> storeAreasRegistered) {
		this.storeAreasRegistered = storeAreasRegistered;
	}

		
}
