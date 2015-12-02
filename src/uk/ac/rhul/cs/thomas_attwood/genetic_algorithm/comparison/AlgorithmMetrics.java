package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison;

/**
 * Stores the metrics of an algorithm.
 */
public class AlgorithmMetrics {

  /**
   * The total running time of the algorithm in milliseconds.
   */
  private final long runningTime;

  /**
   * The number of generations that the algorithm has been running for.
   */
  private final int generationCount;

  /**
   * Creates a metrics class with the running time and the generation count.
   * 
   * @param runningTime
   *          the running time of the algorithm in milliseconds
   * @param generationCount
   *          the number of generations that the algorithm has been running for
   */
  public AlgorithmMetrics(long runningTime, int generationCount) {
    this.runningTime = runningTime;
    this.generationCount = generationCount;
  }

  /**
   * The number of generations the algorithm has been running for.
   * 
   * @return the number of generations the algorithm has been running for
   */
  public int getGenerationCount() {
    return generationCount;
  }

  /**
   * The running time of the algorithm in milliseconds.
   * 
   * @return the running time of the algorithm in milliseconds
   */
  public long getRunningTime() {
    return runningTime;
  }

}
