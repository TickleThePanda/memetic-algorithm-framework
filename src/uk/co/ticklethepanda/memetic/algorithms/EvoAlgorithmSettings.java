package uk.co.ticklethepanda.memetic.algorithms;

/**
 * Contains the settings of an evolutionary algorithm.
 */
public class EvoAlgorithmSettings {

  /**
   * Helps to build settings for algorithm.
   */
  public static class Builder {
    /**
     * The pool size to build the EvoAlgorithmSettings with.
     */
    int poolSize = POOL_SIZE;

    /**
     * The crossover size to build the EvoAlgorithmSettings with.
     */
    int crossoverSize = CROSSOVER_SIZE;

    /**
     * The elite size to build the EvoAlgorithmSettings with.
     */
    int eliteSize = ELITIST_SIZE;

    /**
     * The mutation rate to build the EvoAlgorithmSettings with.
     */
    float mutationRate = MUTATION_RATE;

    /**
     * The steps before duplicates are removed to build the EvoAlgorithmSettings with.
     */
    int stepsDuplicates = STEPS_DUPLICATES;

    /**
     * Builds a new EvoAlgorithmSettings with the defined settings.
     * 
     * @return a new EvoAlgorithmSettings with the defined settings
     */
    public EvoAlgorithmSettings build() {
      return new EvoAlgorithmSettings(poolSize, eliteSize, crossoverSize,
          mutationRate, stepsDuplicates);
    }

    /**
     * Gets the currently defined crossover size.
     * 
     * @return the currently defined crossover size
     */
    public int getCrossoverSize() {
      return crossoverSize;
    }

    /**
     * Gets the currently defined elite size.
     * 
     * @return the currently defined elite size
     */
    public int getEliteSize() {
      return eliteSize;
    }

    /**
     * Gets the currently defined mutation rate.
     * 
     * @return the currently defined mutation rate
     */
    public float getMutationRate() {
      return mutationRate;
    }

    /**
     * Gets the currently defined pool size.
     * 
     * @return the currently defined pool size
     */
    public int getPoolSize() {
      return poolSize;
    }

    /**
     * Gets the currently defined steps before removing duplicates.
     * 
     * @return the currently defined steps before removing duplicates
     */
    public int getStepsDuplicates() {
      return stepsDuplicates;
    }

    /**
     * Sets the crossover size.
     * 
     * @return this builder
     */
    public Builder setCrossoverSize(final int crossoverSize) {
      this.crossoverSize = crossoverSize;
      return this;
    }

    /**
     * Sets the elite size.
     * 
     * @return this builder
     */
    public Builder setEliteSize(final int eilteSize) {
      eliteSize = eilteSize;
      return this;
    }

    /**
     * Sets the mutation rate.
     * 
     * @return this builder
     */
    public Builder setMutationRate(final float mutationRate) {
      this.mutationRate = mutationRate;
      return this;
    }

    /**
     * Sets the pool size.
     * 
     * @return this builder
     */
    public Builder setPoolSize(final int poolSize) {
      this.poolSize = poolSize;
      return this;
    }

    /**
     * Sets the steps before removing duplicates.
     * 
     * @return this builder
     */
    public Builder setStepsDuplicates(final int stepsDuplicates) {
      this.stepsDuplicates = stepsDuplicates;
      return this;
    }
  }

  /**
   * The default elitist size.
   */
  private static final int ELITIST_SIZE = 5;

  /**
   * The default elitist size for EUCLID_TSP.
   */
  private static final int ELITIST_SIZE_TSP = 10;

  /**
   * The default mutation rate.
   */
  private static final float MUTATION_RATE = 0.4f;

  /**
   * The default mutation rate for EUCLID_TSP.
   */
  private static final float MUTATION_RATE_TSP = 0.1f;

  /**
   * The default pool size.
   */
  private static final int POOL_SIZE = 100;

  /**
   * The default pool size for EUCLID_TSP.
   */
  private static final int POOL_SIZE_TSP = 100;

  /**
   * The default steps before removing duplicates.
   */
  private static final int STEPS_DUPLICATES = 10000;

  /**
   * The default steps before removing duplicates size for EUCLID_TSP.
   */
  private static final int STEPS_DUPLICATES_TSP = 10000;

  /**
   * The default crossover size.
   */
  private static final int CROSSOVER_SIZE = 10;

  /**
   * The default crossover size for EUCLID_TSP.
   */
  private static final int CROSSOVER_SIZE_TSP = 5;

  /**
   * The default settings.
   */
  public static final EvoAlgorithmSettings DEFAULT_SETTINGS =
      new EvoAlgorithmSettings(POOL_SIZE, CROSSOVER_SIZE, ELITIST_SIZE,
          MUTATION_RATE, STEPS_DUPLICATES);

  /**
   * The default settings for EUCLID_TSP.
   */
  public static final EvoAlgorithmSettings DEFAULT_SETTINGS_TSP =
      new EvoAlgorithmSettings(POOL_SIZE_TSP, 
          ELITIST_SIZE_TSP, CROSSOVER_SIZE_TSP, MUTATION_RATE_TSP, STEPS_DUPLICATES_TSP);

  /**
   * The elite size, the number of best solutions that are saved each generation.
   */
  private final int elitistSize;

  /**
   * The mutation rate, the probability of mutation.
   */
  private final float mutationRate;

  /**
   * The population size.
   */
  private final int poolSize;

  /**
   * The number of steps before removing duplicates.
   */
  private final int stepsBeforeRemDups;

  /**
   * The crossover size, the number of crossover operations that happen.
   */
  private final int crossoverSize;

  /**
   * This method initialises an EvoAlgorithmSettings object with the pool size, the elitist size,
   * the crossover size, the mutation rate, and the steps before removing duplicates.
   * 
   * @param poolSize
   *          the pool size
   * @param elitistSize
   *          the number of the best solutions to keep
   * @param crossoverSize
   *          the crossover size
   * @param mutationRate
   *          the mutation rate
   * @param stepsBeforeRemDups
   *          number of steps before removing duplicates
   */
  public EvoAlgorithmSettings(final int poolSize, final int elitistSize,
      final int crossoverSize, final float mutationRate,
      final int stepsBeforeRemDups) {

    this.poolSize = poolSize;
    this.mutationRate = mutationRate;
    this.elitistSize = elitistSize;
    this.crossoverSize = crossoverSize;
    this.stepsBeforeRemDups = stepsBeforeRemDups;
  }

  /**
   * Gets the crossover size, the number of crossover operations that happen.
   * 
   * @return the crossover size
   */
  public int getCrossoverSize() {
    return crossoverSize;
  }

  /**
   * Gets the elite size, the number of best solutions that are saved each generation.
   * 
   * @return the elite size
   */
  public int getElitismSize() {
    return elitistSize;
  }

  /**
   * Gets the mutation rate, the probability of mutation.
   * 
   * @return the mutation rate
   */
  public float getMutationRate() {
    return mutationRate;
  }

  /**
   * Gets the populations size.
   * 
   * @return the population size
   */
  public int getPoolSize() {
    return poolSize;
  }

  /**
   * Gets the steps before removing duplicates.
   * 
   * @return the steps before removing duplicates.
   */
  public int getStepsBeforeRemDups() {
    return stepsBeforeRemDups;
  }
}