package ufc.cmu.promocity.backend.report;

import java.util.Date;

import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;

public class ReportPromotion {
	private Promotion promotion;
	private Store store;
	private long idPromotion;
	private String description;
	private Date fromDate;
	private Date toDate;
	private long idStore;
	private String name;
	private double latitude;
	private double longitude;
	
	public ReportPromotion(Promotion promotion) {
		this.promotion = promotion;
		this.store = this.promotion.getStore();
		
		this.idPromotion = promotion.getId();
		this.description = promotion.getDescription();
		this.fromDate = promotion.getFromDate();
		this.toDate = promotion.getToDate();
		
		this.idStore = store.getId();
		this.name = store.getName();
		this.latitude = store.getLatitude();
		this.longitude = store.getLongitude();
	}
	
	public ReportPromotion(Promotion promotion, Store store) {
		this.promotion = promotion;
		this.store = store;
		this.idPromotion = promotion.getId();
		this.description = promotion.getDescription();
		this.fromDate = promotion.getFromDate();
		this.toDate = promotion.getToDate();
		this.idStore = store.getId();
		this.name = store.getName();
		this.latitude = store.getLatitude();
		this.longitude = store.getLongitude();
	}

	public long getIdPromotion() {
		return idPromotion;
	}

	public void setIdPromotion(long idPromotion) {
		this.idPromotion = idPromotion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public long getIdStore() {
		return idStore;
	}

	public void setIdStore(long idStore) {
		this.idStore = idStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
