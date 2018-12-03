package ufc.cmu.promocity.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotion, Long>{
	List<Promotion> findByStore(Store store);
	@Query("select p from Promotion p join p.store s where s.user = ?1")
	List<Promotion> findFromStoresFromUser(User user);
	@Query("select distinct c.promotion from Coupon c join c.promotion where c.user = ?1")
	List<Promotion> findFromCouponsFromUser(User user);
	@Query(value="select p.* from promotions p join stores s on s.id = p.store_id where is_in_radius(?1,?2,s.latitude,s.longitude,2,s.radius)", nativeQuery=true)
	List<Promotion> findInsideRadius(double latitude, double longitude);
}
