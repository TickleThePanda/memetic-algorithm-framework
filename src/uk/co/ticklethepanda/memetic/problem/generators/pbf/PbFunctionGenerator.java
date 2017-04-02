package uk.co.ticklethepanda.memetic.problem.generators.pbf;

import uk.co.ticklethepanda.memetic.problem.generators.ProblemGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.BitString;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PsuedoBooleanFunction;

/**
 * Defines a Pseudo-Boolean function factory.
 *
 */
public interface PbFunctionGenerator extends ProblemGenerator<BitString, PsuedoBooleanFunction> {

  @Override
  PsuedoBooleanFunction generateFunction();

}