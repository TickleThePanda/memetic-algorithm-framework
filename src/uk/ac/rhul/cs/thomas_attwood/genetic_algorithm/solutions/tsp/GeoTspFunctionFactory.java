package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.Point;
import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitFuncFactory;

/**
 * Generates random Symmetric EUCLID_TSP.
 *
 */
public class GeoTspFunctionFactory implements
    FitFuncFactory<TspTour, GeoTspFunction> {

  /**
   * The maximum value that a city's x or y coordinate could have.
   */
  private static final int MAX_CITY_VALUES = 180;

  /**
   * The random that is used to generate functions.
   */
  private final Random random;
  /**
   * The size of the function to generate.
   */
  private final int size;

  /**
   * Creates a EuclidTspFunctionFactory with the size, uses <code>System.nanoTime()</code> for
   * the seed.
   * 
   * @param size
   *          the size of the functions that the factory will generate
   */
  public GeoTspFunctionFactory(final int size) {
    this(System.nanoTime(), size);
  }

  /**
   * Creates a EuclidTspFunctionFactory with the size and the seed.
   * 
   * @param seed
   *          the seed that will be used to generate the algorithm
   * @param size
   *          the size of the functions that the factory will generate
   */
  public GeoTspFunctionFactory(final long seed, final int size) {
    random = new Random(seed);
    this.size = size;
  }

  @Override
  public GeoTspFunction generateFunction() {
    final GeoPoint[] cities = new GeoPoint[size];

    for (int i = 0; i < cities.length; i++) {
      cities[i] =
          new GeoPoint((double)random.nextInt(MAX_CITY_VALUES),(double)
              random.nextInt(MAX_CITY_VALUES));
    }

    return new GeoTspFunction(cities);
  }

}
