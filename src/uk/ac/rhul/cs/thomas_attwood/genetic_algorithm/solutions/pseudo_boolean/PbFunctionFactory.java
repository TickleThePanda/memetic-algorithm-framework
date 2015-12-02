package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitFuncFactory;

/**
 * Defines a Pseudo-Boolean function factory.
 *
 */
public interface PbFunctionFactory extends
    FitFuncFactory<PbSolution, PbFunction> {

  @Override
  PbFunction generateFunction();

}