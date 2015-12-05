package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.tsp;

import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.ProblemGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Cities;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Tour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.City.Geo;

/**
 * Generates random Symmetric EUCLID_TSP.
 *
 */
public class GeoCitiesGenerator implements
    ProblemGenerator<Tour, Cities<Geo>> {

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
  public GeoCitiesGenerator(final int size) {
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
  public GeoCitiesGenerator(final long seed, final int size) {
    random = new Random(seed);
    this.size = size;
  }

  @Override
  public Cities<Geo> generateFunction() {
    final Geo[] cities = new Geo[size];

    for (int i = 0; i < cities.length; i++) {
      cities[i] =
          new Geo((double)random.nextInt(MAX_CITY_VALUES),(double)
              random.nextInt(MAX_CITY_VALUES));
    }

    return new Cities<Geo>(cities);
  }

}
