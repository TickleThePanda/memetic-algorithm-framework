package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Arrays;
import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

/**
 * The genetic (allele) solution for pseudo-boolean functions.
 *
 */
public class PbSolution implements Comparable<PbSolution>, Solution<PbSolution> {

  /**
   * The fitness function that this solution is for.
   */
  private final FitnessFunction<PbSolution> function;

  /**
   * The hash for the current solution.
   */
  private int hash;

  /**
   * Whether the hash has been cached yet.
   */
  private boolean hashCached;

  /**
   * The bit string.
   */
  private final boolean[] bitString;

  /**
   * The random generator that defines mutation.
   */
  private final Random random;

  /**
   * The seed for the RNG.
   */
  private final long seed;

  /**
   * Creates a PbSolution with the bit string and the function, uses a random seed for doing
   * mutation.
   * 
   * @param bitString
   *          the bit string that is the solution to the function
   * @param function
   *          the function to be used to evaluate the bit string
   */
  public PbSolution(final boolean[] bitString,
      final FitnessFunction<PbSolution> function) {
    this(System.nanoTime(), bitString, function);
  }

  /**
   * Creates a new Pseudo-Boolean function with the seed, the items and the function.
   * 
   * @param seed
   *          the seed that the mutations will be generated with
   * @param items
   *          the bit string that is the solution to the function
   * @param function
   *          the function used to evaluate the bit string
   */
  public PbSolution(final long seed, final boolean[] items,
      final FitnessFunction<PbSolution> function) {
    random = new Random(seed);
    this.seed = seed;
    this.bitString = Arrays.copyOf(items, items.length);
    this.function = function;
  }

  @Override
  public int compareTo(final PbSolution pbsCompare) {
    // n changes is difference in size;
    int changeCount = Math.abs(pbsCompare.size() - size());
    PbSolution smallest;
    if (pbsCompare.size() > size()) {
      smallest = this;
    } else {
      smallest = pbsCompare;
    }
    for (int i = 0; i < smallest.size(); i++) {
      if (pbsCompare.bitString[i] != bitString[i]) {
        changeCount++;
      }
    }
    return changeCount;
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
    final PbSolution other = (PbSolution) obj;
    if (!Arrays.equals(bitString, other.bitString)) {
      return false;
    }
    return true;
  }

  /**
   * Creates a new PbSolution and flips the specified bit.
   * 
   * @param flipIndex
   *          the index to be flipped
   * @return the PbSolution with the flipped bit
   */
  public PbSolution flipBit(final int flipIndex) {
    final boolean[] bitString = new boolean[size()];
    for (int index = 0; index < size(); index++) {
      if (index == flipIndex) {
        bitString[index] = !get(index);
      } else {
        bitString[index] = get(index);
      }
    }

    return new PbSolution(bitString, getFitnessFunction());
  }

  /**
   * Gets the boolean at the specified index.
   * 
   * @param index
   *          the boolean to gets
   * @return the boolean at the specified index
   */
  public boolean get(final int index) {
    if (index >= size()) {
      throw new IndexOutOfBoundsException("Index " + index
          + " is out of bounds!");
    }
    return bitString[index];
  }

  @Override
  public int getObjectiveValue() {
    return function.actualValue(this);
  }

  @Override
  public int getFitness() {
    return function.evaluateFitness(this);
  }

  @Override
  public FitnessFunction<PbSolution> getFitnessFunction() {
    return function;
  }

  @Override
  public PbSolution getMutated() {
    final int mutationLocation = random.nextInt(size());

    final boolean[] newItems = new boolean[bitString.length];

    for (int i = 0; i < bitString.length; i++) {
      if (i != mutationLocation) {
        newItems[i] = bitString[i];
      } else {
        newItems[i] = !bitString[i];
      }
    }

    return new PbSolution(random.nextLong(), newItems, function);
  }

  @Override
  public int hashCode() {
    if (hashCached) {
      return hash;
    } else {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(bitString);
      hash = result;
      hashCached = true;
      return result;
    }
  }

  @Override
  public int size() {
    return bitString.length;
  }

  @Override
  public String toString() {
    return "PbSolution [items=" + Arrays.toString(bitString) + ", seed=" + seed
        + "]";
  }

}
