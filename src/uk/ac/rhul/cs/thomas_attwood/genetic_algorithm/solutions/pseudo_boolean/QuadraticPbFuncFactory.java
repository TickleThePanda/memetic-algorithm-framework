package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Used to generate random Quadratic Psuedo-Boolean functions.
 *
 */
public class QuadraticPbFuncFactory implements PbFunctionFactory {

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
  public QuadraticPbFuncFactory(final int size) {
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
  public QuadraticPbFuncFactory(final long seed, final int size) {
    if (size < 2) {
      throw new IllegalArgumentException("size " + size + "is invalid");
    }
    this.size = size;
    random = new Random(seed);
  }

  @Override
  public PbFunction generateFunction() {

    final ArrayList<Term> problemOperations = new ArrayList<Term>();

    final ArrayList<Integer> notUsedList = generateNotUsedList();

    Collections.shuffle(notUsedList);

    while (!notUsedList.isEmpty()) {
      boolean linear = random.nextBoolean();
      int coefficient = random.nextInt(COEFFICIENT_MAX * 2) - COEFFICIENT_MAX;
      int usedBit = notUsedList.remove(0);
      boolean booleanModifier = random.nextBoolean();
      if (linear) {
        BooleanIndeterminant[] booleanIndeterminant =
            new BooleanIndeterminant[] { new BooleanIndeterminant(usedBit,
                booleanModifier) };
        problemOperations.add(new Term(coefficient, booleanIndeterminant));
      } else {
        BooleanIndeterminant operation1 =
            new BooleanIndeterminant(usedBit, booleanModifier);
        int usedBit2 = random.nextInt(size);
        boolean booleanModifier2 = random.nextBoolean();
        BooleanIndeterminant operation2 =
            new BooleanIndeterminant(usedBit2, booleanModifier2);
        problemOperations.add(new Term(coefficient, new BooleanIndeterminant[] {
            operation1, operation2 }));
      }
    }

    final PbFunction pff =
        new PbFunction(problemOperations.toArray(new Term[0]));

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
