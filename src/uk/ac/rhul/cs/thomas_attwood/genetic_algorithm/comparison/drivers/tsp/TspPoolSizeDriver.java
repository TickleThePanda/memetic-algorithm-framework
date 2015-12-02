package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspLiTwoOpt;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;

public final class TspPoolSizeDriver {

  private TspPoolSizeDriver() {
  }

  public static void main(final String[] args) {
    // build test set
    final ArrayList<AlgorithmRunner<?>> algorithmList =
        new ArrayList<AlgorithmRunner<?>>();

    final EuclidTspFunctionFactory funcFactory =
        new EuclidTspFunctionFactory(PROBLEM_SIZE);

    for (int j = 0; j < N_PROBLEMS; j++) {
      final EuclidTspFunction masterfunction =
          funcFactory.generateFunction();

      for (int currentPool = SMALLEST_POOL; currentPool < SMALLEST_POOL
          + N_TESTS * STEP_SIZE; currentPool += STEP_SIZE) {

        final EuclidTspFunction copyFunction = masterfunction.copy();

        final TspTourFactory solFactory1 = new TspTourFactory(copyFunction);

        final TspLiTwoOpt twoOpt =
            new TspLiTwoOpt(LocalImprovementType.SINGLE_RANDOM);

        final EvoAlgorithmSettings settings =
            new EvoAlgorithmSettings(currentPool, ELITE_SIZE, CROSSOVER_SIZE,
                MUTATION_RATE, STEPS_DUPLICATES);

        final MemeticAlgorithm<TspTour> tspGa =
            new MemeticAlgorithm<TspTour>(settings, solFactory1, twoOpt);

        algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET));
      }
    }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintPopulationSize(true)
            .setPrintGenerationCount(true).build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-pool.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  private static final int STEPS_DUPLICATES = 10000000;
  private static final int CROSSOVER_SIZE = 10;
  private static final int ELITE_SIZE = 20;
  private static final float MUTATION_RATE = 0.4f;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 50;
  private static final int BUDGET = 2000;

  protected static final int N_TESTS = 10;
  protected static final int SMALLEST_POOL = 50;
  protected static final int STEP_SIZE = 50;

  private static final int N_THREADS = 2;

  protected static final int SEED = 0;

}
