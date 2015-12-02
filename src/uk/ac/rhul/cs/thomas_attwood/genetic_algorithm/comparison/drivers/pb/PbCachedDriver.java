package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.pb;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolution;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolutionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.QuadraticPbFuncFactory;

public final class PbCachedDriver {

  public static void main(final String[] args) {

    final ArrayList<AlgorithmRunner<?>> algoList =
        new ArrayList<AlgorithmRunner<?>>();

    final QuadraticPbFuncFactory funcFactory =
        new QuadraticPbFuncFactory(PB_SIZE);

    final ComparisonLogger cl =
        new ComparisonLogger.Builder().setPrintCachedFitness(true).build();

    for (int i = 0; i < N_PROBLEMS; i++) {
      final PbFunction pbCacheFunction = funcFactory.generateFunction();
      final PbFunction pbNoCacheFunction = pbCacheFunction.copy();

      final PbSolutionFactory pbCacheSolutionFactory =
          new PbSolutionFactory(SEED, pbCacheFunction);
      final PbSolutionFactory pbNoCacheSolutionFactory =
          new PbSolutionFactory(SEED, pbNoCacheFunction);

      final GeneticAlgorithm<PbSolution> gaCache =
          new GeneticAlgorithm<PbSolution>(SEED, pbCacheSolutionFactory);
      final GeneticAlgorithm<PbSolution> gaNoCache =
          new GeneticAlgorithm<PbSolution>(SEED, pbNoCacheSolutionFactory);

      algoList.add(new AlgorithmRunner<PbSolution>(gaCache, BUDGET_MILLIS,
          REPEATS_UNTIL_QUIT));
      algoList.add(new AlgorithmRunner<PbSolution>(gaNoCache, BUDGET_MILLIS,
          REPEATS_UNTIL_QUIT));
    }

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(cl, algoList, N_THREADS, "output/pb-cached.csv");
    try {
      tar.run();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private PbCachedDriver() {
  }

  private static final int BUDGET_MILLIS = 120000;
  private static final int N_PROBLEMS = 10;
  private static final int N_THREADS = 3;
  private static final int PB_SIZE = 100;
  private static final int REPEATS_UNTIL_QUIT = 10;

  protected static final long SEED = 0;
}
