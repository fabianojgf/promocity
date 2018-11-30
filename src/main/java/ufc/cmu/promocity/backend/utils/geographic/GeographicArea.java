package ufc.cmu.promocity.backend.utils.geographic;

/**
 * Classe responsavel por fazer os calculos de area geograficas
 * @author armandosoaressousa
 *
 */
public class GeographicArea {
	
	/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::                                                                         :*/
	/*::  This routine calculates the distance between two points (given the     :*/
	/*::  latitude/longitude of those points). It is being used to calculate     :*/
	/*::  the distance between two locations using GeoDataSource (TM) prodducts  :*/
	/*::                                                                         :*/
	/*::  Definitions:                                                           :*/
	/*::    South latitudes are negative, east longitudes are positive           :*/
	/*::                                                                         :*/
	/*::  Passed to function:                                                    :*/
	/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
	/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
	/*::    unit = the unit you desire for results                               :*/
	/*::           where: 'M' is statute miles (default)                         :*/
	/*::                  'K' is kilometers                                      :*/
	/*::                  'N' is nautical miles                                  :*/
	/*::  Worldwide cities and other features databases with latitude longitude  :*/
	/*::  are available at https://www.geodatasource.com                         :*/
	/*::                                                                         :*/
	/*::  For enquiries, please contact sales@geodatasource.com                  :*/
	/*::                                                                         :*/
	/*::  Official Web site: https://www.geodatasource.com                       :*/
	/*::           https://www.geodatasource.com/developers/java                 :*/
	/*::           GeoDataSource.com (C) All Rights Reserved 2017                :*/
	/*::                                                                         :*/
	/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/**
	 * Calculate the distance between two GPS points 
	 * @param lat1 latitude origem
	 * @param lon1 longitude origem
	 * @param lat2 latitude destination
	 * @param lon2 longitude destination
	 * @param unit kind of unit (Kilometer for example)
	 * @return distance in unit
	 */
	private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	/**
	 * Calculate the distance in meters between two GeoPoints
	 * @param from GPSPoint
	 * @param to GPSPoint
	 * @return the distance in kilometers
	 */
	public double distance(GPSPoint from, GPSPoint to) {
		return this.distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude(), "K");
		
	}
	
	public double distance(double x1, double y1, double x2, double y2) {
		return this.distance(x1, y1, x2, y2, "K");
		
	}

	
}
