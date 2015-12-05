package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.pbf;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.ProblemGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;

/**
 * Defines a Pseudo-Boolean function factory.
 *
 */
public interface PbFunctionGenerator extends
    ProblemGenerator<BitString, PsuedoBooleanFunction> {

  @Override
  PsuedoBooleanFunction generateFunction();

}