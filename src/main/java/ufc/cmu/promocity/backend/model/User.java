package ufc.cmu.promocity.backend.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ufc.cmu.promocity.backend.model.AbstractModel;

/**
 * Classe modelo de Usuário
 * @author armandosoaressousa
 *
 */
@Entity
@Table(name="users")
public class User extends AbstractModel<Long>{
	
	@Column(length=50)
	private String username;
	
	@Column(length=255)
	private String password;
	
	@Column(length=255)
	private String completeName;
	
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	
	@Column(length=255)
	private String email;
	
	private double latitude = 0;
	private double longitude = 0;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
	private List<Coupon> coupons = new ArrayList<Coupon>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
	private List<Track> tracks = new ArrayList<Track>();
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_friendship", 
		joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "user_friend_id") })
 	private List<User> friends = new ArrayList<User>();
	
	public User() {
		this.friends = new ArrayList<User>();
	}
	
	public User(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		Long id;
		id = super.getId();
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coupon) {
		if (!this.alreadyCoupon(coupon)) {
			this.coupons.add(coupon);
		}
	}
	
	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public void addTrack(Track track) {
		if (!this.alreadyTrack(track)) {
			this.tracks.add(track);
		}
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	/**
	 * Adiciona um novo id de amigo
	 * @param idFriend
	 */
	public boolean addFriend(User friend) {
		if (!alreadyFriend(friend)) {
			this.friends.add(friend);	
			return true;
		}else {
			return false;
		} 
	}
	
	public boolean alreadyFriend(User friend) {
		//percorre a lista de amigos e checa se o amigo já está nela
		for (User userFriend : this.friends) {
			if (userFriend.getId() == friend.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean deleteFriend(User friend) {		
		//pega a lista de amigos
		Iterator<User> it = friends.iterator();
		while(it.hasNext()) {
			User userFriend = it.next();
			if (userFriend.getId() == friend.getId()) {
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public int getAmountOfCoupons() {
		return this.coupons.size();
	}

	public int getAmountOfFriends() {
		return this.friends.size();
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public boolean alreadyCoupon(Coupon coupon) {
		//percorre a lista de cupons e checa se o cupom já está nela
		for (Coupon element : this.coupons) {
			if (element.getId() == coupon.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean alreadyTrack(Track track) {
		//percorre a lista de tracks e checa se a track pesquisada já está nela
		for (Track element : this.tracks) {
			if (element.getId() == track.getId()) {
				return true;
			}
		}
		return false;
	}
}