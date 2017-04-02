package uk.co.ticklethepanda.genetic_algorithms.sudoku.structure;

import java.awt.Point;

public class SudokuPoint {
	private final Point point;
	private final int value;
	public SudokuPoint(Point point, int value) {
		super();
		this.point = point;
		this.value = value;
	}
	public Point getPoint() {
		return point;
	}
	public int getValue() {
		return value;
	}
	
	
}
