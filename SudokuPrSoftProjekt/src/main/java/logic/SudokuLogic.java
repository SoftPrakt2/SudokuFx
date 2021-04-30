package logic;

import application.SudokuField;

public class SudokuLogic extends BasicGameLogic {

	private Cell [][] cells;
	

	public SudokuLogic(Gamestate gamestate, double timer, boolean isCorrect) {
		super(gamestate, timer, isCorrect);
		this.cells = new Cell[9][9];
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	@Override
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, guess)  && checkCol(col, guess) && checkBox(row, col, guess) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public  void validManualInput(Cell[][] cells) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {

			}
		}
	}
	
	
	@Override
	public boolean checkRow(int row, int guess) {
		for(int col = 0; col < this.cells.length; col++) {
			if(this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkCol(int col, int guess) {
		for(int row = 0; row < this.cells.length; row++) {
			if(this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean checkBox(int row, int col, int guess) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		for(int i = r; i < r + 3; i++) {
			for(int j = c; j < c + 3; j++) {
				if(this.cells[i][j].getValue() == guess) {
					return false;
				}
			}
		}
		return true;
	}


	//erstellt generell sudoku spiel mit richtigen ziffern
	public void createSudoku() {
		int a;
		int counter;
		int globalCounter = 0;
		int universalCounter = 0;
		boolean isTrueOrFalse = false;
		
		for(int row = 0; row < this.cells.length; row++) {
			counter = 0;
			for(int col = 0; col < this.cells[row].length; col++) {
				a = (int) (Math.random() * 9) + 1;
				isTrueOrFalse = valid(row, col, a);
				globalCounter++;
				universalCounter++;
				if(isTrueOrFalse) {
					this.cells[row][col].setValue(a);
				}
				else {
					while(!isTrueOrFalse) {
						counter++;
						a = (int) (Math.random() * 9) + 1;
						isTrueOrFalse = valid(row, col, a);
						if(isTrueOrFalse) {
							this.cells[row][col].setValue(a);;
							break;
						}
						if(counter == 100) {
							for(int colTwo = 0; colTwo < cells[row].length; colTwo++) {
								this.cells[row][colTwo].setValue(0);   
							}
							col = -1;
							counter = 0;
							break;
						}
						if(globalCounter == 5000) {
							row = 0;
							col = 0;
							globalCounter = 0;
							break;
						}
						globalCounter++;
						universalCounter++;
					}
				}
			}
		}
		System.out.println("Global Counter = " + universalCounter);	
	}
	
	static int counter = 0;
	
	public boolean solveSudoku() {	
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				if(this.cells[row][col].getValue() == 0) {
					for(int y = 0; y < this.cells.length; y++) {
						counter++;
						if(valid(row, col, y + 1)) {
							this.cells[row][col].setValue(y + 1);
							if(solveSudoku()) {
								return true;
							}
							else {
								this.cells[row][col].setValue(0);
							}
						}
					}
					return false;
				}
			}
		}
		System.out.println(counter);
		return true;
	}

	@Override
	public boolean hint() {
		int randomRow = (int)(Math.random() * 8);
		int randomCol = (int)(Math.random() * 8);
		int randomGuess = (int)(Math.random() * 9) + 1;
		
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				while(this.cells[row][col].getValue() != 0 && row == randomRow && col == randomCol) {
					randomRow = (int)(Math.random() * 8);
					randomCol = (int)(Math.random() * 8);
					row = 0;
					col = 0;
					break;
				}
				if(this.cells[row][col].getValue() == 0 && row == randomRow && col == randomCol) {
					while(!valid(row, col, randomGuess)) {
						randomGuess = (int)(Math.random() * 9) + 1;
					}
					this.cells[row][col].setValue(randomGuess);
					return true;
				}
			}
		}
		return false;
	}



	@Override
	public void setUpLogicArray() {		
		

		int box = 1;
		int uid = 0;
		for(int i = 0; i < this.cells.length; i++) {
			for(int j = 0; j < this.cells[i].length; j++) {
				if(i < 3 && j < 3) {
					box = 1;
				}
				
				else if(i < 3 && j < 6) {
					box = 2;
				}
				else if(i < 3 && j < 9) {
					box = 3;
				}
				else if(i < 6 && j < 3) {
					box = 4;
				}
				else if(i < 6 && j < 6) {
					box = 5;
				}
				else if(i < 6 && j < 9) {
					box = 6;
				}
				else if(i < 9 && j < 3) {
					box = 7;
				}
				else if(i < 9 && j < 6) {
					box = 8;
				}
				else if(i < 9 && j < 9) {
					box = 9;
				}
				Cell cell = new Cell(uid, i, j, box, 0, 0);
				cells[i][j] = cell;
				uid++;
			}
		}
	}

	public void difficulty(int diff) {
		
		for(int row = 0; row < this.cells.length;row++) {
			for(int j = 0; j < this.cells[row].length;j++) {
			int random =(int) (Math.random()*10)+1;
			if(random <= diff) {
				this.cells[row][j].setIsReal(true);
			} else {
				this.cells[row][j].setValue(0);
			}
			}
		}
	}

	public void setCell(int col, int row, int guess) {
		this.cells[col][row].setValue(guess);
		this.cells[col][row].setIsReal(true);
	}

	public void printCells() {	
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				System.out.print(this.cells[row][col].getValue() + " ");
				if(col == 2 || col == 5) {
					System.out.print("|");
				}
			}
			System.out.println();
			if(row == 2 || row == 5 || row == 8) {
				System.out.println("-------------------");
			}
		}
	}
		
		//muss noch besprochen werden in welche klasse die methoden gehören
	
		//updatet den stand des sudoku arrays auf den stand des logik arrays, nach autosolve immer?


	


}