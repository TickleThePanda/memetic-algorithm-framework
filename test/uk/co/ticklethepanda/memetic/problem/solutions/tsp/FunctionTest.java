package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FunctionTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void actualValue_simpleFitnessFunction$randomOrder_CorrectFitness() {

    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final City.Euclidian[] cities = new City.Euclidian[] { city1, city2, city3, city4 };

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(Arrays.asList(cities));

    final Tour testSolution = new Tour(SolutionTest.generateTSPSolution(4), function);

    int sum = 0;

    for (int i = 0; i < testSolution.size(); i++) {
      final City.Euclidian cityA = cities[testSolution.get(i)];
      final City.Euclidian cityB = cities[testSolution.get((i + 1) % testSolution.size())];
      sum += cityA.distance(cityB);
    }

    assertEquals(sum, function.actualValue(testSolution));
  }

  @Test
  public void actualValue_simpleFitnessFunction$simpleOrder_CorrectFitness() {

    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(
        Arrays.asList(new City.Euclidian[] { city1, city2, city3, city4 }));

    assertEquals(400, function.actualValue(new Tour(new Integer[] { 0, 1, 2, 3 }, function)));
  }

  @Test
  public void equals_simpleCities_equal() {
    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final City.Euclidian[] cities = new City.Euclidian[] { city1, city2, city3, city4 };

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(Arrays.asList(cities));
    final Cities<City.Euclidian> function2 = new Cities<City.Euclidian>(Arrays.asList(cities));

    assertEquals(function, function2);
  }

  @Test
  public void equals_simpleCitiesDifferentObjects_equal() {
    final City.Euclidian city11 = new City.Euclidian(0, 0);
    final City.Euclidian city12 = new City.Euclidian(0, 100);
    final City.Euclidian city13 = new City.Euclidian(100, 100);
    final City.Euclidian city14 = new City.Euclidian(100, 0);

    final City.Euclidian city21 = new City.Euclidian(0, 0);
    final City.Euclidian city22 = new City.Euclidian(0, 100);
    final City.Euclidian city23 = new City.Euclidian(100, 100);
    final City.Euclidian city24 = new City.Euclidian(100, 0);

    final City.Euclidian[] cities1 = new City.Euclidian[] { city11, city12, city13, city14 };
    final City.Euclidian[] cities2 = new City.Euclidian[] { city21, city22, city23, city24 };

    final Cities<City.Euclidian> function1 = new Cities<City.Euclidian>(Arrays.asList(cities1));
    final Cities<City.Euclidian> function2 = new Cities<City.Euclidian>(Arrays.asList(cities2));

    assertEquals(function1, function2);
  }

  @Test
  public void evaluateFitness_simpleFitnessFunction$randomOrder_CorrectFitness() {

    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final int maxDist = (int) Math.sqrt(100 * 100 + 100 * 100);
    final int totalMax = maxDist * 4;

    final City.Euclidian[] cities = new City.Euclidian[] { city1, city2, city3, city4 };

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(Arrays.asList(cities));

    final Tour testSolution = new Tour(SolutionTest.generateTSPSolution(4), function);

    int sum = 0;

    for (int i = 0; i < testSolution.size(); i++) {
      final City.Euclidian cityA = cities[testSolution.get(i)];
      final City.Euclidian cityB = cities[testSolution.get((i + 1) % testSolution.size())];
      sum += cityA.distance(cityB);
    }

    assertEquals(totalMax - sum, function.evaluateFitness(testSolution));
  }

  @Test
  public void evaluateFitness_simpleFitnessFunction$simpleOrder_CorrectFitness() {

    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final int maxDist = (int) Math.sqrt(100 * 100 + 100 * 100);
    final int totalMax = maxDist * 4;

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(
        Arrays.asList(new City.Euclidian[] { city1, city2, city3, city4 }));

    assertEquals(totalMax - 400,
        function.evaluateFitness(new Tour(new Integer[] { 0, 1, 2, 3 }, function)));
  }

  @Test
  public void getEuclidPoint_simpleCities_correctCities() {

    final City.Euclidian city1 = new City.Euclidian(0, 0);
    final City.Euclidian city2 = new City.Euclidian(0, 100);
    final City.Euclidian city3 = new City.Euclidian(100, 100);
    final City.Euclidian city4 = new City.Euclidian(100, 0);

    final City.Euclidian[] cities = new City.Euclidian[] { city1, city2, city3, city4 };

    final Cities<City.Euclidian> function = new Cities<City.Euclidian>(Arrays.asList(cities));

    for (int i = 0; i < cities.length; i++) {
      assertEquals(cities[i], function.getCity(i));
    }
  }

  @Before
  public void setUp() throws Exception {
  }

}
