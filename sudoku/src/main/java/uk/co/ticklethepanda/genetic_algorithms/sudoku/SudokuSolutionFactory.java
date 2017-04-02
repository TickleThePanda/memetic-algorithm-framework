package uk.co.ticklethepanda.genetic_algorithms.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import uk.co.ticklethepanda.genetic_algorithms.genetics.SolutionFactory;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuCell;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuGrid;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuPoint;

public class SudokuSolutionFactory implements
		SolutionFactory<SudokuSolution> {

	Random random;
	int poolSize;
	ArrayList<?> seed;

	public SudokuSolutionFactory(ArrayList<?> seed, int poolSize) {
		this.poolSize = poolSize;
		this.seed = seed;
		random = new Random();
	}

	@Override
	public SudokuSolution generateSolution() {
		SudokuSolution newGrid = new SudokuSolution();
		for (Object point : seed) {
			newGrid.addFixedPoint((SudokuPoint) point);
		}
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				if (newGrid.getGrid().getGrid()[x][y] == null) {
					newGrid.getGrid().getGrid()[x][y] = new SudokuCell(
							random.nextInt(9) + 1, false);
				}
			}
		}
		return newGrid;
	}

	

	@Override
	public int getPoolSize() {
		return poolSize;
	}

	@Override
	public SudokuSolution combineSolutions(ArrayList<SudokuSolution> problems) {
		int[] combinationPoint = new int[problems.size() - 1];
		for (int i = 0; i < combinationPoint.length; i++) {
			combinationPoint[i] = random.nextInt(SudokuGrid.GRID_COUNT);
		}
		Arrays.sort(combinationPoint);
		SudokuSolution newGrid = new SudokuSolution();

		int i = 0;
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				int locInGrid = SudokuGrid.GRID_WIDTH * x + y;
				while (locInGrid > combinationPoint[i]) {
					if(i >= combinationPoint.length - 1) {
						break;
					} else {
					i++;
					}
				}
				newGrid.getGrid().getGrid()[x][y] = problems.get(i).getGrid().getGrid()[x][y];
			}
		}
		newGrid.setFixedCount(problems.get(i).getFixedCount());
		return newGrid;
	}

}
