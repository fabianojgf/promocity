package ufc.cmu.promocity.backend.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="promotions")
public class Promotion extends AbstractModel<Long>{
	
	private String description;
	private Date fromDate;
	private Date toDate;
	@ManyToOne
	private Store store;
	private Integer numRequiredCoUsers;
	private Integer numMaxCoupons;
	private Integer numReleasedCoupons;
	private boolean special = false;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="promotion")
	private List<Coupon> coupons;
	
	public Promotion() {
		super();
	}
	
	public Promotion(Long id, String description, List<Coupon> CouponList) {
		super();
		this.description = description;
		this.coupons = CouponList;
	}
	
	public Long getId() {
		return super.getId();
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
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Integer getNumRequiredCoUsers() {
		return numRequiredCoUsers;
	}

	public void setNumRequiredCoUsers(Integer numRequiredCoUsers) {
		this.numRequiredCoUsers = numRequiredCoUsers;
	}

	public Integer getNumMaxCoupons() {
		return numMaxCoupons;
	}

	public void setNumMaxCoupons(Integer numMaxCoupons) {
		this.numMaxCoupons = numMaxCoupons;
	}

	public Integer getNumReleasedCoupons() {
		return numReleasedCoupons;
	}

	public void setNumReleasedCoupons(Integer numReleasedCoupons) {
		this.numReleasedCoupons = numReleasedCoupons;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}			
}