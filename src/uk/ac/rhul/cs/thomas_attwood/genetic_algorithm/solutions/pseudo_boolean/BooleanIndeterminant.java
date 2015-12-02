package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

/**
 * Defines a boolean operation for Pseudo-Boolean Functions.
 *
 */
public class BooleanIndeterminant {
  /**
   * prime to generate hash codes.
   */
  private static final int PRIME_TWO = 1237;
  /**
   * prime to generate hash codes.
   */
  private static final int PRIME_ONE = 1231;
  /**
   * the expected boolean value.
   */
  private final boolean expectedValue;
  /**
   * the index of the boolean.
   */
  private final int index;

  /**
   * Creates a new Operation with the index and the expected boolean value.
   * 
   * @param index
   *          the index of the bit
   * @param expectedValue
   *          the expected boolean value of the bit
   */
  public BooleanIndeterminant(final int index, final boolean expectedValue) {
    this.index = index;
    this.expectedValue = expectedValue;
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
    final BooleanIndeterminant other = (BooleanIndeterminant) obj;
    if (expectedValue != other.expectedValue) {
      return false;
    }
    if (index != other.index) {
      return false;
    }
    return true;
  }

  /**
   * Gets the index of the boolean expected in the bit string.
   * 
   * @return the index of the boolean expected in the bit string
   */
  public int getIndex() {
    return index;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (expectedValue ? PRIME_ONE : PRIME_TWO);
    result = prime * result + index;
    return result;
  }

  /**
   * Gets the expected value of the boolean.
   * 
   * @return the expected value of the boolean
   */
  public boolean getExpectedValue() {
    return expectedValue;
  }

  @Override
  public String toString() {
    return "Operation [location=" + index + ", flipped=" + expectedValue + "]";
  }

}