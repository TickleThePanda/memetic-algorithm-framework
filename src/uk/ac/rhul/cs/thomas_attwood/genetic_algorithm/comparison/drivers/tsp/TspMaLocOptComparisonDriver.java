package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspLiTwoOpt;

public final class TspMaLocOptComparisonDriver {

  private TspMaLocOptComparisonDriver() {
  }

  public static void main(final String[] args) {

    final ArrayList<AlgorithmRunner<?>> algorithmList =
        new ArrayList<AlgorithmRunner<?>>();

    final ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintLocalImprovementType(true)
            .build();

    for (int currentSize = SMALLEST_PROBLEM; currentSize < SMALLEST_PROBLEM
        + N_SIZE_PROBLEMS * STEP_SIZE; currentSize += STEP_SIZE) {

      final int budget = (int) BUDGET;

      final EuclidTspFunctionFactory funcFactory =
          new EuclidTspFunctionFactory(currentSize);
      for (int j = 0; j < N_PROBLEMS; j++) {

        final EuclidTspFunction tspFunction1 =
            funcFactory.generateFunction();
        final EuclidTspFunction tspFunction2 = tspFunction1.copy();
        final EuclidTspFunction tspFunction3 = tspFunction1.copy();

        final TspTourFactory solFactory1 =
            new TspTourFactory(SEED, tspFunction1);
        final TspTourFactory solFactory2 =
            new TspTourFactory(SEED, tspFunction2);
        final TspTourFactory solFactory3 =
            new TspTourFactory(SEED, tspFunction3);

        final TspLiTwoOpt locImprov1 =
            new TspLiTwoOpt(LocalImprovementType.SINGLE_RANDOM);
        final TspLiTwoOpt locImprov2 =
            new TspLiTwoOpt(LocalImprovementType.SINGLE_ORDERED);
        final TspLiTwoOpt locImprov3 =
            new TspLiTwoOpt(LocalImprovementType.NEIGHBOURHOOD);

        final MemeticAlgorithm<TspTour> tspMa1 =
            new MemeticAlgorithm<TspTour>(
                EvoAlgorithmSettings.DEFAULT_SETTINGS_TSP, solFactory1,
                locImprov1);

        final MemeticAlgorithm<TspTour> tspMa2 =
            new MemeticAlgorithm<TspTour>(
                EvoAlgorithmSettings.DEFAULT_SETTINGS_TSP, solFactory2,
                locImprov2);

        final MemeticAlgorithm<TspTour> tspMa3 =
            new MemeticAlgorithm<TspTour>(
                EvoAlgorithmSettings.DEFAULT_SETTINGS_TSP, solFactory3,
                locImprov3);

        algorithmList.add(new AlgorithmRunner<TspTour>(tspMa1, budget,
            GENERATIONS_REPEAT_BEFORE_END));
        algorithmList.add(new AlgorithmRunner<TspTour>(tspMa2, budget,
            GENERATIONS_REPEAT_BEFORE_END));
        algorithmList.add(new AlgorithmRunner<TspTour>(tspMa3, budget,
            GENERATIONS_REPEAT_BEFORE_END));

      }
    }

    Collections.reverse(algorithmList);

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-ma-locimprov.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static final int GENERATIONS_REPEAT_BEFORE_END = 100;

  protected static final double BUDGET = 10000;
  protected static final int N_PROBLEMS = 10;
  protected static final int N_SIZE_PROBLEMS = 10;
  private static final int N_THREADS = 2;
  protected static final int SEED = 0;
  protected static final int SMALLEST_PROBLEM = GENERATIONS_REPEAT_BEFORE_END;

  protected static final int STEP_SIZE = GENERATIONS_REPEAT_BEFORE_END;
}
