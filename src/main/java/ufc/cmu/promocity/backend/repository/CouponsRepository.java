package ufc.cmu.promocity.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;

@Repository
public interface CouponsRepository extends JpaRepository<Coupon, Long>{
	List<Coupon> findByUser(User user);
	List<Coupon> findByPromotion(Promotion promotion);
	List<Coupon> findByUserAndPromotion(User user, Promotion promotion);
	Coupon findByQrCode(String qrCode);
	
	@Query("select c from Coupon c join c.promotion p where p.store = ?1")
	List<Coupon> findWithStore(Store store);
	@Query("select c from Coupon c join c.promotion p where c.user = ?1 and p.store = ?2")
	List<Coupon> findWithUserStore(User user, Store store);
}
