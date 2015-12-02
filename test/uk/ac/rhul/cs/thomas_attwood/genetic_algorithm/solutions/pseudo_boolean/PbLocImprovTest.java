package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;

public class PbLocImprovTest {

  private PbFunction simpleFitnessFunction;

  @Test
  public void GetCompleteImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    assertEquals(solution, locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetCompleteImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getCompleteImprovedSolution(solution));
    }
  }

  @Test
  public void GetCompleteImprovedSolution_TwoImprovementsPossible_BothBitsFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    assertEquals(expectedSolution,
        locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    assertEquals(solution, locImprov.getNeighbourhoodImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_OnePossible_BestBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, false, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, false, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    assertEquals(solution, locImprov.getSingleOrderedImprovedSolution(solution));
  }

  @Test
  public void GetSingleOrderedImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    assertEquals(solution, locImprov.getSingleRandomImprovedSolution(solution));
  }

  @Test
  public void GetSingleRandomImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    PbSolution expectedSolution =
        new PbSolution(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleRandomImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    PbSolution solution =
        new PbSolution(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<PbSolution> locImprov = new PbLocImprov();

    int expectedFitness = 400;

    for (int i = 0; i < 10; i++) {
      assertTrue("expected fitness == 400 for single improvment",
          expectedFitness == locImprov
              .getSingleRandomImprovedSolution(solution).getFitness());
    }
  }

  @Before
  public void setUpSimple() {
    final int coefficient = 100;
    Term[] terms =
        new Term[] {
            new Term(
                coefficient,
                new BooleanIndeterminant[] { new BooleanIndeterminant(0, false) }),
            new Term(
                coefficient,
                new BooleanIndeterminant[] { new BooleanIndeterminant(1, false) }),
            new Term(
                coefficient,
                new BooleanIndeterminant[] { new BooleanIndeterminant(2, false) }),
            new Term(
                coefficient,
                new BooleanIndeterminant[] { new BooleanIndeterminant(3, false) }),
            new Term(
                coefficient,
                new BooleanIndeterminant[] { new BooleanIndeterminant(4, false) }) };
    simpleFitnessFunction = new PbFunction(terms);
  }
}