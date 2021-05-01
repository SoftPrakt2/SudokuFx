package logic;

import application.SudokuField;




public class SamuraiLogic extends BasicGameLogic {
	
	private Cell[][] cells;
	
	public SamuraiLogic(Gamestate gamestate, double timer, boolean isCorrect) {	
		super(gamestate, timer, isCorrect);
		this.cells = new Cell [21][21];		
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
}

@Override
public Cell[][] getCells() {
	// TODO Auto-generated method stub
	return this.cells;
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
