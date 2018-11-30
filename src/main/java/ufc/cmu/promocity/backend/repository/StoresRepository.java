package ufc.cmu.promocity.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufc.cmu.promocity.backend.model.Store;

@Repository
public interface StoresRepository extends JpaRepository<Store, Long>{

}
