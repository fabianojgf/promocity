package ufc.cmu.promocity.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.User;

@Repository
public interface StoresRepository extends JpaRepository<Store, Long>{
	List<Store> findByUser(User user);
	@Query(value="select * from stores s where is_in_radius(:latitude,:longitude,s.latitude,s.longitude,2,s.radius)", nativeQuery = true)
	List<Store> findInsideRadius(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
