package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TSPFunctionFactoryTest.class, TSPFunctionTest.class,
    TSPSolutionFactoryTest.class, TSPSolutionTest.class, TSPTwoOptTest.class })
public class TSPTests {

}
