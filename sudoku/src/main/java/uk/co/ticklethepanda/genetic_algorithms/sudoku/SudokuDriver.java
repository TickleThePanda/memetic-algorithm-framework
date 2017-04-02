package uk.co.ticklethepanda.genetic_algorithms.sudoku;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import uk.co.ticklethepanda.genetic_algorithms.genetics.GeneticAlgorithm;
import uk.co.ticklethepanda.genetic_algorithms.genetics.SolutionFitnessComparitor;
import uk.co.ticklethepanda.genetic_algorithms.genetics.SimulationVariables;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.fitness.BasicSudokuFitnessFunction;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuPoint;

public class SudokuDriver {
	public static final int GENERATIONS = 1000;
	public static final int POOL_SIZE = 10000;
	public static final int ELITE_SAVE = 10;
	public static final int NUMBER_OF_PARENTS = 10;
	public static final float REPLACE_RATE = 0.05f;
	public static final float MUTATION_RATE = 0.05f;
	// http://www.theguardian.com/lifeandstyle/2014/jul/24/sudoku-2871-hard
	public final static SudokuPoint[] SEED_ARRAY = new SudokuPoint[] {
			new SudokuPoint(new Point(0, 0), 4),
			new SudokuPoint(new Point(0, 2), 8),
			new SudokuPoint(new Point(0, 4), 1),
			new SudokuPoint(new Point(0, 8), 9),
			new SudokuPoint(new Point(1, 3), 9),
			new SudokuPoint(new Point(1, 6), 8),
			new SudokuPoint(new Point(2, 1), 2),
			new SudokuPoint(new Point(2, 5), 7),
			new SudokuPoint(new Point(2, 8), 3),
			new SudokuPoint(new Point(3, 2), 3),
			new SudokuPoint(new Point(3, 7), 1),
			new SudokuPoint(new Point(4, 0), 1),
			new SudokuPoint(new Point(4, 4), 6),
			new SudokuPoint(new Point(4, 8), 2),
			new SudokuPoint(new Point(5, 1), 5),
			new SudokuPoint(new Point(5, 6), 7),
			new SudokuPoint(new Point(6, 0), 5),
			new SudokuPoint(new Point(6, 3), 7),
			new SudokuPoint(new Point(6, 7), 4),
			new SudokuPoint(new Point(7, 2), 4),
			new SudokuPoint(new Point(7, 5), 1),
			new SudokuPoint(new Point(8, 0), 8),
			new SudokuPoint(new Point(8, 4), 5),
			new SudokuPoint(new Point(8, 6), 3),
			new SudokuPoint(new Point(8, 8), 6) };

	public final static ArrayList<SudokuPoint> SEED_ARRAY_LIST = new ArrayList<SudokuPoint>(
			Arrays.asList(SEED_ARRAY));

	private static void displaySeed() {
		int[][] blank = new int[9][9];
		for (SudokuPoint sp : SEED_ARRAY) {
			blank[sp.getPoint().x][sp.getPoint().y] = sp.getValue();
		}

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (y % 3 == 0)
					System.out.print("|");
				if (blank[x][y] != 0)
					System.out.print(blank[x][y]);
				else
					System.out.print(" ");
			}
			if ((x + 1) % 3 == 0)
				System.out.println("\n------------");
			else
				System.out.println();
		}
	}

	public static void main(String args[]) {
		displaySeed();
		getSolution().printSolution();
	}

	private static SudokuSolution getSolution() {
		SudokuSolutionFactory ssf = new SudokuSolutionFactory(SEED_ARRAY_LIST,
				POOL_SIZE);
		BasicSudokuFitnessFunction lsff = new BasicSudokuFitnessFunction();
		SimulationVariables rates = new SimulationVariables(ELITE_SAVE,
				MUTATION_RATE, REPLACE_RATE, GENERATIONS, NUMBER_OF_PARENTS);
		GeneticAlgorithm<SudokuSolution> ga = new GeneticAlgorithm<SudokuSolution>(
				ssf, lsff, rates);

		ga.runAlgorithm();
		return ga.getBest();
	}
}
