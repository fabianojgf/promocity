package ufc.cmu.promocity.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.User;
import ufc.cmu.promocity.backend.repository.CouponsRepository;

/**
 * Class the manipulate the repository of Coupons
 * @author armandosoaressousa
 *
 */
@Service
public class CouponsService extends AbstractService<Coupon, Long>{
	
	@Autowired
	private CouponsRepository couponsRepository;

	@Override
	protected JpaRepository<Coupon, Long> getRepository() {
		return couponsRepository;
	}
	
	public List<Coupon> findByUserAndPromotion(User user, Promotion promotion) {
		return couponsRepository.findByUserAndPromotion(user, promotion);
	}
	
	public Coupon findByQrCode(String qrCode) {
		return couponsRepository.findByQrCode(qrCode);
	}
}
