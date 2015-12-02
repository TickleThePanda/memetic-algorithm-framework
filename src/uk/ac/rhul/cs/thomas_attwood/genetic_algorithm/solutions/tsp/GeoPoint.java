package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.geom.Point2D;

public class GeoPoint implements TspPoint<GeoPoint> {

	public static final double RRR = 6378.388;
	public static final double PI = 3.141592;
	private double lon;
	private double lat;

	public GeoPoint(Point2D p) {
		lat = toGeographical(p.getX());
		lon = toGeographical(p.getY());
	}

	public GeoPoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public static double toGeographical(double n) {
		int deg = (int) n;

		double min = n - deg;
		return PI * (deg + 5.0 * min / 3.0) / 180.0;
	}

	public int distance(GeoPoint other) {
		double q1 = Math.cos(this.lon - other.lon);
		double q2 = Math.cos(this.lat - other.lat);
		double q3 = Math.cos(this.lat + other.lat);
		int dist = (int) (RRR
				* Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
		return dist;
	}

	@Override
	public double getX() {
		return lat;
	}

	@Override
	public double getY() {
		return lon;
	}
}