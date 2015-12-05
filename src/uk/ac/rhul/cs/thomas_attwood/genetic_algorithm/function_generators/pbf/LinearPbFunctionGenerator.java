package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.function_generators.pbf;

import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PsuedoBooleanIndeterminant;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PseudoBooleanTerm;

/**
 * Used to generate random Linear Psuedo-Boolean functions.
 *
 */
public class LinearPbFunctionGenerator implements PbFunctionGenerator {

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
  public LinearPbFunctionGenerator(final int size) {
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
  public LinearPbFunctionGenerator(final long seed, final int size) {
    if (size < 1) {
      throw new IllegalArgumentException();
    }
    this.random = new Random(seed);
    this.size = size;
  }

  @Override
  public PsuedoBooleanFunction generateFunction() {

    final PseudoBooleanTerm[] spBooleanProblemOperation = new PseudoBooleanTerm[size];
    for (int i = 0; i < size; i++) {
      final PsuedoBooleanIndeterminant bo = new PsuedoBooleanIndeterminant(i, false);
      spBooleanProblemOperation[i] =
          new PseudoBooleanTerm(random.nextInt(MAX_COEFFICIENT) - MAX_COEFFICIENT / 2,
              new PsuedoBooleanIndeterminant[] { bo });
    }
    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spBooleanProblemOperation);
    return spbp;
  }

  @Override
  public String toString() {
    return "LinearPbFunctionFactory [random=" + random + ", seed=" + ", size="
        + size + "]";
  }

}
