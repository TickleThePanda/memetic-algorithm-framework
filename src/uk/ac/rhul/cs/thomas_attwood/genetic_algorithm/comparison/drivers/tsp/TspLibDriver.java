package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.drivers.tsp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.AlgorithmRunner;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ComparisonLogger;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison.ThreadedAlgorithms;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.TspNnAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspReader;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspLiMulti;

public class TspLibDriver {

  final static int BUDGET_SECONDS = 10000;
  final static int BUDGET_MILLISECONDS = BUDGET_SECONDS * 1000;

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      doExperiment();
    }
  }

  private static void doExperiment() {
    List<AlgorithmRunner<?>> algoRunners = new ArrayList<AlgorithmRunner<?>>();
    try {
      Files.walk(Paths.get("./tsplib/")).forEach(
          filePath -> {
            if (Files.isRegularFile(filePath)) {
              TspReader reader = new TspReader();

              TspFunction<?> problem = reader.readProblem(filePath.toFile());

              if (problem != null) {
                for (int i = 0; i < 1; i++) {

                  if (problem != null) {
                    TspFunction<?> copy = (TspFunction<?>) problem.copy();
                    final int population = (int) Math.sqrt(problem.size()) * 25;
                    final float mutationRate = 0.1f;
                    final int eliteSize = (int) ((float) population * 0.08f);
                    final int crossoverSize = (int) ((float) population * 0.08f);
                    final int remTimer = 15;

                    MemeticAlgorithm<TspTour> tour =
                        new MemeticAlgorithm<TspTour>(new EvoAlgorithmSettings.Builder()
                            .setPoolSize(population).setMutationRate(mutationRate)
                            .setEliteSize(eliteSize).setCrossoverSize(crossoverSize)
                            .setStepsDuplicates(remTimer).build(), new TspTourFactory(copy),
                            new TspLiMulti());

                    algoRunners.add(new AlgorithmRunner<TspTour>(tour, BUDGET_MILLISECONDS, 400));
                  }
                  if (problem != null) {
                    TspFunction<?> copy = (TspFunction<?>) problem.copy();
                    final int population = (int) Math.sqrt(problem.size()) * 25;
                    final float mutationRate = 0.1f;
                    final int eliteSize = (int) ((float) population * 0.08f);
                    final int crossoverSize = (int) (population * 0.08f);
                    final int remTimer = 15;

                    GeneticAlgorithm<TspTour> tour =
                        new GeneticAlgorithm<TspTour>(new EvoAlgorithmSettings.Builder()
                            .setPoolSize(population).setMutationRate(mutationRate)
                            .setEliteSize(eliteSize).setCrossoverSize(crossoverSize)
                            .setStepsDuplicates(remTimer).build(), new TspTourFactory(copy));

                    algoRunners.add(new AlgorithmRunner<TspTour>(tour, BUDGET_MILLISECONDS, 400));
                  }
                  if (problem != null) {
                    TspFunction<?> copy = (TspFunction<?>) problem.copy();
                    TspNnAlgorithm tour = new TspNnAlgorithm(copy);

                    algoRunners.add(new AlgorithmRunner<TspTour>(tour, BUDGET_MILLISECONDS, 400));
                  }
                }
              }
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }

    Collections.sort(algoRunners, new Comparator<AlgorithmRunner<?>>() {

      @Override
      public int compare(AlgorithmRunner<?> o1, AlgorithmRunner<?> o2) {
        return o1.getSize() - o2.getSize();
      }

    });

    ThreadedAlgorithms ta =
        new ThreadedAlgorithms(new ComparisonLogger.Builder().setPrintProblemSize(false)
            .setPrintAlgorithmType(true).setPrintGenerationCount(true).build(), algoRunners, 2,
            "output/tspLib-600.csv");
    try {
      ta.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
