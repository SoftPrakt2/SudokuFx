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
////
////	public Cell[] getCells() {
////		return cells;
////	}
////
////	public void setCells(Cell[] cells) {
////		this.cells = cells;
////	}
////
////	public Cell[] getCellsCenter() {
////		return cellsCenter;
////	}
////
////	public void setCellsCenter(Cell[] cellsCenter) {
////		this.cellsCenter = cellsCenter;
////	}
////
////	public Cell[] getCellsOne() {
////		return cellsOne;
////	}
////
////	public void setCellsOne(Cell[] cellsOne) {
////		this.cellsOne = cellsOne;
////	}
////
////	public Cell[] getCellsTwo() {
////		return cellsTwo;
////	}
////
////	public void setCellsTwo(Cell[] cellsTwo) {
////		this.cellsTwo = cellsTwo;
////	}
////
////	public Cell[] getCellsThree() {
////		return cellsThree;
////	}
////
////	public void setCellsThree(Cell[] cellsThree) {
////		this.cellsThree = cellsThree;
////	}
////
////	public Cell[] getCellsFour() {
////		return cellsFour;
////	}
////
////	public void setCellsFour(Cell[] cellsFour) {
////		this.cellsFour = cellsFour;
////	}
////
////	@Override
////	public boolean checkRows() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkCols() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkBoxs() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkRowsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkColsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkBoxsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public void autofill() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	public void fillSimple(int centralBox) {
////		
////		int a;
////		int count;
////		int durchlaufe = 100;
////		int universal = 0;
////		boolean isTrueOrFalse;
////		for (int k = 0; k < this.cells.length; k++) {
////			count = 0;
////			a = (int) (Math.random() * (10 - 1)) + 1;
////
////			this.cells[k].setValue(a);
////			isTrueOrFalse = valid();
////			while (isTrueOrFalse == false) {
////				count++;
////				universal++;
////				a = (int) (Math.random() * (10 - 1)) + 1;
////
////				this.cells[k].setValue(a);
////				isTrueOrFalse = valid();
////				if (count == durchlaufe && k < 9) {
////					for (int r = 0; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 0;
////				} else if (count == durchlaufe && k < 18) {
////					for (int r = 9; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 9;
////				} else if (count == durchlaufe && k < 27) {
////					for (int r = 18; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 18;
////				} else if (count == durchlaufe && k < 36) {
////					for (int r = 27; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 27;
////				} else if (count == durchlaufe && k < 45) {
////					for (int r = 36; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 36;
////				} else if (count == durchlaufe && k < 54) {
////					for (int r = 45; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 45;
////				} else if (count == durchlaufe && k < 63) {
////					for (int r = 54; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 54;
////				} else if (count == durchlaufe && k < 72) {
////					for (int r = 63; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 63;
////				} else if (count == durchlaufe && k < 81) {
////					for (int r = 72; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 72;
////				}
////			}
////		}
////	}
////	
////	@Override
////	public void fill() {
////		int a;
////		int count;
////		int durchlaufe = 100;
////		int universal = 0;
////		boolean isTrueOrFalse;
////		for (int k = 0; k < this.cells.length; k++) {
////			count = 0;
////			a = (int) (Math.random() * (10 - 1)) + 1;
////
////			this.cells[k].setValue(a);
////			isTrueOrFalse = valid();
////			while (isTrueOrFalse == false) {
////				count++;
////				universal++;
////				a = (int) (Math.random() * (10 - 1)) + 1;
////
////				this.cells[k].setValue(a);
////				isTrueOrFalse = valid();
////				if (count == durchlaufe && k < 9) {
////					for (int r = 0; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 0;
////				} else if (count == durchlaufe && k < 18) {
////					for (int r = 9; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 9;
////				} else if (count == durchlaufe && k < 27) {
////					for (int r = 18; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 18;
////				} else if (count == durchlaufe && k < 36) {
////					for (int r = 27; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 27;
////				} else if (count == durchlaufe && k < 45) {
////					for (int r = 36; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 36;
////				} else if (count == durchlaufe && k < 54) {
////					for (int r = 45; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 45;
////				} else if (count == durchlaufe && k < 63) {
////					for (int r = 54; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 54;
////				} else if (count == durchlaufe && k < 72) {
////					for (int r = 63; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 63;
////				} else if (count == durchlaufe && k < 81) {
////					for (int r = 72; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 72;
////				}
////			}
////		}
////	}
////
////	@Override
////	public void hint() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void autosolve() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void difficulty(int diff) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void printCells() {
////		// TODO Auto-generated method stub
////		
////	}
////
//////	@Override
//////	public void manualSolve() {
//////		// TODO Auto-generated method stub
//////		
//////	}
////
////
////}
//
//
//
//
//
//
//package logic;
//
//public class SamuraiLogic extends BasicGameLogic {
//
//	private Cell [][] cells;
//	
//	public SamuraiLogic(Gamestate gamestate, double timer, boolean isCorrect) {
//		super(gamestate, timer, isCorrect);
//		this.cells = new Cell[21][21];
//	}
//	
//	public void setCell(int row, int col, int guess) {
//		this.cells[row][col].setValue(guess);
//	}
//	
//	public Cell[][] getCells(){
//		return this.cells;
//	}
//
//	@Override
//	public boolean checkRow(int row, int guess) {
//		
//		return false;
//	}
//
//	@Override
//	public boolean checkCol(int col, int guess) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean checkBox(int row, int col, int guess) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean valid(int row, int col, int guess) {
//		if(row > 5 && row < 15 && col > 5 && col < 15) {
//			for(; row < 15; row++) {
//				for(;col < 15; col++) {
//					if(this.cells[row][col].getValue() == guess) {
//						return false;
//					}
//				}
//			}
//		} else
//		if(row < 9 && col < 9) {
//			for(; row < 9; row++) {
//				for(;col < 9; col++) {
//					if(this.cells[row][col].getValue() == guess) {
//						return false;
//					}
//				}
//			}
//		} else 
//		if(row < 9 && col > 11) {
//			for(; row < 9; row++) {
//				for(;col < 21; col++) {
//					if(this.cells[row][col].getValue() == guess) {
//						return false;
//					}
//				}
//			}
//		} else
//		if(row > 11 && col < 9) {
//			for(; row < 21; row++) {
//				for(;col < 9; col++) {
//					if(this.cells[row][col].getValue() == guess) {
//						return false;
//					}
//				}
//			}
//		} else
//		if(row > 11 && col > 11) {
//			for(; row < 21; row++) {
//				for(;col < 21; col++) {
//					if(this.cells[row][col].getValue() == guess) {
//						return false;
//					}
//				}
//			}
//		}
//		
//		return true;
//	}
//
//	@Override
//	public boolean checkRowsUser() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean checkColsUser() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean checkBoxsUser() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void autofill() {
//		// TODO Auto-generated method stub
//		int box = 1;
//		int uid = 0;
//		for(int i = 0; i < this.cells.length; i++) {
//			for(int j = 0; j < this.cells[i].length; j++) {
//				if(i < 3 && j < 3) {
//					box = 1;
//				}
//				else if(i < 3 && j < 6) {
//					box = 2;
//				}
//				else if(i < 3 && j < 9) {
//					box = 3;
//				}
//				else if(i < 3 && j < 12) {
//					box = 4;
//				}
//				else if(i < 3 && j < 15) {
//					box = 5;
//				}
//				else if(i < 3 && j < 18) {
//					box = 6;
//				}
//				else if(i < 3 && j < 21) {
//					box = 7;
//				}
//				
//				else if(i < 6 && j < 3) {
//					box = 8;
//				}
//				else if(i < 6 && j < 6) {
//					box = 9;
//				}
//				else if(i < 6 && j < 9) {
//					box = 10;
//				}
//				else if(i < 6 && j < 12) {
//					box = 11;
//				}
//				else if(i < 6 && j < 15) {
//					box = 12;
//				}
//				else if(i < 6 && j < 18) {
//					box = 13;
//				}
//				else if(i < 6 && j < 21) {
//					box = 14;
//				}
//				
//				else if(i < 9 && j < 3) {
//					box = 15;
//				}
//				else if(i < 9 && j < 6) {
//					box = 15;
//				}
//				else if(i < 9 && j < 9) {
//					box = 16;
//				}
//				else if(i < 9 && j < 12) {
//					box = 17;
//				}
//				else if(i < 9 && j < 15) {
//					box = 18;
//				}
//				else if(i < 9 && j < 18) {
//					box = 19;
//				}
//				else if(i < 9 && j < 21) {
//					box = 20;
//				}
//				
//				else if(i < 12 && j < 3) {
//					box = 21;
//				}
//				else if(i < 12 && j < 6) {
//					box = 22;
//				}
//				else if(i < 12 && j < 9) {
//					box = 23;
//				}
//				else if(i < 12 && j < 12) {
//					box = 24;
//				}
//				else if(i < 12 && j < 15) {
//					box = 25;
//				}
//				else if(i < 12 && j < 18) {
//					box = 26;
//				}
//				else if(i < 12 && j < 21) {
//					box = 27;
//				}
//				
//				else if(i < 15 && j < 3) {
//					box = 28;
//				}
//				else if(i < 15 && j < 6) {
//					box = 29;
//				}
//				else if(i < 15 && j < 9) {
//					box = 30;
//				}
//				else if(i < 15 && j < 12) {
//					box = 31;
//				}
//				else if(i < 15 && j < 15) {
//					box = 32;
//				}
//				else if(i < 15 && j < 18) {
//					box = 33;
//				}
//				else if(i < 15 && j < 21) {
//					box = 34;
//				}
//				
//				else if(i < 18 && j < 3) {
//					box = 35;
//				}
//				else if(i < 18 && j < 6) {
//					box = 36;
//				}
//				else if(i < 18 && j < 9) {
//					box = 37;
//				}
//				else if(i < 18 && j < 12) {
//					box = 38;
//				}
//				else if(i < 18 && j < 15) {
//					box = 39;
//				}
//				else if(i < 18 && j < 18) {
//					box = 40;
//				}
//				else if(i < 18 && j < 21) {
//					box = 41;
//				}
//				
//				else if(i < 21 && j < 3) {
//					box = 42;
//				}
//				else if(i < 21 && j < 6) {
//					box = 43;
//				}
//				else if(i < 21 && j < 9) {
//					box = 44;
//				}
//				else if(i < 21 && j < 12) {
//					box = 45;
//				}
//				else if(i < 21 && j < 15) {
//					box = 46;
//				}
//				else if(i < 21 && j < 18) {
//					box = 47;
//				}
//				else if(i < 21 && j < 21) {
//					box = 48;
//				}
//				
//				
//				Cell cell = new Cell(uid, i, j, box, 0, 0);
//				cells[i][j] = cell;
//				uid++;
//			}
//		}
//	}
//
//	@Override
//	public void fill() {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void hint() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	static int counter;
//	@Override
//	public boolean autoSolve() {
//		
//		int a = 0;
//		for(int i = 0; i < this.cells.length; i++) {
//			for(int j = 0; j < this.cells[i].length; j++) {
//				if((i < 9 || i > 11) && (j < 9 || j > 11)) {
//					if(this.cells[i][j].getValue() == 0) {
//						counter++;
//						for(int y = 0; y < this.cells.length; y++) {
//							a = (int)(Math.random() * 9) + 1;
//							if(valid(i, j, a)) {
//								this.cells[i][j].setValue(a);
//								if(autoSolve() == true) {
//									return true;
//								}
//								else {
//									this.cells[i][j].setValue(0);;
//								}
//							}
//						}
//						return false;
//					}
//				}
//			}
//		}
//		System.out.println(counter);
//		return true;
//	}
//
//	@Override
//	public void difficulty(int diff) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void printCells() {
//		// TODO Auto-generated method stub
//		for(int i = 0; i < this.cells.length; i++) {
//			for(int j = 0; j < this.cells.length; j++) {
//				System.out.print(this.cells[i][j].getValue() + " ");
//				if(j == 2 || j == 5 || j == 8 || j == 11 || j == 14 || j == 17 || j == 20) {
//					System.out.print("|");
//				}
//			}
//			System.out.println();
//			if(i == 2 || i == 5 || i == 8 || i == 11 || i == 14 || i == 17 || i == 20) {
//				System.out.println("-------------------");
//			}
//		}
//	}
//	
////	private Cell[] cells;
////	private Cell[] cellsCenter;
////	private Cell[] cellsOne;
////	private Cell[] cellsTwo;
////	private Cell[] cellsThree;
////	private Cell[] cellsFour;
////	
////	public SamuraiLogic(Gamestate gamestate, double timer, boolean isCorrect) {
////		super(gamestate, timer, isCorrect);
////		this.cells = new Cell[441];
////		this.cellsCenter = new Cell[81];
////		this.cellsOne = new Cell[81];
////		this.cellsTwo = new Cell[81];
////		this.cellsFour = new Cell[81];
////	}
////
////	public Cell[] getCells() {
////		return cells;
////	}
////
////	public void setCells(Cell[] cells) {
////		this.cells = cells;
////	}
////
////	public Cell[] getCellsCenter() {
////		return cellsCenter;
////	}
////
////	public void setCellsCenter(Cell[] cellsCenter) {
////		this.cellsCenter = cellsCenter;
////	}
////
////	public Cell[] getCellsOne() {
////		return cellsOne;
////	}
////
////	public void setCellsOne(Cell[] cellsOne) {
////		this.cellsOne = cellsOne;
////	}
////
////	public Cell[] getCellsTwo() {
////		return cellsTwo;
////	}
////
////	public void setCellsTwo(Cell[] cellsTwo) {
////		this.cellsTwo = cellsTwo;
////	}
////
////	public Cell[] getCellsThree() {
////		return cellsThree;
////	}
////
////	public void setCellsThree(Cell[] cellsThree) {
////		this.cellsThree = cellsThree;
////	}
////
////	public Cell[] getCellsFour() {
////		return cellsFour;
////	}
////
////	public void setCellsFour(Cell[] cellsFour) {
////		this.cellsFour = cellsFour;
////	}
////
////	@Override
////	public boolean checkRows() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkCols() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkBoxs() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkRowsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkColsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkBoxsUser() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public void autofill() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	public void fillSimple(int centralBox) {
////		
////		int a;
////		int count;
////		int durchlaufe = 100;
////		int universal = 0;
////		boolean isTrueOrFalse;
////		for (int k = 0; k < this.cells.length; k++) {
////			count = 0;
////			a = (int) (Math.random() * (10 - 1)) + 1;
////
////			this.cells[k].setValue(a);
////			isTrueOrFalse = valid();
////			while (isTrueOrFalse == false) {
////				count++;
////				universal++;
////				a = (int) (Math.random() * (10 - 1)) + 1;
////
////				this.cells[k].setValue(a);
////				isTrueOrFalse = valid();
////				if (count == durchlaufe && k < 9) {
////					for (int r = 0; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 0;
////				} else if (count == durchlaufe && k < 18) {
////					for (int r = 9; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 9;
////				} else if (count == durchlaufe && k < 27) {
////					for (int r = 18; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 18;
////				} else if (count == durchlaufe && k < 36) {
////					for (int r = 27; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 27;
////				} else if (count == durchlaufe && k < 45) {
////					for (int r = 36; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 36;
////				} else if (count == durchlaufe && k < 54) {
////					for (int r = 45; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 45;
////				} else if (count == durchlaufe && k < 63) {
////					for (int r = 54; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 54;
////				} else if (count == durchlaufe && k < 72) {
////					for (int r = 63; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 63;
////				} else if (count == durchlaufe && k < 81) {
////					for (int r = 72; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 72;
////				}
////			}
////		}
////	}
////	
////	@Override
////	public void fill() {
////		int a;
////		int count;
////		int durchlaufe = 100;
////		int universal = 0;
////		boolean isTrueOrFalse;
////		for (int k = 0; k < this.cells.length; k++) {
////			count = 0;
////			a = (int) (Math.random() * (10 - 1)) + 1;
////
////			this.cells[k].setValue(a);
////			isTrueOrFalse = valid();
////			while (isTrueOrFalse == false) {
////				count++;
////				universal++;
////				a = (int) (Math.random() * (10 - 1)) + 1;
////
////				this.cells[k].setValue(a);
////				isTrueOrFalse = valid();
////				if (count == durchlaufe && k < 9) {
////					for (int r = 0; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 0;
////				} else if (count == durchlaufe && k < 18) {
////					for (int r = 9; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 9;
////				} else if (count == durchlaufe && k < 27) {
////					for (int r = 18; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 18;
////				} else if (count == durchlaufe && k < 36) {
////					for (int r = 27; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 27;
////				} else if (count == durchlaufe && k < 45) {
////					for (int r = 36; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 36;
////				} else if (count == durchlaufe && k < 54) {
////					for (int r = 45; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 45;
////				} else if (count == durchlaufe && k < 63) {
////					for (int r = 54; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 54;
////				} else if (count == durchlaufe && k < 72) {
////					for (int r = 63; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 63;
////				} else if (count == durchlaufe && k < 81) {
////					for (int r = 72; r <= k; r++) {
////						cells[r].setValue(0);
////					}
////					k = 72;
////				}
////			}
////		}
////	}
////
////	@Override
////	public void hint() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void autosolve() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void difficulty(int diff) {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void printCells() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public void manualSolve() {
////		// TODO Auto-generated method stub
////		
////	}
////
////	@Override
////	public boolean checkRow(int row, int guess) {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkCol(int col, int gues) {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean checkBox(int row, int col, int gues) {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean valid(int row, int col, int guess) {
////		// TODO Auto-generated method stub
////		return false;
////	}
//
//

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
public void hint() {
	// TODO Auto-generated method stub
	
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
public void connectArrays(SudokuField[][] sudokuField) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean compareResult(SudokuField[][] sudokuField) {
	// TODO Auto-generated method stub
	return false;
}

//@Override
//public void connectArrays(SudokuField[][] sudokuField) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public boolean compareResult(SudokuField[][] sudokuField) {
//	// TODO Auto-generated method stub
//	return false;
//}
//
//@Override
//public void connectArrays(SudokuField[][] sudokuField) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public boolean compareResult(SudokuField[][] sudokuField) {
//	// TODO Auto-generated method stub
//	return false;
//}
}
