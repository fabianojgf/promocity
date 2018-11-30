package ufc.cmu.promocity.backend.utils.geographic;

/**
 * Classe de Geopoint em Latitude e Longitude 
 * @author armandosoaressousa
 *
 */
public class GPSPoint {
	private double latitude;
	private double longitude;
	
	/**
	 * Constructor of GPSPoint
	 * @param x latitude
	 * @param y longitude
	 */
	public GPSPoint(double x, double y) {
		this.latitude = x;
		this.longitude = y;
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
	
}
