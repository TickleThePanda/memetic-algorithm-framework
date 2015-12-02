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

public final class Crossover_fixed_size_100_1000 {

  private Crossover_fixed_size_100_1000() {
  }

  public static void main(final String[] args) {

    // build test set
    final ArrayList<AlgorithmRunner<?>> algorithmList = new ArrayList<AlgorithmRunner<?>>();

    final EuclidTspFunctionFactory funcFactory = new EuclidTspFunctionFactory(PROBLEM_SIZE);

    for (int j = 0; j < N_PROBLEMS; j++) {
      final EuclidTspFunction masterfunction = funcFactory.generateFunction();
      for (int i = 0; i < 10; i++) {
        final EuclidTspFunction copyFunction = masterfunction.copy();

        final TspTourFactory solFactory1 = new TspTourFactory(copyFunction);

        final TspLiMulti twoOpt = new TspLiMulti(LocalImprovementType.SINGLE_RANDOM);

        final EvoAlgorithmSettings settings =
            new EvoAlgorithmSettings(POOL_SIZE, ELITE_SIZE, CROSS_SIZE, MUTATION_RATE,
                STEPS_DUPLICATES);

        final MemeticAlgorithm<TspTour> tspGa =
            new MemeticAlgorithm<TspTour>(settings, solFactory1, twoOpt);

        algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET, 100));
      }
    }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintCrossoverSize(true).setPrintGenerationCount(true)
            .build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/crossover-fixed-size100.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // simulation settings
  private static final int POOL_SIZE = 100;
  private static final int ELITE_SIZE = (int) (0.08f * POOL_SIZE);
  private static final int CROSS_SIZE = (int) (0.08f * POOL_SIZE);
  private static final float MUTATION_RATE = 0.1f;
  private static final int STEPS_DUPLICATES = 15;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 100;
  private static final int BUDGET = 20000;

  // other
  private static final int N_THREADS = 2;
}
