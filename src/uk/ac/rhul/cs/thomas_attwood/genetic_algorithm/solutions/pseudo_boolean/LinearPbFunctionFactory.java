package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Random;

/**
 * Used to generate random Linear Psuedo-Boolean functions.
 *
 */
public class LinearPbFunctionFactory implements PbFunctionFactory {

  /**
   * The maximum coefficient any Term can be generated with.
   */
  private static final int MAX_COEFFICIENT = 1000;

  /**
   * The random to generate the function with.
   */
  private final Random random;

  /**
   * The size of the functions to create (the number of bits).
   */
  private final int size;

  /**
   * Creates a new LinearPbFunctionFactory with the number of bits and a random seed.
   * 
   * @param size
   *          the size of the function the factory will generate in number of bits. This must be > 1
   */
  public LinearPbFunctionFactory(final int size) {
    this(System.nanoTime(), size);
  }

  /**
   * Creates a new LinearPbFunctionFactory with the number of bits and the seed.
   * 
   * @param seed
   *          the seed used to initialise the function generator
   * @param size
   *          the size of the function the factory will generate. This must be greater than 1.
   */
  public LinearPbFunctionFactory(final long seed, final int size) {
    if (size < 1) {
      throw new IllegalArgumentException();
    }
    this.random = new Random(seed);
    this.size = size;
  }

  @Override
  public PbFunction generateFunction() {

    final Term[] spBooleanProblemOperation = new Term[size];
    for (int i = 0; i < size; i++) {
      final BooleanIndeterminant bo = new BooleanIndeterminant(i, false);
      spBooleanProblemOperation[i] =
          new Term(random.nextInt(MAX_COEFFICIENT) - MAX_COEFFICIENT / 2,
              new BooleanIndeterminant[] { bo });
    }
    final PbFunction spbp = new PbFunction(spBooleanProblemOperation);
    return spbp;
  }

  @Override
  public String toString() {
    return "LinearPbFunctionFactory [random=" + random + ", seed=" + ", size="
        + size + "]";
  }

}
