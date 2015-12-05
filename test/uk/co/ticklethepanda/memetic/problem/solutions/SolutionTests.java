package uk.co.ticklethepanda.memetic.problem.solutions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PbTests;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ PbTests.class, TestSuite.class })
public class SolutionTests {

}
