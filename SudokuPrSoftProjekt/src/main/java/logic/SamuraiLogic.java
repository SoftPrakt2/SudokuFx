package logic;

import application.SudokuField;




public class SamuraiLogic extends BasicGameLogic {
	
	private Cell[] cells;
	private Cell[] cellsCenter;
	private Cell[] cellsOne;
	private Cell[] cellsTwo;
	private Cell[] cellsThree;
	private Cell[] cellsFour;
	
public SamuraiLogic(Gamestate gamestate, double timer, boolean isCorrect) {	super(gamestate, timer, isCorrect);
		this.cells = new Cell[441];
		this.cellsCenter = new Cell[81];
		this.cellsOne = new Cell[81];
		this.cellsTwo = new Cell[81];
		this.cellsFour = new Cell[81];
	}

@Override
public boolean checkRow(int row, int guess) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean checkCol(int col, int guess) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean checkBox(int row, int col, int guess) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean valid(int row, int col, int guess) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setUpLogicArray() {
	// TODO Auto-generated method stub
	
}

@Override
public void createSudoku() {
	// TODO Auto-generated method stub
	
}

@Override
public boolean hint() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean solveSudoku() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void difficulty(int diff) {
	// TODO Auto-generated method stub
	
}

@Override
public void printCells() {
	// TODO Auto-generated method stub
	
}

@Override
public Cell[][] getCells() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setCell(int col, int row, int guess) {
	// TODO Auto-generated method stub
	
}

@Override
public void validManualInput(Cell[][] cells) {
	// TODO Auto-generated method stub
	
}

}
