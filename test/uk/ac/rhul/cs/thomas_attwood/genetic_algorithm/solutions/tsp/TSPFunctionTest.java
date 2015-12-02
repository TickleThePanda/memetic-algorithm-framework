package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TSPFunctionTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void actualValue_simpleFitnessFunction$randomOrder_CorrectFitness() {

    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final EuclidPoint[] cities = new EuclidPoint[] { city1, city2, city3, city4 };

    final EuclidTspFunction function = new EuclidTspFunction(cities);

    final TspTour testSolution =
        new TspTour(TSPSolutionTest.generateTSPSolution(4), function);

    int sum = 0;

    for (int i = 0; i < testSolution.size(); i++) {
      final EuclidPoint cityA = cities[testSolution.get(i)];
      final EuclidPoint cityB =
          cities[testSolution.get((i + 1) % testSolution.size())];
      sum += cityA.distance(cityB);
    }

    assertEquals(sum, function.actualValue(testSolution));
  }

  @Test
  public void actualValue_simpleFitnessFunction$simpleOrder_CorrectFitness() {

    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final EuclidTspFunction function =
        new EuclidTspFunction(new EuclidPoint[] { city1, city2, city3, city4 });

    assertEquals(400,
        function.actualValue(new TspTour(new int[] { 0, 1, 2, 3 }, function)));
  }

  @Test
  public void equals_simpleCities_equal() {
    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final EuclidPoint[] cities = new EuclidPoint[] { city1, city2, city3, city4 };

    final EuclidTspFunction function = new EuclidTspFunction(cities);
    final EuclidTspFunction function2 = new EuclidTspFunction(cities);

    assertEquals(function, function2);
  }

  @Test
  public void equals_simpleCitiesDifferentObjects_equal() {
    final EuclidPoint city11 = new EuclidPoint(0, 0);
    final EuclidPoint city12 = new EuclidPoint(0, 100);
    final EuclidPoint city13 = new EuclidPoint(100, 100);
    final EuclidPoint city14 = new EuclidPoint(100, 0);

    final EuclidPoint city21 = new EuclidPoint(0, 0);
    final EuclidPoint city22 = new EuclidPoint(0, 100);
    final EuclidPoint city23 = new EuclidPoint(100, 100);
    final EuclidPoint city24 = new EuclidPoint(100, 0);

    final EuclidPoint[] cities1 = new EuclidPoint[] { city11, city12, city13, city14 };
    final EuclidPoint[] cities2 = new EuclidPoint[] { city21, city22, city23, city24 };

    final EuclidTspFunction function1 = new EuclidTspFunction(cities1);
    final EuclidTspFunction function2 = new EuclidTspFunction(cities2);

    assertEquals(function1, function2);
  }

  @Test
  public void evaluateFitness_simpleFitnessFunction$randomOrder_CorrectFitness() {

    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final int maxDist = (int) Math.sqrt(100 * 100 + 100 * 100);
    final int totalMax = maxDist * 4;

    final EuclidPoint[] cities = new EuclidPoint[] { city1, city2, city3, city4 };

    final EuclidTspFunction function = new EuclidTspFunction(cities);

    final TspTour testSolution =
        new TspTour(TSPSolutionTest.generateTSPSolution(4), function);

    int sum = 0;

    for (int i = 0; i < testSolution.size(); i++) {
      final EuclidPoint cityA = cities[testSolution.get(i)];
      final EuclidPoint cityB =
          cities[testSolution.get((i + 1) % testSolution.size())];
      sum += cityA.distance(cityB);
    }

    assertEquals(totalMax - sum, function.evaluateFitness(testSolution));
  }

  @Test
  public void evaluateFitness_simpleFitnessFunction$simpleOrder_CorrectFitness() {

    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final int maxDist = (int) Math.sqrt(100 * 100 + 100 * 100);
    final int totalMax = maxDist * 4;

    final EuclidTspFunction function =
        new EuclidTspFunction(new EuclidPoint[] { city1, city2, city3, city4 });

    assertEquals(totalMax - 400, function.evaluateFitness(new TspTour(
        new int[] { 0, 1, 2, 3 }, function)));
  }

  @Test
  public void getEuclidPoint_simpleCities_correctCities() {

    final EuclidPoint city1 = new EuclidPoint(0, 0);
    final EuclidPoint city2 = new EuclidPoint(0, 100);
    final EuclidPoint city3 = new EuclidPoint(100, 100);
    final EuclidPoint city4 = new EuclidPoint(100, 0);

    final EuclidPoint[] cities = new EuclidPoint[] { city1, city2, city3, city4 };

    final EuclidTspFunction function = new EuclidTspFunction(cities);

    for (int i = 0; i < cities.length; i++) {
      assertEquals(cities[i], function.getCity(i));
    }
  }

  @Before
  public void setUp() throws Exception {
  }

}
