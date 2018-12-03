package ufc.cmu.promocity.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufc.cmu.promocity.backend.model.Track;
import ufc.cmu.promocity.backend.model.User;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long>{
	List<Track> findByUser(User user);
	Track findLastByUser(User user);
}
