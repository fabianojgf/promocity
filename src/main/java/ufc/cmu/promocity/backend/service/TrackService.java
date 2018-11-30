package ufc.cmu.promocity.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Track;
import ufc.cmu.promocity.backend.repository.CouponsRepository;
import ufc.cmu.promocity.backend.repository.TrackRepository;

/**
 * Class the manipulate the repository of Coupons
 * @author armandosoaressousa
 *
 */
@Service
public class TrackService extends AbstractService<Track, Long>{
	
	@Autowired
	private TrackRepository trackRepository;

	@Override
	protected JpaRepository<Track, Long> getRepository() {
		return trackRepository;
	}
	
}
