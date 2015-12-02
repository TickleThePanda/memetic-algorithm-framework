package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspLiMulti;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;

public final class Crossover_0to24_step1_size100_pop_50_250_step50_Driver {

  private Crossover_0to24_step1_size100_pop_50_250_step50_Driver() {
  }

  public static void main(final String[] args) {

    for (int i = 0; i < 5; i++)
      doExperiment();

  }

  private static void doExperiment() {
    // build test set
    final ArrayList<AlgorithmRunner<?>> algorithmList = new ArrayList<AlgorithmRunner<?>>();

    final EuclidTspFunctionFactory funcFactory = new EuclidTspFunctionFactory(PROBLEM_SIZE);

    for (int j = 0; j < N_PROBLEMS; j++) {
      final EuclidTspFunction masterfunction = funcFactory.generateFunction();

      for (int currentPopulation = SMALLEST_POPULATION; currentPopulation < SMALLEST_POPULATION
          + POPULATION_N_STEPS * POPULATION_STEP_SIZE; currentPopulation += POPULATION_STEP_SIZE) {

        for (int currentCrossover = SMALLEST_CROSSOVER; currentCrossover < SMALLEST_CROSSOVER
            + CROSSOVER_N_STEPS * CROSSOVER_STEP_SIZE; currentCrossover += CROSSOVER_STEP_SIZE) {

          final EuclidTspFunction copyFunction = masterfunction.copy();

          final TspTourFactory solFactory1 = new TspTourFactory(copyFunction);

          final TspLiMulti twoOpt = new TspLiMulti(LocalImprovementType.SINGLE_RANDOM);

          final EvoAlgorithmSettings settings =
              new EvoAlgorithmSettings(currentPopulation, ELITE_SIZE, currentCrossover,
                  MUTATION_RATE, STEPS_DUPLICATES);

          final MemeticAlgorithm<TspTour> tspGa =
              new MemeticAlgorithm<TspTour>(settings, solFactory1, twoOpt);

          algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET, 100));
        }
      }
    }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintCrossoverSize(true).setPrintGenerationCount(false)
            .setPrintPopulationSize(true).build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-crossover-0-18-step1-size100-pop-50-250-step-50.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // simulation settings
  private static final int ELITE_SIZE = 1;
  private static final float MUTATION_RATE = 0.3f;
  private static final int STEPS_DUPLICATES = 100;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 50;
  private static final int BUDGET = 20000;

  // mutable settings
  protected static final int SMALLEST_CROSSOVER = 0;
  protected static final int CROSSOVER_STEP_SIZE = 1;
  protected static final int CROSSOVER_N_STEPS = 25;

  protected static final int SMALLEST_POPULATION = 50;
  protected static final int POPULATION_STEP_SIZE = 50;
  protected static final int POPULATION_N_STEPS = 5;

  // other
  private static final int N_THREADS = 2;
}
