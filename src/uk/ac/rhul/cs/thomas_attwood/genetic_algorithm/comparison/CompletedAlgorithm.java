package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm;

/**
 * Contains the data about a completed
 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm Algorithm}.
 */
public class CompletedAlgorithm {
  /**
   * The completed algorithm.
   */
  private final Algorithm<?> algorithm;
  /**
   * The completed algorithm metrics.
   */
  private final AlgorithmMetrics algorithmMetrics;

  /**
   * Constructs a new CompletedAlgorithm with the algorithm and the algorithm metrics.
   * 
   * @param algorithm
   *          the algorithm that is complete
   * @param algorithmMetrics
   *          the metrics of the completed algorithm
   */
  public CompletedAlgorithm(Algorithm<?> algorithm,
      AlgorithmMetrics algorithmMetrics) {
    this.algorithm = algorithm;
    this.algorithmMetrics = algorithmMetrics;
  }

  /**
   * Gets the algorithm that has been completed.
   * 
   * @return the algorithm that has been completed
   */
  public Algorithm<?> getAlgorithm() {
    return algorithm;
  }

  /**
   * Gets the metrics of the algorithm that has been completed.
   * 
   * @return the metrics of the algorithm that has been completed
   */
  public AlgorithmMetrics getAlgorithmMetrics() {
    return algorithmMetrics;
  }
}
