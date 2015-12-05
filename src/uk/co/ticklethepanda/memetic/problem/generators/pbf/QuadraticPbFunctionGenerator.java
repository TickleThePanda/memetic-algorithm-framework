package uk.co.ticklethepanda.memetic.problem.generators.pbf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PseudoBooleanTerm;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PsuedoBooleanIndeterminant;

/**
 * Used to generate random Quadratic Psuedo-Boolean functions.
 *
 */
public class QuadraticPbFunctionGenerator implements PbFunctionGenerator {

  /**
   * The maximum coefficient any Term can be generated with.
   */
  private static final int COEFFICIENT_MAX = 1000;
  /**
   * The RNG used to generate the function.
   */
  private final Random random;
  /**
   * The size of the function to generate.
   */
  private final int size;

  /**
   * Creates a new QuadraticPbFuncFactory with the size and seeding it with
   * <code>System.nanoTime()</code>.
   * 
   * @param size
   *          the size of the functions to create
   */
  public QuadraticPbFunctionGenerator(final int size) {
    this(System.nanoTime(), size);
  }

  /**
   * Creates a new QuadraticPbFuncFactory with the size and with the seed.
   * 
   * @param seed
   *          the seed to generate the functions with
   * @param size
   *          the size of the functions to create
   */
  public QuadraticPbFunctionGenerator(final long seed, final int size) {
    if (size < 2) {
      throw new IllegalArgumentException("size " + size + "is invalid");
    }
    this.size = size;
    random = new Random(seed);
  }

  @Override
  public PsuedoBooleanFunction generateFunction() {

    final ArrayList<PseudoBooleanTerm> problemOperations = new ArrayList<PseudoBooleanTerm>();

    final ArrayList<Integer> notUsedList = generateNotUsedList();

    Collections.shuffle(notUsedList);

    while (!notUsedList.isEmpty()) {
      boolean linear = random.nextBoolean();
      int coefficient = random.nextInt(COEFFICIENT_MAX * 2) - COEFFICIENT_MAX;
      int usedBit = notUsedList.remove(0);
      boolean booleanModifier = random.nextBoolean();
      if (linear) {
        PsuedoBooleanIndeterminant[] booleanIndeterminant =
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(usedBit,
                booleanModifier) };
        problemOperations.add(new PseudoBooleanTerm(coefficient, booleanIndeterminant));
      } else {
        PsuedoBooleanIndeterminant operation1 =
            new PsuedoBooleanIndeterminant(usedBit, booleanModifier);
        int usedBit2 = random.nextInt(size);
        boolean booleanModifier2 = random.nextBoolean();
        PsuedoBooleanIndeterminant operation2 =
            new PsuedoBooleanIndeterminant(usedBit2, booleanModifier2);
        problemOperations.add(new PseudoBooleanTerm(coefficient, new PsuedoBooleanIndeterminant[] {
            operation1, operation2 }));
      }
    }

    final PsuedoBooleanFunction pff =
        new PsuedoBooleanFunction(problemOperations.toArray(new PseudoBooleanTerm[0]));

    return pff;

  }

  /**
   * Generates a list of unused bits.
   * 
   * @return the list of unused bits.
   */
  private ArrayList<Integer> generateNotUsedList() {
    final Integer[] notUsed = new Integer[size];

    for (int i = 0; i < size; i++) {
      notUsed[i] = i;
    }

    final ArrayList<Integer> notUsedList =
        new ArrayList<Integer>(Arrays.asList(notUsed));
    return notUsedList;
  }

  @Override
  public String toString() {
    return "QuadraticPbFuncFactory [size=" + size + ", random=" + random + "]";
  }

}
