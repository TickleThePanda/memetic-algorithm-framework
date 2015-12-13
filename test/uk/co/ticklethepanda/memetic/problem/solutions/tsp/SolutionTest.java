package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.ticklethepanda.memetic.problem.generators.tsp.EuclidianCitiesGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.City.Euclidian;

public class SolutionTest {

  private final static Random random = new Random();

  public static Integer[] generateTSPSolution(final int size) {

    final Integer[] tspSolution = new Integer[size];

    final ArrayList<Integer> toChooseFrom = new ArrayList<Integer>();
    for (int i = 0; i < size; i++) {
      toChooseFrom.add(i);
    }
    for (int i = 0; i < size; i++) {
      final int selected = random.nextInt(toChooseFrom.size());
      tspSolution[i] = toChooseFrom.get(selected);
      toChooseFrom.remove(selected);
    }

    return tspSolution;
  }

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  private Cities<Euclidian> function;

  @Before
  public void setUp() throws Exception {
    final int size = 10;
    function = new EuclidianCitiesGenerator(size).generateFunction();
  }

  @Test
  public void testGet_randomSolution_correctValue() {
    final int size = 10;
    final Integer[] tspArray = generateTSPSolution(size);
    final Tour tsp = new Tour(tspArray, function);

    for (int i = 0; i < size; i++) {
      assertEquals(tspArray[i].intValue(), tsp.get(i));
    }
  }

  @Test
  public void testGet_simpleSolution_correctValue() {
    final Integer[] tspArray = { 0, 1, 2 };
    final Tour tsp = new Tour(tspArray, function);
    for (int i = 0; i < tspArray.length; i++) {
      assertEquals(tspArray[i].intValue(), tsp.get(i));
    }
  }

  @Test
  public void testGetActualValue_randomSolution_correctValue() {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(10).generateFunction();
    final Tour solution = new Tour(generateTSPSolution(10), function);

    assertEquals(function.actualValue(solution), solution.getObjectiveValue());
  }

  @Test
  public void testGetFitness_randomSolution_correctFitness() {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(10).generateFunction();
    final Tour solution = new Tour(generateTSPSolution(10), function);

    assertEquals(function.evaluateFitness(solution), solution.getFitness());
  }

  @Test
  public void testGetFitnessFunction_randomSolution_correctFitnessFunction() {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(10).generateFunction();
    final Tour solution = new Tour(generateTSPSolution(10), function);

    assertEquals(function, solution.getFitnessFunction());
  }

  @Test
  public void testGetMutated_simpleSolutionSeed0_bitFlipped() {
    final int size = 3;
    final int seed = 0;
    final Integer[] tspArray = new Integer[size];

    for (int i = 0; i < tspArray.length; i++) {
      tspArray[i] = i;
    }

    final Tour tsp = new Tour(seed, tspArray, function);

    final Random random = new Random(seed);

    final int swap1 = random.nextInt(size);
    final int swap2 = random.nextInt(size);

    final Integer[] tspArrayMutated = new Integer[size];

    for (int i = 0; i < size; i++) {
      if (i == swap1) {
        tspArrayMutated[i] = tspArray[swap2];
      } else if (i == swap2) {
        tspArrayMutated[i] = tspArray[swap1];
      } else {
        tspArrayMutated[i] = tspArray[i];
      }
    }

    final Tour actualMutated = new Tour(tspArrayMutated, function);

    final Tour mutated = tsp.getMutated();

    assertEquals(actualMutated, mutated);

  }

  @Test
  public void testSize_randomSolution_correctValue() {
    final int size = 10;
    final Integer[] tspArray = generateTSPSolution(size);
    final Tour tsp = new Tour(tspArray, function);

    assertEquals(size, tsp.size());
  }

  @Test
  public void testSize_simpleSolution_correctValue() {
    final int size = 3;
    final Integer[] tspArray = new Integer[] { 0, 1, 2 };
    final Tour tsp = new Tour(tspArray, function);
    assertEquals(size, tsp.size());
  }
}
