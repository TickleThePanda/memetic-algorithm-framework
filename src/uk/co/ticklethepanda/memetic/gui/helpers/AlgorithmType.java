package uk.co.ticklethepanda.memetic.gui.helpers;

import uk.co.ticklethepanda.memetic.gui.ProblemType;

/**
 * The type of algorithm.
 *
 */
public enum AlgorithmType {

  /**
   * Defines the genetic algorithm type.
   */
  GENETIC("Genetic", ProblemType.values()),
  /**
   * Defines the memetic algorithm type.
   */
  MEMETIC("Memetic", ProblemType.values()),
  /**
   * Defines the nearest neighbour algorithm type.
   */
  NEAREST_NEIGHBOUR("Nearest Neighbour",
      new ProblemType[] { ProblemType.EUCLID_TSP });

  /**
   * Defines the problems that the type of algorithm can run on.
   */
  private ProblemType[] availableProblems;
  /**
   * The name of the algorithm in plaintext.
   */
  String name;

  /**
   * Creates an AlgorithmType with the name and available problems.
   * 
   * @param name
   *          the name of the algorithm
   * @param availableProblems
   *          the problems available to the algorithm
   */
  private AlgorithmType(final String name, final ProblemType[] availableProblems) {
    this.name = name;
    this.availableProblems = availableProblems;
  }

  /**
   * Gets the problems that the type of algorithm can run on.
   * 
   * @return the problems that the type of algorithm can run on
   */
  public ProblemType[] getAvailableProblems() {
    return availableProblems;
  }

  @Override
  public String toString() {
    switch (this) {
    case GENETIC:
      return "Genetic";
    case MEMETIC:
      return "Memetic";
    case NEAREST_NEIGHBOUR:
      return "Nearest Neighbour";
    default:
      return "Error";
    }
  }
}
