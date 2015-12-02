package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;

public final class Crossover_0to19_step10_size100_noLocImprov_Driver {

  private Crossover_0to19_step10_size100_noLocImprov_Driver() {
  }

  public static void main(final String[] args) {

    // build test set
    final ArrayList<AlgorithmRunner<?>> algorithmList = new ArrayList<AlgorithmRunner<?>>();

      final EuclidTspFunctionFactory funcFactory = new EuclidTspFunctionFactory(PROBLEM_SIZE);

      for (int j = 0; j < N_PROBLEMS; j++) {
        final EuclidTspFunction masterfunction = funcFactory.generateFunction();

        for (int currentCrossover = SMALLEST_CROSSOVER; currentCrossover < SMALLEST_CROSSOVER
            + N_TESTS * STEP_SIZE; currentCrossover += STEP_SIZE) {

          final EuclidTspFunction copyFunction = masterfunction.copy();

          final TspTourFactory solFactory1 = new TspTourFactory(copyFunction, false);

          final EvoAlgorithmSettings settings =
              new EvoAlgorithmSettings(POOL_SIZE, ELITE_SIZE, currentCrossover, MUTATION_RATE,
                  STEPS_DUPLICATES);

          final GeneticAlgorithm<TspTour> tspGa =
              new GeneticAlgorithm<TspTour>(settings, solFactory1);

          algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET, 100));
        }
      }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintCrossoverSize(true).setPrintGenerationCount(true)
            .build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS, "output/tsp-crossover-0to90-step1-size100-noLocImprov.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // simulation settings
  private static final int POOL_SIZE = 100;
  private static final int ELITE_SIZE = 1;
  private static final float MUTATION_RATE = 0.3f;
  private static final int STEPS_DUPLICATES = 100;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 500;
  private static final int BUDGET = 20000;

  // mutable settings
  protected static final int SMALLEST_CROSSOVER = 0;
  protected static final int STEP_SIZE = 1;
  protected static final int N_TESTS = 20;

  // other
  private static final int N_THREADS = 2;
}
