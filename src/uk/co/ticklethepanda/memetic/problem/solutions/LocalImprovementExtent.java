package uk.co.ticklethepanda.memetic.problem.solutions;

/**
 * Defines the extent of local improvement that can be completed ordered from most expensive, on
 * average, to least expensive.
 *
 */
public enum LocalImprovementExtent {

  /**
   * This type expects the local improvement to use the index of 0 as a starting point for the
   * improvement to iterate from there. The best improvement found is the subjected to the same
   * process.
   */
  COMPLETE,
  /**
   * This type expects the local improvement to use the index of 0 as a starting point for the
   * improvement and to iterate from there. The best improvement found is returned.
   */
  NEIGHBOURHOOD,
  /**
   * This type expects the local improvement to use the index of 0 as a starting point for the
   * improvement and to iterate from there, until an improvement is found.
   */
  SINGLE_ORDERED,
  /**
   * This type expects the local improvement to use the index of 0 as a starting point for the
   * improvement and to iterate from there, until an improvement is found.
   */
  SINGLE_RANDOM
}