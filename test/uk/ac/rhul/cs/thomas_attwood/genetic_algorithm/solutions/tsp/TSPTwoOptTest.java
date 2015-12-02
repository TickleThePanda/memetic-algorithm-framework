package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;

public class TSPTwoOptTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		random = new Random();

	}

	private static Random random;

	@Test
	public void LocalTwoOpt_RandomInputRandomSizeRandomSwap_CorrectOutput() {
		final int randomSize = random.nextInt(50) + 10;
		final EuclidTspFunction tf = new EuclidTspFunctionFactory(randomSize)
				.generateFunction();

		final int[] initialOrder = TSPSolutionTest
				.generateTSPSolution(randomSize);

		final TspTour solution = new TspTour(initialOrder, tf);

		final TspLiTwoOpt locImprov = new TspLiTwoOpt();
		TspTour bestSolution = solution;

		for (int i = 0; i < initialOrder.length; i++) {
			for (int j = i + 1; j < initialOrder.length; j++) {

				final TspTour improved = locImprov.doLazySingleSwap(solution,
						i, j);
				if (improved.getFitness() > bestSolution.getFitness()) {
					bestSolution = improved;
				}

			}
		}

		assertEquals(bestSolution,
				locImprov.getNeighbourhoodImprovedSolution(solution));
	}

	@Test
	public void LocImprov_ExtendsLocalImprovement_AssumptionCorrect() {
		final Class<?> locImprov = LocalImprovement.class;
		assertTrue(Arrays.asList(TspLiTwoOpt.class.getInterfaces()).contains(
				locImprov));
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void SingleTwoOpt_RandomInputRandomSizeRandomSwap_CorrectOutput() {
		final int randomSize = random.nextInt(50) + 10;
		final EuclidTspFunction tf = new EuclidTspFunctionFactory(randomSize)
				.generateFunction();

		final int[] initialOrder = TSPSolutionTest
				.generateTSPSolution(randomSize);

		final TspTour solution = new TspTour(initialOrder, tf);

		final TspLiTwoOpt locImprov = new TspLiTwoOpt();

		final int firstIndex = random.nextInt(randomSize - 1);
		final int secondIndex = random.nextInt(randomSize - 1 - firstIndex)
				+ firstIndex + 1;

		final int[] newOrder = new int[randomSize];
		for (int i = 0; i < newOrder.length; i++) {
			if (i >= firstIndex && i <= secondIndex) {
				final int reverseIndex = secondIndex + firstIndex - i;
				newOrder[i] = initialOrder[reverseIndex];
			} else {
				newOrder[i] = initialOrder[i];
			}
		}

		final TspTour improved = locImprov.doSingleSwap(solution,
				firstIndex, secondIndex);

		for (int i = 0; i < improved.size(); i++) {
			assertEquals(newOrder[i], improved.get(i));
		}
	}

	@Test
	public void SingleTwoOpt_SimpleInput_CorrectOutput() {
		final EuclidTspFunction tf = new EuclidTspFunction(new EuclidPoint[] {
				new EuclidPoint(0, 0), new EuclidPoint(0, 100),
				new EuclidPoint(100, 100), new EuclidPoint(100, 0) });
		final TspLiTwoOpt locImprov = new TspLiTwoOpt();

		final int[] initialOrder = new int[] { 0, 2, 1, 3 };

		final TspTour solution = new TspTour(initialOrder, tf);

		final int[] newOrder = new int[] { 0, 1, 2, 3 };

		final int firstIndex = 1;
		final int secondIndex = 2;

		final TspTour improved = locImprov.doLazySingleSwap(solution,
				firstIndex, secondIndex);
		
		for (int i = 0; i < improved.size(); i++) {
			assertEquals(newOrder[i], improved.get(i));
		}
	}
	
}
