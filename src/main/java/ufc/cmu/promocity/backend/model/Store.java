package ufc.cmu.promocity.backend.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ufc.cmu.promocity.backend.utils.DistanceUtil;
import ufc.cmu.promocity.backend.utils.DistanceUtil.Radius;

@Entity
@Table(name="stores")
public class Store extends AbstractModel<Long>{
	private String name;
	private String address;
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	private double radius;
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="store")
	private List<Promotion> promotions = new LinkedList<Promotion>();
	
	public Store() {
		super();
	}
	
	public Store(String name, String address, String city, String state) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public void addPromotion(Promotion promotion) {
		this.promotions.add(promotion);		
	}
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public double distanceFrom(double latitude, double longitude) {
		double distance = DistanceUtil.distanteBetween(latitude, longitude, this.latitude, this.longitude, Radius.KILOMETERS);
		double distance2 = DistanceUtil.distante(latitude, longitude, this.latitude, this.longitude, Radius.KILOMETERS);
		return distance;
	}
	
	public boolean isInRangeRadius(double latitude, double longitude) {
		double distance = distanceFrom(latitude, longitude);
		return distance <= radius;
	}
}