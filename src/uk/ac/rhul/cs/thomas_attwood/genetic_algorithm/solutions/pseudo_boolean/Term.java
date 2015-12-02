package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A list of BooleanIndeterminats and a coefficient, specifying a Term.
 *
 */
public class Term implements Iterable<BooleanIndeterminant> {

  /**
   * Iterator for the BooleanIndeterminants.
   */
  private final class BooleanIndeterminantIterator implements
      Iterator<BooleanIndeterminant> {

    /**
     * The current index in the list of indeterminant.
     */
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < booleanIndeterminants.length;
    }

    @Override
    public BooleanIndeterminant next() {
      return booleanIndeterminants[index++];
    }

  }

  /**
   * List if BooleanIndeterminant for the term.
   */
  private final BooleanIndeterminant[] booleanIndeterminants;

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
  public Term(final int coefficient, final BooleanIndeterminant[] indeterminants) {

    for (final BooleanIndeterminant bo : indeterminants) {
      if (bo == null) {
        throw new IllegalArgumentException("a boolean operation is null");
      }
    }

    this.coefficient = coefficient;
    this.booleanIndeterminants = indeterminants;
  }

  /**
   * Calculates the value of the solution for this term.
   * 
   * @param pbSolution
   *          the solution to test
   * @return the value of the solution for this term
   */
  public int calculateValue(final PbSolution pbSolution) {

    if (hasCorrectLocations(pbSolution)) {
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
    final Term other = (Term) obj;
    if (!Arrays.equals(booleanIndeterminants, other.booleanIndeterminants)) {
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
  public BooleanIndeterminant getBooleanIndeterminant(final int index) {
    return booleanIndeterminants[index];
  }

  /**
   * Gets the number of BooleanIndeterminants that the term has.
   * 
   * @return the number of BooleanIndeterminats that the term has
   */
  public int getSize() {
    return booleanIndeterminants.length;
  }

  /**
   * Whether the PbSolution has the correct locations for this term to have a value.
   * 
   * @param pbSolution
   *          the solution that is to be checked
   * @return whether the solution has the correct locations for this term to have a value
   */
  public boolean hasCorrectLocations(final PbSolution pbSolution) {
    for (final BooleanIndeterminant booleanOperation : booleanIndeterminants) {
      if (pbSolution.get(booleanOperation.getIndex()) == booleanOperation
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
    result = prime * result + Arrays.hashCode(booleanIndeterminants);
    result = prime * result + coefficient;
    return result;
  }

  @Override
  public Iterator<BooleanIndeterminant> iterator() {
    return new BooleanIndeterminantIterator();
  }

  @Override
  public String toString() {
    final int maxLen = 3;
    return "Term [modifier="
        + coefficient
        + ", booleanOperations="
        + (booleanIndeterminants != null ? Arrays.asList(booleanIndeterminants)
            .subList(0, Math.min(booleanIndeterminants.length, maxLen)) : null)
        + "]";
  }

}
