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

public final class TspMutRateDriver {

  private TspMutRateDriver() {
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

      for (float currentMutation = SMALLEST_MUTATION; currentMutation < SMALLEST_MUTATION
          + N_TESTS * STEP_SIZE; currentMutation += STEP_SIZE) {

        final EuclidTspFunction copyFunction = masterfunction.copy();

        final TspTourFactory solFactory1 = new TspTourFactory(copyFunction);

        final TspLiTwoOpt twoOpt =
            new TspLiTwoOpt(LocalImprovementType.SINGLE_RANDOM);

        final EvoAlgorithmSettings settings =
            new EvoAlgorithmSettings(POOL_SIZE, ELITE_SIZE, CROSSOVER_SIZE,
                currentMutation, STEPS_DUPLICATES);

        final MemeticAlgorithm<TspTour> tspGa =
            new MemeticAlgorithm<TspTour>(settings, solFactory1, twoOpt);

        algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET));
      }
    }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintMutationRate(true)
            .setPrintGenerationCount(true).build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-mutation.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static final int POOL_SIZE = 100;
  private static final int STEPS_DUPLICATES = 10000000;
  private static final int CROSSOVER_SIZE = 10;
  private static final int ELITE_SIZE = 90;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 1000;
  private static final int BUDGET = 2000;

  protected static final float N_TESTS = 10;
  protected static final float SMALLEST_MUTATION = 0.0f;
  protected static final float STEP_SIZE = 0.1f;

  private static final int N_THREADS = 2;

  protected static final int SEED = 0;

}
