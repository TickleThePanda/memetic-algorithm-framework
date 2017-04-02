package uk.co.ticklethepanda.genetic_algorithms.sudoku.structure;


public class SudokuGrid {
	private final SudokuCell[][] grid;

	public static final int GRID_WIDTH = 9;
	public static final int GRID_HEIGHT = 9;
	public static final int GRID_COUNT = GRID_WIDTH * GRID_HEIGHT;
	
	public static final int BOXES_HORIZ = 3;
	public static final int BOXES_WIDTH = GRID_WIDTH / BOXES_HORIZ;
	public static final int BOXES_VERTI = 3;
	public static final int BOXES_HEIGHT = GRID_HEIGHT / BOXES_VERTI;
	
	public SudokuGrid() {
		grid = new SudokuCell[GRID_WIDTH][GRID_HEIGHT];
	}

	/**
	 * @return the grid
	 */
	public SudokuCell[][] getGrid() {
		return grid;
	}
	
}