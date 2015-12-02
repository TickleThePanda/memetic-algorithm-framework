package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitFuncFactory;

/**
 * Generates random UnsymmetricTspFunctions.
 *
 */
public class UnsymmetricTspFunctionFactory implements
    FitFuncFactory<TspTour, UnsymmetricTspFunction> {

  /**
   * The maximum value a distance can be.
   */
  private static final int MAX_DISTANCE = 10000;

  /**
   * The random that is used to generate functions.
   */
  private final Random random;
  /**
   * The size of the function to generate.
   */
  private final int size;

  /**
   * Creates a UnsymmetricTspFunctionFactory with the size, uses <code>System.nanoTime()</code> for
   * the seed.
   * 
   * @param size
   *          the size of the functions that the factory will generate
   */
  public UnsymmetricTspFunctionFactory(final int size) {
    this(new Random().nextInt(), size);
  }

  /**
   * Creates a UnsymmetricTspFunctionFactory with the size and the seed.
   * 
   * @param seed
   *          the seed that will be used to generate the algorithm
   * @param size
   *          the size of the functions that the factory will generate
   */
  public UnsymmetricTspFunctionFactory(final long seed, final int size) {
    random = new Random(seed);
    this.size = size;
  }

  @Override
  public UnsymmetricTspFunction generateFunction() {
    final int[][] weightedConnectivityMatrix = new int[size][size];

    for (int i = 0; i < weightedConnectivityMatrix.length; i++) {
      for (int j = 0; j < weightedConnectivityMatrix.length; j++) {
        weightedConnectivityMatrix[i][j] = random.nextInt(MAX_DISTANCE);
      }
    }

    final UnsymmetricTspFunction function =
        new UnsymmetricTspFunction(weightedConnectivityMatrix);

    return function;
  }

}
