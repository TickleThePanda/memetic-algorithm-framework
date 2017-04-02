package uk.co.ticklethepanda.genetic_algorithms.sudoku.structure;

import uk.co.ticklethepanda.genetic_algorithms.sudoku.SudokuSolution;

public class SudokuString {
	private int[] string;
	
	public SudokuString() {
		string = new int[SudokuGrid.GRID_COUNT];
	}
	
	public SudokuString(SudokuSolution sg) {
		string = new int[SudokuGrid.GRID_COUNT];
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				string[SudokuGrid.GRID_WIDTH * x + y] = sg.getGrid().getGrid()[x][y].getCellValue();
			}
		}
	}
	
	public void setValue(int coord, int value) {
		string[coord] = value;
	}
	
	public int getValue(int i) {
		return string[i];
	}
}