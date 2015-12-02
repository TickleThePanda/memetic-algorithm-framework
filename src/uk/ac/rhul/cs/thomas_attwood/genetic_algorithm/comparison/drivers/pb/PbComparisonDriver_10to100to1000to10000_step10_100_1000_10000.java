package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.pb;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbLocImprov;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolution;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolutionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.QuadraticPbFuncFactory;

public final class PbComparisonDriver_10to100to1000to10000_step10_100_1000_10000 {

  public static void main(final String[] args) {

    doExperiment(100, 10, 0);

    doExperiment(1000, 10, 0);

    doExperiment(10000, 2, 0);
    
    doExperiment(10000, 2, 2);
    
    doExperiment(10000, 2, 4);
    
    doExperiment(10000, 2, 6);
    
    doExperiment(10000, 2, 8);
  }

  private static void doExperiment(int order, int number, int start) {
    final ComparisonLogger cl = new ComparisonLogger.Builder().build();

    final ArrayList<AlgorithmRunner<?>> algoList = new ArrayList<AlgorithmRunner<?>>();

    int firstValue = order/10 * (start + 1);
    int lastValue = firstValue + order/10 * number;
    
    for (int s = firstValue; s < lastValue; s += order/10) {
      final QuadraticPbFuncFactory funcFactory = new QuadraticPbFuncFactory(s);

      for (int i = 0; i < N_PROBLEMS; i++) {
        final PbFunction function = funcFactory.generateFunction();
        final PbFunction functionCopy = function.copy();

        final PbSolutionFactory factory1 = new PbSolutionFactory(function);
        final PbSolutionFactory factory2 = new PbSolutionFactory(functionCopy);

        final GeneticAlgorithm<PbSolution> genetic = new GeneticAlgorithm<PbSolution>(factory1);

        final MemeticAlgorithm<PbSolution> memetic =
            new MemeticAlgorithm<PbSolution>(factory2, new PbLocImprov());

        algoList.add(new AlgorithmRunner<PbSolution>(genetic, BUDGET_MILLIS, REPEATS_UNTIL_QUIT));
        algoList.add(new AlgorithmRunner<PbSolution>(memetic, BUDGET_MILLIS, REPEATS_UNTIL_QUIT));
      }
    }

    final ThreadedAlgorithms tar =
        new ThreadedAlgorithms(cl, algoList, N_THREADS, "output/pb_comparison.csv");
    try {
      tar.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private PbComparisonDriver_10to100to1000to10000_step10_100_1000_10000() {
  }

  private static final int BUDGET_MILLIS = 120 * 1000;
  private static final int N_PROBLEMS = 10;
  private static final int N_THREADS = 2;
  private static final int REPEATS_UNTIL_QUIT = 100;
}
