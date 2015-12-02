package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Runs a set of algorithms on a set number of different threads and outputs results to a file.
 */
public class ThreadedAlgorithms {

  /**
   * The default number of threads to use.
   */
  private static final int DEFAULT_NUMBER_OF_THREADS = 2;
  /**
   * The number of threads to use.
   */
  private final int numberOfThreads;
  /**
   * The algorithms to run.
   */
  private final List<AlgorithmRunner<?>> algorithms;
  /**
   * The logger to use to output the results of algorithms.
   */
  private final ComparisonLogger logger;
  /**
   * The ouput location of the results of the algorithms.
   */
  private final String outputLocation;

  /**
   * Creates a new ThreadedAlgorithms set that can be completed for comparison with the list of
   * {@link AlgorithmRunner}, the number of threads, the output location, and the default comparison
   * logger. The default logger is defined by {@link ComparisonLogger.Builder}
   * 
   * @param algorithmRunners
   *          the algorithms runners to run
   * @param numberOfthreads
   *          the number of threads that can be used at max
   * @param outputLocation
   *          the output location of the data relative to this directory
   */
  public ThreadedAlgorithms(
      final List<AlgorithmRunner<?>> algorithmRunners,
      final int numberOfthreads, String outputLocation) {
    this(new ComparisonLogger.Builder().build(), algorithmRunners,
        numberOfthreads, outputLocation);
  }

  /**
   * Creates a new ThreadedAlgorithms set that can be completed for comparison with the list of
   * {@link AlgorithmRunner}, the output location, the default comparison logger, and the default
   * number of threads. The default number of threads to use is
   * <code>DEFAULT_NUMBER_OF_THREADS</code>, and the default logger is defined by
   * {@link ComparisonLogger.Builder}.
   * 
   * @param algorithmRunners
   *          the algorithm runners to run
   * @param outputLocation
   *          the output location of the data relative to this directory
   */
  public ThreadedAlgorithms(
      final List<AlgorithmRunner<?>> algorithmRunners,
      String outputLocation) {
    this(algorithmRunners, DEFAULT_NUMBER_OF_THREADS, outputLocation);
  }

  /**
   * Creates a new ThreadedAlgorithms set that can be completed for comparison with the comparison
   * logger, the list of {@link AlgorithmRunner}, the number of threads, and the output location.
   * 
   * @param logger
   *          the logger to use to print the results to file
   * @param algorithmRunners
   *          the algorithms runners to run
   * @param numberOfthreads
   *          the number of threads that can be used at max
   * @param outputLocation
   *          the output location of the data relative to this directory
   */
  public ThreadedAlgorithms(ComparisonLogger logger,
      final List<AlgorithmRunner<?>> algorithmRunners,
      final int numberOfthreads, String outputLocation) {
    this.outputLocation = outputLocation;
    this.algorithms = algorithmRunners;
    this.numberOfThreads = numberOfthreads;
    this.logger = logger;
  }

  /**
   * Runs the algorithms sequentially on different threads, out of a thread pool.
   * 
   * @throws IOException
   *           when the output file cannot be written to.
   */
  public void run() throws IOException {
    File file = new File(outputLocation);
    Writer writer = new BufferedWriter(new FileWriter(file, true));

    writer.write(logger.getTitles());

    System.out.print(logger.getTitles());

    List<Future<CompletedAlgorithm>> list =
        new ArrayList<Future<CompletedAlgorithm>>();

    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

    for (AlgorithmRunner<?> al : algorithms) {
      list.add(executor.submit(al));
    }

    for (int i = 0; i < list.size(); i++) {
      Future<CompletedAlgorithm> future = list.get(i);
      try {
        writer.write(logger.getValues(future.get()));
        System.out.print(logger.getValues(future.get()));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    writer.close();

    executor.shutdown();
  }

}
