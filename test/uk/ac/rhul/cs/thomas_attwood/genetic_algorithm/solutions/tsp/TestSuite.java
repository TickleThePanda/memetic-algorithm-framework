package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FunctionFactoryTest.class, FunctionTest.class,
    SolutionFactoryTest.class, SolutionTest.class, TwoOptTest.class })
public class TestSuite {

}
