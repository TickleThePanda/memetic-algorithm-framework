package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.ticklethepanda.memetic.problem.generators.tsp.EuclidianCitiesGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.City.Euclidian;

public class SolutionFactoryTest {

  private static final int MAX_RANDOM_SIZE = 100;

  private static final int MIN_RANDOM_SIZE = 10;

  private static Random random;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    random = new Random();
  }

  private int rSize;
  private Cities<Euclidian> rSizeFunction;
  private Tour.Generator rSizeSF;
  private Cities<Euclidian> simpleFunction;
  private Random simpleMirrorRandom;
  private Integer[] simpleParent1;
  private Integer[] simpleParent2;
  private long simpleSeed;
  private Tour.Generator simpleSF;

  private int simpleSize;
  private Tour simpleTSPParent1;
  private Tour simpleTSPParent2;

  private int size10;
  private Cities<Euclidian> size10Function;
  private Tour.Generator size10SF;

  @Test
  public void Crossover_RandomFunction_SolutionHasCorrectFunction() {
    final Tour parent1 = rSizeSF.generateSolution();
    final Tour parent2 = rSizeSF.generateSolution();

    assertEquals(rSizeFunction, rSizeSF.crossover(parent1, parent2).getFitnessFunction());
  }

  @Test
  public void Crossover_SizeRandom$SeedRandom$SolutionRandom_EveryCityPresent() {

    final Tour solution = rSizeSF.crossover(rSizeSF.generateSolution(), rSizeSF.generateSolution());

    final boolean[] present = new boolean[rSize];

    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city] = true;
    }

    for (int i = 0; i < present.length; i++) {
      assertTrue("city " + i + " isn't present", present[i]);
    }
  }

  @Test
  public void Crossover_SizeRandom$SeedRandom$SolutionRandom_NoDuplicateCities() {

    final Tour solution = rSizeSF.crossover(rSizeSF.generateSolution(), rSizeSF.generateSolution());

    final int[] present = new int[rSize];

    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city]++;
    }

    for (int i = 0; i < present.length; i++) {
      assertTrue("city " + i + " is a duplicate", present[i] <= 1);
    }
  }

  @Test
  public void CrossoverSolution_Size10Seed0SimpleSolution_CorrectOrder() {

    final Integer[] child = new Integer[simpleSize];

    // crossover length
    final int crossoverPoint1 = simpleMirrorRandom.nextInt(simpleSize - 2);
    final int crossoverPoint2 =
        crossoverPoint1 + simpleMirrorRandom.nextInt(simpleSize - crossoverPoint1);

    // find point at which parent2 has the same value as in parent1[0]
    int startingLoc = 0;
    for (int i = 0; i < simpleParent2.length; i++) {
      if (simpleParent2[i] == simpleParent1[0]) {
        startingLoc = i;
        break;
      }
    }

    final int diff = crossoverPoint2 - crossoverPoint1;

    final ArrayList<Integer> visited = new ArrayList<Integer>();
    // we want a list of cities that we have visited.
    for (int i = 0; i < diff; i++) {
      final int city = simpleParent1[crossoverPoint1 + i];
      visited.add(city);
      child[i] = city;
    }

    int iChild = diff;
    int iParent = 0;
    while (iChild < child.length) {
      final int realParentIndex = (startingLoc + crossoverPoint2 + iParent + 1) % simpleSize;
      final int city = simpleParent2[realParentIndex];

      if (visited.contains(city) == false) {
        child[iChild++] = city;
      }

      iParent++;
    }

    final Tour tspChild = new Tour(child, simpleFunction);

    assertEquals(tspChild, simpleSF.crossover(simpleTSPParent1, simpleTSPParent2));
  }

  @Test
  public void GenerateFunction_RandomFunction_SolutionHasCorrectFunction() {
    assertEquals(rSizeFunction, rSizeSF.generateSolution().getFitnessFunction());
  }

  @Test
  public void GenerateSolution_RandomSizeRandomSeed_CorrectOrder() {
    final long seed = random.nextLong();

    final Tour.Generator sf = new Tour.Generator(seed, rSizeFunction, false);

    final Random mirrorRandom = new Random(seed);

    final Tour solution = sf.generateSolution();

    final Integer[] cities = new Integer[solution.size()];
    for (int i = 0; i < solution.size(); i++) {
      cities[i] = i;
    }

    for (int i = 0; i < solution.size(); i++) {
      final int swapLoc = mirrorRandom.nextInt(solution.size());
      final int temp = cities[swapLoc];
      cities[swapLoc] = cities[i];
      cities[i] = temp;
    }

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(cities[i].intValue(), solution.get(i));
    }
  }

  @Test
  public void GenerateSolution_RandomSizeSeed0_CorrectOrder() {
    final long seed = 0;

    final Tour.Generator sf = new Tour.Generator(seed, rSizeFunction, false);

    final Random mirrorRandom = new Random(seed);

    final Tour solution = sf.generateSolution();

    final Integer[] cities = new Integer[solution.size()];
    for (int i = 0; i < solution.size(); i++) {
      cities[i] = i;
    }

    for (int i = 0; i < solution.size(); i++) {
      final int swapLoc = mirrorRandom.nextInt(solution.size());
      final int temp = cities[swapLoc];
      cities[swapLoc] = cities[i];
      cities[i] = temp;
    }

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(cities[i].intValue(), solution.get(i));
    }
  }

  @Test
  public void GenerateSolution_Size10_EveryCityPresent() {

    final Tour solution = size10SF.generateSolution();

    final boolean[] present = new boolean[size10];

    // set cities to true if present because boolean default is false
    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city] = true;
    }

    for (final boolean element : present) {
      assertTrue(element);
    }
  }

  @Test
  public void GenerateSolution_Size10_NoDuplicates() {

    final Tour solution = size10SF.generateSolution();

    final int[] present = new int[size10];
    
    // set cities to true if present because boolean default is false
    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city]++;
    }

    for (final int element : present) {
      assertTrue(element <= 1);
    }
  }

  @Test
  public void GenerateSolution_SizeRandom_EveryCityPresent() {

    final Tour solution = rSizeSF.generateSolution();

    final boolean[] present = new boolean[rSize];

    // set cities to true if present because boolean default is false
    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city] = true;
    }

    for (final boolean element : present) {
      assertTrue(element);
    }
  }

  @Test
  public void GenerateSolution_SizeRandom_NoCityDuplicated() {

    final Tour solution = rSizeSF.generateSolution();

    final int[] present = new int[rSize];
    
    for (int i = 0; i < solution.size(); i++) {
      final int city = solution.get(i);
      present[city]++;
    }

    for (final int element : present) {
      assertTrue(element <= 1);
    }
  }

  @Test
  public void getFitnessFunction_RandomFunction_HasCorrectFunction() {
    assertEquals(rSizeFunction, rSizeSF.getFitnessFunction());
  }

  @Before
  public void setUp() throws Exception {
    simpleSeed = 0;
    simpleMirrorRandom = new Random(simpleSeed);
    simpleSize = 10;

    simpleFunction = new EuclidianCitiesGenerator(simpleSize).generateFunction();

    simpleParent1 = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    simpleParent2 = new Integer[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

    simpleTSPParent1 = new Tour(simpleParent1, simpleFunction);
    simpleTSPParent2 = new Tour(simpleParent2, simpleFunction);

    simpleSF = new Tour.Generator(simpleSeed, simpleFunction);
  }

  @Before
  public void setUpRandomFunction() throws Exception {
    rSize = random.nextInt(MAX_RANDOM_SIZE - MIN_RANDOM_SIZE) + MIN_RANDOM_SIZE;
    rSizeFunction = new EuclidianCitiesGenerator(rSize).generateFunction();

    rSizeSF = new Tour.Generator(rSizeFunction);
  }

  @Before
  public void setUpSize10() {
    size10 = 10;
    size10Function = new EuclidianCitiesGenerator(size10).generateFunction();
    size10SF = new Tour.Generator(size10Function);
  }

}
