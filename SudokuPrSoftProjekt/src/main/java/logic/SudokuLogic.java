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
		if (checkRow(row, guess) == true && checkCol(col, guess) == true && checkBox(row, col, guess) == true ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkRow(int row, int guess) {
		for(int i = 0; i < this.cells.length; i++) {
			if(this.cells[row][i].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkCol(int col, int guess) {
		for(int i = 0; i < this.cells.length; i++) {
			if(this.cells[i][col].getValue() == guess) {
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
		
		for(int i = 0; i < this.cells.length; i++) {
			counter = 0;
			for(int j = 0; j < this.cells[i].length; j++) {
				a = (int) (Math.random() * 9) + 1;
				isTrueOrFalse = valid(i, j, a);
				globalCounter++;
				universalCounter++;
				if(isTrueOrFalse) {
					this.cells[i][j].setValue(a);
				}
				else {
					while(!isTrueOrFalse) {
						counter++;
						a = (int) (Math.random() * 9) + 1;
						isTrueOrFalse = valid(i, j, a);
						if(isTrueOrFalse) {
							this.cells[i][j].setValue(a);;
							break;
						}
						if(counter == 100) {
							for(int y = 0; y < cells[i].length; y++) {
								this.cells[i][y].setValue(0);   //hier geändert1111111111111111111111111111111111111111
							}
							j = -1;
							counter = 0;
							break;
						}
						if(globalCounter == 5000) {
							i = 0;
							j = 0;
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
	
	//löst restliches sudoku automatisch
	public boolean solveSudoku() {	
		for(int i = 0; i < this.cells.length; i++) {
			for(int j = 0; j < this.cells[i].length; j++) {
				if(this.cells[i][j].getValue() == 0) {
					for(int y = 0; y < this.cells.length; y++) {
						counter++;
						if(valid(i, j, y + 1)) {
							this.cells[i][j].setValue(y + 1);
							if(solveSudoku()) {
								return true;
							}
							else {
								this.cells[i][j].setValue(0);
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
	public void hint() {
		int randomRow = (int)(Math.random() * 9) + 1;
		int randomCol = (int)(Math.random() * 9) + 1;
		
		for(int i = 0; i < this.cells.length; i++) {
			for(int j = 0; j < this.cells[i].length; j++) {
				if(this.cells[i][j].getValue() == 0 && i == randomRow && j == randomCol) {
					
				}
				else if(this.cells[i][j].getValue() != 0 && i == randomRow && j == randomCol) {
					randomRow = (int)(Math.random() * 9) + 1;
					randomCol = (int)(Math.random() * 9) + 1;
					i = -1;
					j = -1;
					break;
				}
			}
		}
	}



	@Override
	public void setUpLogicArray() {		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		int box = 1;
		int uid = 0;
		for(int i = 0; i < this.cells.length; i++) {
			for(int j = 0; j < this.cells[i].length; j++) {
				if(i < 3 && j < 3) {
					box = 1;
				}
				else if(i < 3 && j < 3) {
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
		
		for(int i = 0; i < this.cells.length;i++) {
			for(int j = 0; j < this.cells[i].length;j++) {
			long random =(int) (Math.random()*10)+1;
			if(random <= diff) {
				this.cells[i][j].setIsReal(true);
			} else {
				this.cells[i][j].setValue(0);
			}
			}
		}
	}

	public void setCell(int col, int row, int guess) {
		this.cells[col][row].setValue(guess);
		this.cells[col][row].setIsReal(true);
	}

	public void printCells() {	
		for(int i = 0; i < this.cells.length; i++) {
			for(int j = 0; j < this.cells[i].length; j++) {
				System.out.print(this.cells[i][j].getValue() + " ");
				if(j == 2 || j == 5) {
					System.out.print("|");
				}
			}
			System.out.println();
			if(i == 2 || i == 5 || i == 8) {
				System.out.println("-------------------");
			}
		}
	}
	
	
		//updatet den stand des sudoku arrays auf den stand des logik arrays, nach autosolve immer?
	public void connectArrays(SudokuField[][] sudokuField) {
		for(int i = 0; i < sudokuField.length; i++) {
			for(int j = 0; j < sudokuField[i].length;j++) {
			sudokuField[i][j].setText(Integer.toString(cells[j][i].getValue()));
		}
	}	
	}
	
	public boolean compareResult(SudokuField[][] sudokuField) {
			boolean result = true;
			this.solveSudoku();
		
			for(int i = 0; i < sudokuField.length; i++) {
				for(int j = 0; j < sudokuField[i].length;j++) {
						if(!sudokuField[i][j].getText().equals(Integer.toString(cells[j][i].getValue())) && !sudokuField[i][j].getText().equals("0")) {
						sudokuField[i][j].setStyle("-fx-text-fill: red");
						result = false;
						}else if (sudokuField[i][j].getText().equals("0"))
							{sudokuField[i][j].setStyle("-fx-text-fill: black");
							}
						else {
							sudokuField[i][j].setStyle("-fx-text-fill: green");
						}
			}
		}
		return result;
	}


}