package uk.co.ticklethepanda.memetic.gui;

/**
 * Defines the concrete problem types.
 */
public enum ProblemType {

  /**
   * Defines Linear Pseudo-Boolean functions.
   */
  PSEUDO_BOOLEAN_LINEAR("Linear Pseudo-Boolean"),
  /**
   * Defines Quadratic Pseudo-Boolean functions.
   */
  PSEUDO_BOOLEAN_QUADRATIC("Quadratic Pseudo-Boolean"),
  /**
   * Defines Symmetric Travelling Salesman Problem.
   */
  EUCLID_TSP("Euclidian TSP"),
  /**
   * Defines the Unsymmetric Travelling Salesman Problem.
   */
  GEO_TSP("Geometric TSP");

  /**
   * The name of the type of problem in plain-text.
   */
  private String string;

  /**
   * Creates a problem type with the name of the problem.
   * 
   * @param string
   *          the name of the problem
   */
  private ProblemType(final String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return string;
  }
}
