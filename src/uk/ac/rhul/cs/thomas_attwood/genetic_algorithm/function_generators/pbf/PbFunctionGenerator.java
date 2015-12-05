package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.function_generators.pbf;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.function_generators.ProblemGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.BitString;

/**
 * Defines a Pseudo-Boolean function factory.
 *
 */
public interface PbFunctionGenerator extends
    ProblemGenerator<BitString, PsuedoBooleanFunction> {

  @Override
  PsuedoBooleanFunction generateFunction();

}