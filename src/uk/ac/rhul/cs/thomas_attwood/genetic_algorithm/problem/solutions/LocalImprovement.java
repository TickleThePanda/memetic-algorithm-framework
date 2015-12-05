package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions;

/**
 * Contains methods to improve solutions.
 * 
 * @param <E>
 *          the type of solution to be improved.
 */
public interface LocalImprovement<E extends Solution<E>> {

  /**
   * Gets an improved solution from the solution, using the extent of improvement expected.
   * 
   * @param solution
   *          the solution to be improved upon
   * @return the improved solution
   */
  E getImprovedSolution(final E solution);

  /**
   * Gets the type of improvement to be completed.
   * 
   * @return the type of improvement to be completed
   */
  LocalImprovementExtent getType();

  /**
   * Gets an improved solution from the solution, using a single random improvement. This selects a
   * random starting point for the random improvement and iterates from there (looping around to the
   * start of the problem), until an improvement is found. If no improvement is found, then the
   * original is returned.
   * 
   * @param solution
   *          the solution to be improved upon
   * @return the improved solution or the original if no improvement can be found
   */
  E getSingleRandomImprovedSolution(final E original);

  /**
   * Gets an improved solution from the solution, using a single ordered improvement. This uses the
   * index of 0 as a starting point for the improvement and iterates from there, until an
   * improvement is found. If no improvement is found, then the original is returned.
   * 
   * @param solution
   *          the solution to be improved upon
   * @return the improved solution or the original if no improvement can be found
   */
  E getSingleOrderedImprovedSolution(final E original);

  /**
   * Gets an improved solution from the solution, using a neighbourhood improvement. This uses the
   * index of 0 as a starting point for the improvement and iterates from there. The best
   * improvement found is returned. If no improvement is found, then the original is returned.
   * 
   * @param solution
   *          the solution to be improved upon
   * @return the improved solution or the original if no improvement can be found
   */
  E getNeighbourhoodImprovedSolution(final E original);

  /**
   * Gets an improved solution from the solution, using a single ordered improvement. This uses the
   * index of 0 as a starting point for the improvement and iterates from there. The best
   * improvement found is the subjected to the same process. If no improvement is found, then the
   * original is returned.
   * 
   * @param solution
   *          the solution to be improved upon
   * @return the improved solution or the original if no improvement can be found
   */
  E getCompleteImprovedSolution(final E original);

}
