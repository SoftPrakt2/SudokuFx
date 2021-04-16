//package logic;
//
//public abstract class BasicGameLogic{
//
//	private Gamestate gamestate;
//	private double timer;
//	private boolean isCorrect;
//	
//	public BasicGameLogic(Gamestate gamestate, double timer, boolean isCorrect) {
//		super();
//		this.gamestate = gamestate;
//		this.timer = timer;
//		this.isCorrect = isCorrect;
//	}
//	
//	public boolean valid(){
//		if (checkRows() == true && checkCols() == true && checkBoxs() == true ) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	public abstract boolean checkRows();
//	public abstract boolean checkCols();
//	public abstract boolean checkBoxs();
//	
//	
//	public boolean validUser(){
//		if (checkRowsUser() == true && checkColsUser() == true && checkBoxsUser() == true ) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	public abstract boolean checkRowsUser();
//	public abstract boolean checkColsUser();
//	public abstract boolean checkBoxsUser();
//	
//	
//	public abstract void autofill();
//	
//	public abstract void fill();
//	
//	public abstract void hint();
//	
//	public abstract void autosolve();
//	
//	public abstract void difficulty(int diff);
//	
//	public abstract void printCells();
//}

package logic;

import application.SudokuField;

public abstract class BasicGameLogic{

	private Gamestate gamestate;
	private double timer;
	private boolean isCorrect;
	
	public BasicGameLogic(Gamestate gamestate, double timer, boolean isCorrect) {
		super();
		this.gamestate = gamestate;
		this.timer = timer;
		this.isCorrect = isCorrect;
	}
	
	public abstract boolean checkRow(int row, int guess);
	public abstract boolean checkCol(int col, int guess);
	public abstract boolean checkBox(int row, int col, int guess);
	public abstract boolean valid(int row, int col, int guess);
	
	

	

	public abstract void setUpLogicArray();
	
	public abstract void createSudoku();
	
	public abstract void hint();
	
	public abstract boolean solveSudoku();
	
	public abstract void difficulty(int diff);
	
	public abstract void printCells();
	
	public abstract Cell[][] getCells();
	
	public abstract void connectArrays(SudokuField[][] sudokuField);
	
	public abstract boolean compareResult(SudokuField[][] sudokuField);
	
	
}