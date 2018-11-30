package ufc.cmu.promocity.backend.service.interfaces;

import java.util.List;

import ufc.cmu.promocity.backend.model.Coupon;

public interface ICouponService {
	public List<Coupon> getAllCoupons();
	public Coupon getCoupon(Long id);
	public void addCoupon(Coupon Coupon);
	public void updateCoupon(Long id, Coupon newCoupon);
	public Coupon deleteCoupon(Long id);	
}
