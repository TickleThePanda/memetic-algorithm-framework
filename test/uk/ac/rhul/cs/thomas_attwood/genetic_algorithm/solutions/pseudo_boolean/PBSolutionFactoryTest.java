package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.SolutionFactory;

public class PBSolutionFactoryTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  private FitnessFunction<PbSolution> function;

  @Test
  public void GetFitnessFunction_RandomFitnessFunction_CorrectFitnessFunction() {
    final int seed = 0;
    final SolutionFactory<PbSolution> pbsg =
        new PbSolutionFactory(seed, function);

    assertEquals(function, pbsg.getFitnessFunction());
  }

  @Before
  public void setUp() {
    function = new QuadraticPbFuncFactory(10).generateFunction();
  }

  @Test
  public void TestCrossover_DiffSizesFirstSmaller_ChildCorrect() {
    final int maxSize = 6;
    final long seed = 0;

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(maxSize).generateFunction();

    final PbSolution bf1 =
        new PbSolution(new boolean[] { true, false, true, false, true },
            function);
    final PbSolution bf2 =
        new PbSolution(new boolean[] { false, true, false, true, false, true },
            function);

    final PbSolution child =
        new PbSolutionFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf1.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else {
        assertEquals(bf2.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestCrossover_DiffSizesSecondSmaller_ChildCorrect() {
    final int maxSize = 5;
    final long seed = 0;

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(maxSize).generateFunction();

    final PbSolution bf1 =
        new PbSolution(new boolean[] { true, false, true, false, true },
            function);
    final PbSolution bf2 =
        new PbSolution(new boolean[] { false, true, false, true }, function);

    final PbSolution child =
        new PbSolutionFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf2.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else if (i < bf2.size()) {
        assertEquals(bf2.get(i), child.get(i));
      } else {
        assertEquals(bf1.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestCrossover_RandomSeeds_ChildrenCorrect() {
    final long seed = random.nextLong();
    final int maxChildren = 15;
    final int minChildren = 10;
    final int nChildren =
        random.nextInt(maxChildren - minChildren) + minChildren;
    final int size = 4;

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(size).generateFunction();

    final boolean[] bools1 = new boolean[] { true, true, false, false };
    final boolean[] bools2 = new boolean[] { false, true, true, false };

    final PbSolution bf1 = new PbSolution(bools1, function);
    final PbSolution bf2 = new PbSolution(bools2, function);

    final PbSolutionFactory pbSolutionFactory =
        new PbSolutionFactory(seed, function);

    final PbSolution[] children = new PbSolution[nChildren];

    for (int i = 0; i < nChildren; i++) {
      children[i] = pbSolutionFactory.crossover(bf1, bf2);
    }

    final Random seededRandom = new Random(seed);

    for (int childIndex = 0; childIndex < nChildren; childIndex++) {
      final int crossoverPoint = seededRandom.nextInt(size);
      // throw away random long for mutation seed
      seededRandom.nextLong();
      for (int solIndex = 0; solIndex < bf1.size() && solIndex < bf2.size(); solIndex++) {
        if (solIndex < crossoverPoint) {
          assertEquals(bools1[solIndex], children[childIndex].get(solIndex));
        } else {
          assertEquals(bools2[solIndex], children[childIndex].get(solIndex));
        }
      }
    }
  }

  @Test
  public void TestCrossover_Seed0_ChildrenCorrect() {
    final long seed = 0;
    final int maxChildren = 15;
    final int minChildren = 10;
    final int nChildren =
        random.nextInt(maxChildren - minChildren) + minChildren;

    final int size = 4;

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(size).generateFunction();

    final boolean[] bools1 = new boolean[] { true, true, false, false };
    final boolean[] bools2 = new boolean[] { false, true, true, false };

    final PbSolution bf1 = new PbSolution(bools1, function);
    final PbSolution bf2 = new PbSolution(bools2, function);

    final PbSolutionFactory pbSolutionFactory =
        new PbSolutionFactory(seed, function);

    final PbSolution[] children = new PbSolution[nChildren];

    for (int i = 0; i < nChildren; i++) {
      children[i] = pbSolutionFactory.crossover(bf1, bf2);
    }

    final Random seededRandom = new Random(seed);

    for (int childIndex = 0; childIndex < nChildren; childIndex++) {
      final int crossoverPoint = seededRandom.nextInt(4);
      // throwaway random
      seededRandom.nextLong();
      for (int solIndex = 0; solIndex < bf1.size() && solIndex < bf2.size(); solIndex++) {
        if (solIndex < crossoverPoint) {
          assertEquals(bools1[solIndex], children[childIndex].get(solIndex));
        } else {
          assertEquals(bools2[solIndex], children[childIndex].get(solIndex));
        }
      }
    }
  }

  @Test
  public void TestCrossover_TwoRandomParents_ChildCorrect() {
    final int maxRandomSize = 30;
    final int minRandomSize = 10;
    final int randomSize =
        random.nextInt(maxRandomSize - minRandomSize) + minRandomSize;

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(randomSize).generateFunction();

    final long seed = 0;

    final PbSolution bf1 =
        new PbSolution(PbTests.createRandomBoolArr(randomSize), function);
    final PbSolution bf2 =
        new PbSolution(PbTests.createRandomBoolArr(randomSize), function);

    final PbSolution child =
        new PbSolutionFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf2.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else if (i < bf2.size()) {
        assertEquals(bf2.get(i), child.get(i));
      } else {
        assertEquals(bf1.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestGenerateSolution_RandomSeed_SolutionCorrectlyGenerated() {
    final int size = 10;
    final long seed = random.nextLong();

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(size).generateFunction();

    final SolutionFactory<PbSolution> pbsg =
        new PbSolutionFactory(seed, function);

    final Random testRandom = new Random(seed);

    final PbSolution solution = pbsg.generateSolution();

    // throwaway seed generation
    testRandom.nextLong();

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(testRandom.nextBoolean(), solution.get(i));
    }
  }

  @Test
  public void TestGenerateSolution_RandomSeedMultipleSolutions_SolutionCorrectlyGenerated() {
    final int size = 10;
    final int nSols = 10;
    final long seed = random.nextLong();

    final FitnessFunction<PbSolution> function =
        new QuadraticPbFuncFactory(size).generateFunction();

    final Random testRandom = new Random(seed);
    final SolutionFactory<PbSolution> pbsg =
        new PbSolutionFactory(seed, function);

    for (int j = 0; j < nSols; j++) {

      final PbSolution solution = pbsg.generateSolution();

      // throwaway seed generation
      testRandom.nextLong();

      for (int i = 0; i < solution.size(); i++) {
        assertEquals(testRandom.nextBoolean(), solution.get(i));
      }
    }
  }

  @Test
  public void TestGenerateSolution_Seed0_SolutionCorrectlyGenerated() {
    final int seed = 0;
    final SolutionFactory<PbSolution> pbsg =
        new PbSolutionFactory(seed, function);

    final Random testRandom = new Random(seed);

    final PbSolution solution = pbsg.generateSolution();

    // throwaway seed generation
    testRandom.nextLong();

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(testRandom.nextBoolean(), solution.get(i));
    }
  }

}
