package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Iterator;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
/**
 * Defines a symmetric EUCLID_TSP and gives the fitness of a tour.
 *
 */
public class GeoTspFunction implements TspFunction<GeoPoint> {

	private static GeoPoint[] pointArrayToGeo(final Point2D[] cities) {
		GeoPoint[] geoPoints = new GeoPoint[cities.length];

		for (int i = 0; i < cities.length; i++) {
			geoPoints[i] = new GeoPoint(cities[i]);
		}
		return geoPoints;
	}

	/**
	 * Calculated distances for the problem.
	 */
	private final int[][] cd;

	/**
	 * The cities used for the problem.
	 */
	private final GeoPoint[] cities;

	/**
	 * The maximum distance between two cities. Gives a fitness value.
	 */
	private final int maximumDistance;

	private final String problemName;

	public GeoTspFunction(GeoPoint[] cities) {
		this(cities, null, true);
	}

	public GeoTspFunction(GeoPoint[] cities, String problemName) {
		this(cities, problemName, true);
	}

	public GeoTspFunction(GeoPoint[] cities, String problemName,
			boolean cacheEnabled) {
		this.cities = cities;
		maximumDistance = calculateMaximumDistance(cities);
		this.problemName = problemName;
		cd = new int[cities.length][cities.length];
	}

	/**
	 * Creates a symmetric EUCLID_TSP using the cities and using the default
	 * cache value.
	 * 
	 * @param cities
	 *            the cities to use for the problem
	 */
	public GeoTspFunction(final Point2D[] cities) {
		this(cities, null, true);
	}

	/**
	 * Creates a symmetric EUCLID_TSP using the cities, the problem name and
	 * using the default cache value.
	 * 
	 * @param cities
	 *            the cities to use for the problem
	 * @param problemName
	 *            the problem name
	 */
	public GeoTspFunction(final Point2D[] cities, String problemName) {
		this(cities, problemName, true);
	}

	/**
	 * Creates a symmetric EUCLID_TSP using the cities and the cache.
	 * 
	 * @param cities
	 *            the cities to use for the problem
	 * @param doCache
	 *            whether to use the cache
	 */
	public GeoTspFunction(final Point2D[] cities, final String problemName,
			final boolean doCache) {
		this(pointArrayToGeo(cities), problemName, doCache);
	}

	@Override
	public int actualValue(final TspTour solution) {
		return calculateValue(solution);
	}

	private int calculateMaximumDistance(final GeoPoint[] cities) {
		int maxDist = 0;
		for (int i = 0; i < cities.length; i++) {
			for (int j = i + 1; j < cities.length; j++) {
				final int dist = (int) cities[i].distance(cities[j]);
				if (maxDist < dist) {
					maxDist = dist;
				}
			}
		}
		return maxDist;
	}

	/**
	 * Calculates the actual fitness of the solution.
	 * 
	 * @param solution
	 *            the solution to calculate the value for
	 * @return the fitness of the solution
	 */
	private int calculateValue(final TspTour solution) {
		int sum = 0;

		for (int i = 0; i < solution.size(); i++) {
			
			final int cityIndexA = solution.get(i);
			final int cityIndexB = solution.get((i + 1) % solution.size());
			
			sum += distance(cityIndexA, cityIndexB);
		}
		return sum;
	}

	@Override
	public GeoTspFunction copy() {
		return new GeoTspFunction(this.cities, problemName);
	}

	@Override
	public GeoTspFunction copy(boolean cacheEnabled) {
		return new GeoTspFunction(this.cities, problemName, cacheEnabled);
	}

	@Override
	public int distance(int currCity, int newCity) {
		if(cd[currCity][newCity] == 0) {
			cd[currCity][newCity] = cities[currCity].distance(cities[newCity]);
		}
		return cd[currCity][newCity];
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final GeoTspFunction other = (GeoTspFunction) obj;
		if (!Arrays.equals(cities, other.cities)) {
			return false;
		}
		return true;
	}

	@Override
	public int evaluateFitness(final TspTour tspTour) {
		return maximumDistance * cities.length - actualValue(tspTour);
	}

	public GeoPoint getCity(final int index) {
		return cities[index];
	}

	@Override
	public String getProblemName() {
		if (this.problemName == null) {
			return String.valueOf(this.hashCode());
		}
		return this.problemName;
	}

	@Override
	public ProblemType getProblemType() {
		return ProblemType.GEO_TSP;
	}

	@Override
	public boolean hasCache() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cities);
		return result;
	}

	@Override
	public Iterator<GeoPoint> iterator() {
		return new Iterator<GeoPoint>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < size();
			}

			@Override
			public GeoPoint next() {
				return getCity(i++);
			}
		};
	}

	@Override
	public int size() {
		return cities.length;
	}

}
