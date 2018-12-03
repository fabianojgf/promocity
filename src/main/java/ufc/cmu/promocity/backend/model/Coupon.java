package ufc.cmu.promocity.backend.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="coupons")
public class Coupon extends AbstractModel<Long>{
	
	@ManyToOne
	private Promotion promotion;
	@ManyToOne
	private User user;
	private String qrCode;
	private boolean activated = false;
	private boolean consumed = false;
	private Integer numRequiredCoUsers = 0;
	private Integer numActivedCoUsers = 0;
	
	public Coupon() {
		super();
	}
	
	public Coupon(Promotion promotion, User user) {
		super();
		this.promotion = promotion;
		this.user = user;
	}
	
	public Long getId() {
		return super.getId();
	}
	
    public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getQrCode() {
		return qrCode;
	}
	
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public boolean isActivated() {
		return activated;
	}
	
	public Integer getNumRequiredCoUsers() {
		return numRequiredCoUsers;
	}

	public void setNumRequiredCoUsers(Integer numRequiredCoUsers) {
		this.numRequiredCoUsers = numRequiredCoUsers;
	}

	public Integer getNumActivedCoUsers() {
		return numActivedCoUsers;
	}

	public void setNumActivedCoUsers(Integer numActivedCoUsers) {
		this.numActivedCoUsers = numActivedCoUsers;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}
	
	public void incrementUse() {
		if(numActivedCoUsers < numRequiredCoUsers)
			numActivedCoUsers++;
	}
	
	public boolean isRequiredCoUsers() {
		return numRequiredCoUsers > 0;
	}

	/**
     * Checa se um cupom é valido
     * @param IdCoupon
     * @return
     */
    public boolean isValidCoupon() {
    	return !isActivated() && !isConsumed();
    }

}