package ufc.cmu.promocity.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufc.cmu.promocity.backend.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long>{

}
