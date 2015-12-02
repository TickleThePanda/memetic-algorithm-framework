package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;

public final class TspGaElitistSizeDriver {

  private TspGaElitistSizeDriver() {
  }

  public static void main(final String[] args) {
    final ArrayList<AlgorithmRunner<?>> algorithmList =
        new ArrayList<AlgorithmRunner<?>>();

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintEliteSize(true).build();

    for (int currentElite = SMALLEST_ELITE; currentElite < SMALLEST_ELITE
        + N_TESTS * STEP_SIZE; currentElite += STEP_SIZE) {

      final int budget = 10000;

      final EuclidTspFunctionFactory funcFactory =
          new EuclidTspFunctionFactory(PROBLEM_SIZE);
      for (int j = 0; j < N_PROBLEMS; j++) {

        final EuclidTspFunction tspFunction1 =
            funcFactory.generateFunction();

        final TspTourFactory solFactory1 =
            new TspTourFactory(SEED, tspFunction1);

        final GeneticAlgorithm<TspTour> tspGa =
            new GeneticAlgorithm<TspTour>(SEED, new EvoAlgorithmSettings(
                POOL_SIZE, currentElite, 0, MUTATION_RATE, STEPS_DUPLICATES),
                solFactory1);

        algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, budget));
      }
    }

    Collections.reverse(algorithmList);

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-elitist.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static final float MUTATION_RATE = 0.3f;
  protected static final int N_PROBLEMS = 10;

  protected static final int N_TESTS = 9;

  private static final int N_THREADS = 2;
  private static final int POOL_SIZE = 50;
  protected static final int PROBLEM_SIZE = 100;

  protected static final int SEED = 0;
  protected static final int SMALLEST_ELITE = 21;
  protected static final int STEP_SIZE = 1;

  private static final int STEPS_DUPLICATES = 100;
}
