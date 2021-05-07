package logic;

import application.SudokuField;

public class SudokuLogic extends BasicGameLogic {
	
	private Cell[][] cells;
	static int counter = 0;

	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
        super(gamestate, minutesPlayed,secondsPlayed, isCorrect);
        this.cells = new Cell[9][9];
    }

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * Überprüft ob die Methoden checkRow, checkCol und CheckBox true zurückgeben
	 */
	@Override
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, guess) && checkCol(col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * Überprüft in der übergebenen Reihe ob eine idente Zahl vorhanden ist
	 */
	@Override
	public boolean checkRow(int row, int guess) {
		for (int col = 0; col < this.cells.length; col++) {
			if (this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Überprüft in der übergebenen Zeile ob eine idente Zahl vorhanden ist
	 */
	@Override
	public boolean checkCol(int col, int guess) {
		for (int row = 0; row < this.cells.length; row++) {
			if (this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Überprüft in der Box der übergebenen Reihe und Zeile eine idente Zahl vorhanden ist.
	 */
	@Override
	public boolean checkBox(int row, int col, int guess) {
		int r = row - row % 3;
		int c = col - col % 3;

		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (this.cells[i][j].getValue() == guess) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Autogenerator für ein neues Sudoku
	 * Befüllt rekursiv das im Hintergrund liegene Sudoku-Array.
	 */
	public boolean createSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
//						counter++;
						int a = (int) (Math.random() * 9) + 1;
						if (valid(row, col, a)) {
							this.cells[row][col].setValue(a);
							if (createSudoku()) {
								return true;
							} else {
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

	/*
	 * Löst ein Sudoku rekursiv
	 */
	public boolean solveSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
						counter++;
						if(counter == 150000) {
							return true;
						}
						if (valid(row, col, y + 1)) {
							this.cells[row][col].setValue(y + 1);
							if (solveSudoku()) {
								return true;
							} else {
								this.cells[row][col].setValue(0);							
								System.out.println("Test");
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

	/**
	 * Generiert automatische eine zufällig Zahl
	 * Hilfsarray mit den derzeitigen Werten des Sudokus wird erstellt
	 * Generiert daraufhin eine Lösung des derzeitigen Sudokustandes
	 * Überprüft ob generiert Zahl laut Lösung in die Zelle passt und fügt diese in Hilfsarray ein
	 * derzeitige Sudoku nimmt Werte des Hilfsarray an
	 */
	@Override
	public int[] hint() {
		int[] coordinates = new int[2];
		int randomGuess = (int) (Math.random() * 9) + 1;
		int counter = 0;
		int counter2 = 0;
		int counter3 = 0;

		int help[][] = new int[9][9];
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				help[row][col] = this.cells[row][col].getValue();
			}
		}
		this.solveSudoku();

		for (int row = 0; row < this.cells.length; row++) {
			counter2++;
			counter3++;
			for (int col = 0; col < this.cells[row].length; col++) {
				if (help[row][col] == 0 && this.cells[row][col].getValue() == randomGuess) {
					help[row][col] = randomGuess;
					coordinates[0] = row;
					coordinates[1] = col;
					counter++;
					break;
				}
			}
			if (counter3 == 50) {
				coordinates = null;
				break;
			}
			if (counter2 == 8) {
				row = 0;
				counter2 = 0;
				randomGuess = (int) (Math.random() * 9) + 1;
			}
			if (counter == 1) {
				break;
			}
		}
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				this.getCells()[row][col].setValue(help[row][col]);
//				this.setCell(row, col, help[row][col]);
			}
		}
		return coordinates;
	}

	/**
	 * Befüllt Sudokuarray mit mit dem Wert 0
	 */
	@Override
	public void setUpLogicArray() {
        int box = 1;
        int uid = 0;
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (i < 3 && j < 3) {
                    box = 1;
                }

                else if (i < 3 && j < 6) {
                    box = 2;
                } else if (i < 3 && j < 9) {
                    box = 3;
                } else if (i < 6 && j < 3) {
                    box = 4;
                } else if (i < 6 && j < 6) {
                    box = 5;
                } else if (i < 6 && j < 9) {
                    box = 6;
                } else if (i < 9 && j < 3) {
                    box = 7;
                } else if (i < 9 && j < 6) {
                    box = 8;
                } else if (i < 9 && j < 9) {
                    box = 9;
                }
                Cell cell = new Cell(uid, i, j, box, 0, 0);
                cells[i][j] = cell;
                uid++;
            }
        }
    }

	/**
	 * Abhängig von der Übergebenen Zahl werden zufällige Zahl des Arrays auf 0 gesetzt
	 */
	public void difficulty(int diff) {
		for (int row = 0; row < this.cells.length; row++) {
			for (int j = 0; j < this.cells[row].length; j++) {
				int random = (int) (Math.random() * 10) + 1;
				if (random <= diff) {
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
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				System.out.print(this.cells[row][col].getValue() + " ");
				if (col == 2 || col == 5) {
					System.out.print("|");
				}
			}
			System.out.println();
			if (row == 2 || row == 5 || row == 8) {
				System.out.println("-------------------");
			}
		}
	}

	/**
	 * Getter und Setter der Variablen
	 */
	@Override
	public void setGameState(Gamestate gamestate) {
		// TODO Auto-generated method stub
		this.gamestate = gamestate;
	}

	@Override
	public Gamestate getGameState() {
		// TODO Auto-generated method stub
		return this.gamestate;
	}


	// muss noch besprochen werden in welche klasse die methoden gehören

	// updatet den stand des sudoku arrays auf den stand des logik arrays, nach
	// autosolve immer?

}