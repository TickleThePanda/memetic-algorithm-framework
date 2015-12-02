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

public final class Crossover_0to19_step1_size100_mut_0pf_1pf_step5_Driver {

  private Crossover_0to19_step1_size100_mut_0pf_1pf_step5_Driver() {
  }

  public static void main(final String[] args) {

    for (int i = 0; i < 10; i++) {
      doExperiment();
    }

  }

  private static void doExperiment() {
    // build test set
    final ArrayList<AlgorithmRunner<?>> algorithmList = new ArrayList<AlgorithmRunner<?>>();

    final EuclidTspFunctionFactory funcFactory = new EuclidTspFunctionFactory(PROBLEM_SIZE);

    for (int j = 0; j < N_PROBLEMS; j++) {
      final EuclidTspFunction masterfunction = funcFactory.generateFunction();

      for (float currentMut = SMALLEST_MUT; currentMut < SMALLEST_MUT + MUT_N_STEPS * MUT_STEP_SIZE; currentMut +=
          MUT_STEP_SIZE) {

        for (int currentCrossover = SMALLEST_CROSSOVER; currentCrossover < SMALLEST_CROSSOVER
            + CROSSOVER_N_STEPS * CROSSOVER_STEP_SIZE; currentCrossover += CROSSOVER_STEP_SIZE) {

          final EuclidTspFunction copyFunction = masterfunction.copy();

          final TspTourFactory solFactory1 = new TspTourFactory(copyFunction);

          final TspLiMulti twoOpt = new TspLiMulti(LocalImprovementType.SINGLE_RANDOM);

          final EvoAlgorithmSettings settings =
              new EvoAlgorithmSettings.Builder().setCrossoverSize(currentCrossover)
                  .setEliteSize(ELITE_SIZE).setPoolSize(POPULATION_SIZE)
                  .setMutationRate(currentMut).setStepsDuplicates(STEPS_DUPLICATES).build();

          final MemeticAlgorithm<TspTour> tspGa =
              new MemeticAlgorithm<TspTour>(settings, solFactory1, twoOpt);

          algorithmList.add(new AlgorithmRunner<TspTour>(tspGa, BUDGET, 100));
        }
      }
    }

    ComparisonLogger logger =
        new ComparisonLogger.Builder().setPrintCrossoverSize(true).setPrintGenerationCount(false)
            .setPrintPopulationSize(false).setPrintMutationRate(true).setPrintEliteSize(false)
            .setPrintProblemSize(false).setPrintAlgorithmType(false).build();

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(logger, algorithmList, N_THREADS,
            "output/tsp-crossover-0-24-step2-size100-mut-0p-1p-step-0p02.csv");
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
  private static final int POPULATION_SIZE = 100;

  protected static final int PROBLEM_SIZE = 100;

  protected static final int N_PROBLEMS = 10;
  private static final int BUDGET = 20000;

  // mutable settings
  protected static final int SMALLEST_CROSSOVER = 0;
  protected static final int CROSSOVER_STEP_SIZE = 2;
  protected static final int CROSSOVER_N_STEPS = 25;

  protected static final float SMALLEST_MUT = 0.0f;
  protected static final float MUT_STEP_SIZE = 0.05f;
  protected static final float MUT_N_STEPS = 20;

  // other
  private static final int N_THREADS = 2;
}
