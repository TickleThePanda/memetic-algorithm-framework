package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class TSPFunctionFactoryTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
    ;
  }

  private final static int MAX_CITY_DIST = 10000;
  private static final int MAX_SIZE = 20;
  private static final int MIN_SIZE = 10;

  private static Random random;

  @Test
  public void generateProblem_seed0size10_functionCorrectlyGenerated() {
    final long seed = 0;
    final int size = 10;

    final Point[] cities = new Point[size];

    final Random mirrorRandom = new Random(seed);

    for (int i = 0; i < cities.length; i++) {
      cities[i] =
          new Point(mirrorRandom.nextInt(MAX_CITY_DIST),
              mirrorRandom.nextInt(MAX_CITY_DIST));
    }

    final EuclidTspFunctionFactory functionGen =
        new EuclidTspFunctionFactory(seed, size);

    final EuclidTspFunction function = functionGen.generateFunction();

    for (int i = 0; i < cities.length; i++) {
      assertEquals(cities[i], function.getCity(i));
    }

  }

  @Test
  public void generateProblem_seed0sizeRandom_functionCorrectlyGenerated() {
    final long seed = 0;
    final int size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;

    final Point[] cities = new Point[size];

    final Random mirrorRandom = new Random(seed);

    for (int i = 0; i < cities.length; i++) {
      cities[i] =
          new Point(mirrorRandom.nextInt(MAX_CITY_DIST),
              mirrorRandom.nextInt(MAX_CITY_DIST));
    }

    final EuclidTspFunctionFactory functionGen =
        new EuclidTspFunctionFactory(seed, size);

    final EuclidTspFunction function = functionGen.generateFunction();

    for (int i = 0; i < cities.length; i++) {
      assertEquals(cities[i], function.getCity(i));
    }

  }

  @Test
  public void generateProblem_seedRandomsizeRandom_functionCorrectlyGenerated() {
    final long seed = random.nextLong();
    final int size = random.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;

    final Point[] cities = new Point[size];

    final Random mirrorRandom = new Random(seed);

    for (int i = 0; i < cities.length; i++) {
      cities[i] =
          new Point(mirrorRandom.nextInt(MAX_CITY_DIST),
              mirrorRandom.nextInt(MAX_CITY_DIST));
    }

    final EuclidTspFunctionFactory functionGen =
        new EuclidTspFunctionFactory(seed, size);

    final EuclidTspFunction function = functionGen.generateFunction();

    for (int i = 0; i < cities.length; i++) {
      assertEquals(cities[i], function.getCity(i));
    }

  }

}
