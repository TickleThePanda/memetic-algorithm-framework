package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbTests;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TSPTests;

@RunWith(Suite.class)
@SuiteClasses({ PbTests.class, TSPTests.class })
public class SolutionTests {

}
