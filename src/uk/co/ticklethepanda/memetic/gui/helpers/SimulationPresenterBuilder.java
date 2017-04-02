package uk.co.ticklethepanda.memetic.gui.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import uk.co.ticklethepanda.memetic.algorithms.Algorithm;
import uk.co.ticklethepanda.memetic.algorithms.EvoAlgorithmSettings;
import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.gui.controller.SimulationPresenter;
import uk.co.ticklethepanda.memetic.gui.view.PbSolutionView;
import uk.co.ticklethepanda.memetic.gui.view.TSPSolutionView;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.BitString;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Tour;

/**
 * The settings of a an abstract algorithm which can create a concrete presenter for that algorithm.
 */
public final class SimulationPresenterBuilder implements Serializable {

  /**
   * Helps to build the settings.
   */
  public static class Builder {

    /**
     * Defines the minumum problem size allowed.
     */
    private static final int MINIMUM_PROBLEM_SIZE = 3;
    /**
     * The {@link genetic -algorithm.src.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm
     * .AlgorithmType AlgorithmType} to use to build the algorithm.
     */
    private AlgorithmType at = null;
    /**
     * The {@link genetic -algorithm.src.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm .ProblemType
     * ProblemType} to use to build the algorithm.
     */
    private ProblemType pt = null;
    /**
     * The problem size to use to build the algorithm.
     */
    private int problemSize = 0;
    /**
     * The problem seed to use to build the algorithm.
     */
    private long problemSeed = 0;
    /**
     * The evolutionary algoritm settings to use to build the algorithm.
     */
    private EvoAlgorithmSettings eas = null;
    /**
     * Whether to use to build the algorithm.
     */
    private boolean useProblemSeed = false;

    /**
     * Builds the AlgorithmSettings with the defined settings.
     *
     * @return the AlgorithmSettings built
     */
    public SimulationPresenterBuilder build() {
      if (at == null || pt == null || problemSize < MINIMUM_PROBLEM_SIZE) {
        throw new IllegalStateException("algorithm type, problem type or problemSize not set");
      }
      return new SimulationPresenterBuilder(at, pt, problemSize, useProblemSeed, problemSeed, eas);
    }

    /**
     * Sets the AlgorithmType that the settings will use.
     *
     * @param algorithmType
     *          the AlgorithmType that the settings will use
     * @return this builder
     */
    public Builder setAlgorithmType(final AlgorithmType algorithmType) {
      at = algorithmType;
      return this;
    }

    /**
     * Sets the EvoAlgorithmSettings that the settings will use.
     *
     * @param evoAlgorithmSettings
     *          the EvoAlgorithmSettings that the settings will use
     * @return this builder
     */
    public Builder setEvoAlgorithmSettings(final EvoAlgorithmSettings evoAlgorithmSettings) {
      eas = evoAlgorithmSettings;
      return this;
    }

    /**
     * Set the problem seed that the settings will use.
     *
     * @param seed
     *          the problem seed that the settings will use
     * @return this builder
     */
    public Builder setProblemSeed(final long seed) {
      problemSeed = seed;
      return this;
    }

    /**
     * Set the problem size that the settings will use.
     *
     * @param problemSize
     *          the problem size that the settings will use
     * @return this builder
     */
    public Builder setProblemSize(final int problemSize) {
      this.problemSize = problemSize;
      return this;
    }

    /**
     * Set the ProblemType that the settings will use.
     *
     * @param problemType
     *          the problem type that the settings will use
     * @return this builder
     */
    public Builder setProblemType(final ProblemType problemType) {
      pt = problemType;
      return this;
    }

    /**
     * Set the problem seed that the settings will use.
     *
     * @param useProblemSeed
     *          the problem seed that the settings will use
     * @return the builder
     */
    public Builder setUseProblemSeed(final boolean useProblemSeed) {
      this.useProblemSeed = useProblemSeed;
      return this;
    }

  }

  /**
   * The Serial Version UID.
   */
  private static final long serialVersionUID = -7563799282565306208L;

  /**
   * AlgorithmFactory to build the algorithms from the settings.
   */
  private static AlgorithmFactory algorithmFactory = new AlgorithmFactory();

  /**
   * Loads pre-saved settings from a specified file.
   *
   * @param file
   *          the file to load the settings from
   * @return the AlogirithmSettings previously saved to file
   */
  public static SimulationPresenterBuilder readSettingsFromFile(final File file) {
    JsonReader jsonReader = null;
    try {
      jsonReader = new JsonReader(new FileReader(file));
    } catch (final FileNotFoundException e1) {
      e1.printStackTrace();
    }
    if (jsonReader != null) {
      final Gson gson = new Gson();
      return gson.fromJson(jsonReader, SimulationPresenterBuilder.class);
    }
    return null;
  }

  /**
   * The type of algorithm.
   */
  private final AlgorithmType algorithmType;

  /**
   * The type of problem.
   */
  private final ProblemType problemType;

  /**
   * The problem size.
   */
  private final int problemSize;

  /**
   * The problem seed.
   */
  private final long problemSeed;

  /**
   * The evolutionary algorithm settings.
   */
  private final EvoAlgorithmSettings evoAlgoithmSettings;
  /**
   * Whether the algorithm uses the problem seed.
   */
  private final boolean useProblemSeed;

  /**
   * Creates an simulation view builder from the settings provided.
   *
   * @param algorithmType
   *          the algorithm type to build
   * @param problemType
   *          the problem type to build
   * @param problemSize
   *          the problem size to build
   * @param useProblemSeed
   *          whether to use the problem seed
   * @param problemSeed
   *          the problem seed to use
   * @param evoAlgorithmSettings
   *          the evo algorithm settings to use
   */
  private SimulationPresenterBuilder(final AlgorithmType algorithmType,
      final ProblemType problemType, final int problemSize, final boolean useProblemSeed,
      final long problemSeed, final EvoAlgorithmSettings evoAlgorithmSettings) {
    this.algorithmType = algorithmType;
    this.problemType = problemType;
    this.useProblemSeed = useProblemSeed;
    this.problemSize = problemSize;
    this.problemSeed = problemSeed;
    evoAlgoithmSettings = evoAlgorithmSettings;
  }

  /**
   * Create a new simulation window for a Linear Psuedo-Boolean Function from the settings provided.
   *
   * @return a new simulation window for a Linear Psuedo-Boolean Function from the settings
   *         provided.
   */
  public SimulationPresenter<BitString> createNewPblSimulationWindow() {
    Algorithm<BitString> algorithm;
    switch (this.getAlgorithmType()) {
    case GENETIC:
      algorithm = algorithmFactory.createLinearPbGeneticAlgorithm(this.getProblemSeed(),
          problemSize, evoAlgoithmSettings);
      break;
    case MEMETIC:
      algorithm = algorithmFactory.createLinearPbMemeticAlgorithm(this.getProblemSeed(),
          problemSize, evoAlgoithmSettings);
      break;
    default:
      return null;
    }
    final PbSolutionView pbsv = new PbSolutionView();

    return new SimulationPresenter<BitString>(algorithm, pbsv);
  }

  /**
   * Create a new simulation window for a Quadratic Psuedo-Boolean Function from the settings
   * provided.
   *
   * @return a new simulation window for a Quadratic Psuedo-Boolean Function from the settings
   *         provided.
   */
  public SimulationPresenter<BitString> createNewPbqSimulationWindow() {

    Algorithm<BitString> algorithm;
    switch (this.getAlgorithmType()) {
    case GENETIC:
      algorithm = algorithmFactory.createQuadraticPbGeneticAlgorithm(problemSeed, problemSize,
          evoAlgoithmSettings);
      break;
    case MEMETIC:
      algorithm = algorithmFactory.createQuadraticPbMemeticAlgorithm(problemSeed, problemSize,
          evoAlgoithmSettings);
      break;
    default:
      return null;
    }
    final PbSolutionView pbsv = new PbSolutionView();

    return new SimulationPresenter<BitString>(algorithm, pbsv);
  }

  /**
   * Create a new simulation window from the settings provided.
   *
   * @return a new simulation window from the settings provided.
   */
  public SimulationPresenter<?> createNewSimulation() {
    try {
      return Executors.newSingleThreadExecutor().submit(() -> {
        SimulationPresenter<?> simulationPresenter;
        switch (problemType) {
        case PSEUDO_BOOLEAN_LINEAR:
          simulationPresenter = createNewPblSimulationWindow();
          break;
        case PSEUDO_BOOLEAN_QUADRATIC:
          simulationPresenter = createNewPbqSimulationWindow();
          break;
        case EUCLID_TSP:
          simulationPresenter = createNewTspSimulationWindow();
          break;
        default:
          simulationPresenter = null;
        }
        return simulationPresenter;
      }).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Create a new simulation window for a EUCLID_TSP problem from the settings provided.
   *
   * @return a new simulation window for a EUCLID_TSP problem from the settings provided.
   */
  public SimulationPresenter<Tour> createNewTspSimulationWindow() {
    Algorithm<Tour> algorithm;
    switch (this.getAlgorithmType()) {
    case GENETIC:
      algorithm = algorithmFactory.createTspGeneticAlgorithm(this.getProblemSeed(), problemSize,
          evoAlgoithmSettings);
      break;
    case MEMETIC:
      algorithm = algorithmFactory.createTspMemeticAlgorithm(this.getProblemSeed(), problemSize,
          evoAlgoithmSettings);
      break;
    case NEAREST_NEIGHBOUR:
      algorithm = algorithmFactory.createTspNnAlgorithm(this.getProblemSeed(), problemSize);
      break;
    default:
      return null;
    }
    final TSPSolutionView tspv = new TSPSolutionView();
    return new SimulationPresenter<Tour>(algorithm, tspv);
  }

  /**
   * Creates a simulation presenter from the settings provided.
   *
   * @return a simulation presenter from the settings provided.
   */
  public SimulationPresenter<?> createSimulationPresenter() {
    SimulationPresenter<?> simulationPresenter;

    switch (getProblemType()) {
    case PSEUDO_BOOLEAN_LINEAR:
      simulationPresenter = this.createNewPblSimulationWindow();
      break;
    case PSEUDO_BOOLEAN_QUADRATIC:
      simulationPresenter = this.createNewPbqSimulationWindow();
      break;
    case EUCLID_TSP:
      simulationPresenter = this.createNewTspSimulationWindow();
      break;
    default:
      simulationPresenter = null;
    }
    return simulationPresenter;
  }

  /**
   * Gets the algorithm type of the builder.
   *
   * @return the algorithm type of the builder
   */
  public AlgorithmType getAlgorithmType() {
    return algorithmType;
  }

  /**
   * Gets the evolutionary algorithm settings of the builder.
   *
   * @return the evolutionary algorithm settings of the builder
   */
  public EvoAlgorithmSettings getEvoAlgorithmSettings() {
    return evoAlgoithmSettings;
  }

  /**
   * Gets the problem seed for the builder.
   *
   * @return the problems seed for the builder
   */
  public long getProblemSeed() {
    return problemSeed;
  }

  /**
   * Gets the problem size for the builder.
   *
   * @return the problem size for the builder
   */
  public int getProblemSize() {
    return problemSize;
  }

  /**
   * Gets the problem type of the builder.
   *
   * @return the problem type of the builder
   */
  public ProblemType getProblemType() {
    return problemType;
  }

  /**
   * Gets the problem seed of the builder.
   *
   * @return the problem seed of the builder
   */
  public boolean useProblemSeed() {
    return useProblemSeed;
  }

  /**
   * Writes the current builder out to the file provided.
   *
   * @param file
   *          the file to write the builder out to
   */
  public void writeSettingsToFile(final File file) {
    JsonWriter jsonWriter = null;
    try {
      jsonWriter = new JsonWriter(new FileWriter(file));
    } catch (final IOException e1) {
      e1.printStackTrace();
    }
    if (jsonWriter != null) {
      final Gson gson = new Gson();
      gson.toJson(this, SimulationPresenterBuilder.class, jsonWriter);
    }
  }
}