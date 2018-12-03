package ufc.cmu.promocity.backend.utils;

public class DistanceUtil {
	public enum Radius {
		MILES {
			public double size() {
				return 3959;
			}
		}, KILOMETERS {
			public double size() {
				return 6371;
			}
		}, METERS {
			public double size() {
				return KILOMETERS.size() * 1000;
			}
		};
		
		public abstract double size();
	}
	
	public static double distante(double lat1, double long1, double lat2, double long2, Radius radius) {
		double R = radius.size();
	    double dLat = Math.toRadians(lat1-lat2);
	    double dLon = Math.toRadians(long1-long2);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	            Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1)) * 
	            Math.sin(dLon/2) * Math.sin(dLon/2); 
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    return R * c;
	}
	
	public static double distanteBetween(double lat1, double long1, double lat2, double long2, Radius radius) {
		double distance = (radius.size() * 
					Math.acos(
							Math.cos(Math.toRadians(lat1)) *
							Math.cos(Math.toRadians(lat2)) *
							Math.cos(Math.toRadians(long2) - Math.toRadians(long1)) +
							Math.sin(Math.toRadians(lat1)) *
							Math.sin(Math.toRadians(lat2))
							)
					);
	    return distance;
	}
}
