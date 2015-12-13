package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.ticklethepanda.memetic.problem.generators.tsp.EuclidianCitiesGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;

public class TwoOptTest {

  private static Random random;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    random = new Random();

  }

  @Test
  public void LocImprov_ExtendsLocalImprovement_AssumptionCorrect() {
    final Class<?> locImprov = LocalImprovement.class;
    assertTrue(Arrays.asList(TwoOptLocalImprovement.class.getInterfaces()).contains(locImprov));
  }

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void SingleTwoOpt_RandomInputRandomSizeRandomSwap_CorrectOutput() {
    final int randomSize = random.nextInt(50) + 10;
    final Cities<City.Euclidian> tf = new EuclidianCitiesGenerator(randomSize).generateFunction();

    final Integer[] initialOrder = SolutionTest.generateTSPSolution(randomSize);

    final Tour solution = new Tour(initialOrder, tf);

    final TwoOptLocalImprovement locImprov = new TwoOptLocalImprovement();

    final int firstIndex = random.nextInt(randomSize - 1);
    final int secondIndex = random.nextInt(randomSize - 1 - firstIndex) + firstIndex + 1;

    final Integer[] newOrder = new Integer[randomSize];
    for (int i = 0; i < newOrder.length; i++) {
      if (i >= firstIndex && i <= secondIndex) {
        final int reverseIndex = secondIndex + firstIndex - i;
        newOrder[i] = initialOrder[reverseIndex];
      } else {
        newOrder[i] = initialOrder[i];
      }
    }

    final Tour improved = locImprov.doSingleSwap(solution, firstIndex, secondIndex);

    for (int i = 0; i < improved.size(); i++) {
      assertEquals(newOrder[i].intValue(), improved.get(i));
    }
  }

  @Test
  public void SingleTwoOpt_SimpleInput_CorrectOutput() {
    final Cities<City.Euclidian> tf = new Cities<City.Euclidian>(
        Arrays.asList(new City.Euclidian[] { new City.Euclidian(0, 0), new City.Euclidian(0, 100),
            new City.Euclidian(100, 100), new City.Euclidian(100, 0) }));
    final TwoOptLocalImprovement locImprov = new TwoOptLocalImprovement();

    final Integer[] initialOrder = new Integer[] { 0, 2, 1, 3 };

    final Tour solution = new Tour(initialOrder, tf);

    final Integer[] newOrder = new Integer[] { 0, 1, 2, 3 };

    final int firstIndex = 1;
    final int secondIndex = 2;

    final Tour improved = locImprov.doLazySingleSwap(solution, firstIndex, secondIndex);

    for (int i = 0; i < improved.size(); i++) {
      assertEquals(newOrder[i].intValue(), improved.get(i));
    }
  }

}
