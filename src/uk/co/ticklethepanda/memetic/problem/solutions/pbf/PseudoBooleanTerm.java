package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A list of BooleanIndeterminats and a coefficient, specifying a Term.
 *
 */
public class PseudoBooleanTerm implements Iterable<PsuedoBooleanIndeterminant> {

  /**
   * Iterator for the BooleanIndeterminants.
   */
  private final class BooleanIndeterminantIterator implements
      Iterator<PsuedoBooleanIndeterminant> {

    /**
     * The current index in the list of indeterminant.
     */
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < psuedoBooleanIndeterminants.length;
    }

    @Override
    public PsuedoBooleanIndeterminant next() {
      return psuedoBooleanIndeterminants[index++];
    }

  }

  /**
   * List if BooleanIndeterminant for the term.
   */
  private final PsuedoBooleanIndeterminant[] psuedoBooleanIndeterminants;

  /**
   * The coefficient for the term.
   */
  private final int coefficient;

  /**
   * Creates a new Term with the coefficient and indeterminants.
   * 
   * @param coefficient
   *          the coefficient
   * @param indeterminants
   *          the indeterminants
   */
  public PseudoBooleanTerm(final int coefficient, final PsuedoBooleanIndeterminant[] indeterminants) {

    for (final PsuedoBooleanIndeterminant bo : indeterminants) {
      if (bo == null) {
        throw new IllegalArgumentException("a boolean operation is null");
      }
    }

    this.coefficient = coefficient;
    this.psuedoBooleanIndeterminants = indeterminants;
  }

  /**
   * Calculates the value of the solution for this term.
   * 
   * @param bitString
   *          the solution to test
   * @return the value of the solution for this term
   */
  public int calculateValue(final BitString bitString) {
    if(bitString == null) {
      throw new IllegalArgumentException();
    }

    if (hasCorrectLocations(bitString)) {
      return coefficient;
    } else {
      return 0;
    }
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
    final PseudoBooleanTerm other = (PseudoBooleanTerm) obj;
    if (!Arrays.equals(psuedoBooleanIndeterminants, other.psuedoBooleanIndeterminants)) {
      return false;
    }
    if (coefficient != other.coefficient) {
      return false;
    }
    return true;
  }

  /**
   * Gets the term's coefficient.
   * 
   * @return the term's coefficient.
   */
  public int getCoefficient() {
    return coefficient;
  }

  /**
   * Gets the BooleanIndeterminant in the specified index.
   * 
   * @param index
   *          the index to get
   * @return the BooleanIndeterminant at the specified index.
   */
  public PsuedoBooleanIndeterminant getBooleanIndeterminant(final int index) {
    return psuedoBooleanIndeterminants[index];
  }

  /**
   * Gets the number of BooleanIndeterminants that the term has.
   * 
   * @return the number of BooleanIndeterminats that the term has
   */
  public int getSize() {
    return psuedoBooleanIndeterminants.length;
  }

  /**
   * Whether the PbSolution has the correct locations for this term to have a value.
   * 
   * @param bitString
   *          the solution that is to be checked
   * @return whether the solution has the correct locations for this term to have a value
   */
  public boolean hasCorrectLocations(final BitString bitString) {
    for (final PsuedoBooleanIndeterminant booleanOperation : psuedoBooleanIndeterminants) {
      if (bitString.get(booleanOperation.getIndex()) == booleanOperation
          .getExpectedValue()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(psuedoBooleanIndeterminants);
    result = prime * result + coefficient;
    return result;
  }

  @Override
  public Iterator<PsuedoBooleanIndeterminant> iterator() {
    return new BooleanIndeterminantIterator();
  }

  @Override
  public String toString() {
    final int maxLen = 3;
    return "Term [modifier="
        + coefficient
        + ", booleanOperations="
        + (psuedoBooleanIndeterminants != null ? Arrays.asList(psuedoBooleanIndeterminants)
            .subList(0, Math.min(psuedoBooleanIndeterminants.length, maxLen)) : null)
        + "]";
  }

}
