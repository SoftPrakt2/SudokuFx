package logic;

import javafx.scene.paint.Color;

public class Cell {

	// Spalte 1 - 9
	int col;

	// Reihe 1 - 9
	int row;

	// Box 1 - 9
	int box;

	// Wert 1 - 9
	int value;

	// Manuelle Eingabe
	boolean isReal;

	// Color color
	Color color;

	public Cell(int row, int col, int box, int value) {
		super();
		this.row = row;
		this.col = col;
		this.box = box;
		this.value = value;
		this.isReal = true;
	}

	public boolean getIsReal() {
		return this.isReal;
	}

	public void setIsReal(boolean newState) {
		this.isReal = newState;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getBox() {
		return box;
	}

	public void setBox(int box) {
		this.box = box;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Feld [col=" + col + ", row=" + row + ", box=" + box + ", value=" + value + "]";
	}
}