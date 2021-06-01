package logic;

import application.SudokuField;

public class FreeFormLogic extends BasicGameLogic {

	private Cell[][] cells;
	static int counter = 0;

	public FreeFormLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gametype = "FreeForm";
		hintCounter = 3;
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
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * Überprüft in der übergebenen Reihe ob eine idente Zahl vorhanden ist
	 */
	@Override
	public boolean checkRow(int row, int col, int guess) {
		for (col = 0; col < this.cells.length; col++) {
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
	public boolean checkCol(int row, int col, int guess) {
		for (row = 0; row < this.cells.length; row++) {
			if (this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Überprüft in der Box der übergebenen Reihe und Zeile eine idente Zahl
	 * vorhanden ist.
	 */
	@Override
	public boolean checkBox(int row, int col, int guess) {
		int toCheckBox = cells[row][col].getBox();
		for (int i = 0; i < this.cells.length; i++) {
			for (int j = 0; j < this.cells[i].length; j++) {
			if (cells[i][j].getBox() == toCheckBox)
				if (this.cells[i][j].getValue() == guess) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Autogenerator für ein neues Sudoku Befüllt rekursiv das im Hintergrund
	 * liegene Sudoku-Array.
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
	@Override
	public boolean solveSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
						counter++;
//						if(counter == 500000) {
//							return true;
//						}
						if (valid(row, col, y + 1)) {
							this.cells[row][col].setValue(y + 1);
							if (solveSudoku()) {
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


	/**
	 * Generiert automatische eine zufällig Zahl Hilfsarray mit den derzeitigen
	 * Werten des Sudokus wird erstellt Generiert daraufhin eine Lösung des
	 * derzeitigen Sudokustandes Überprüft ob generiert Zahl laut Lösung in die
	 * Zelle passt und fügt diese in Hilfsarray ein derzeitige Sudoku nimmt Werte
	 * des Hilfsarray an
	 */
	@Override
	public int[] hint() {
		int randomGuess = (int) (Math.random() * 9) + 1;
		int [] coordinates = new int [2];
		int counter = 0;
		int counter2 = 0;
		int counter3 = 0;

		int help[][] = new int[this.cells.length][this.cells.length];
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
			if (counter3 == 500) {
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
				this.cells[row][col].setValue(help[row][col]);
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
				Cell cell = new Cell(i, j, box, 0);
				cells[i][j] = cell;
				uid++;
			}
		}
	}

	/**
	 * Abhängig von der Übergebenen Zahl werden zufällige Zahl des Arrays auf 0
	 * gesetzt
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
		this.gamestate = gamestate;
	}

	@Override
	public Gamestate getGamestate() {
		return this.gamestate;
	}

	@Override
	public boolean testIfSolved() {
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				if(this.cells[row][col].getValue() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	// muss noch besprochen werden in welche klasse die methoden gehören

	// updatet den stand des sudoku arrays auf den stand des logik arrays, nach
	// autosolve immer?
	


	
	
	// shuffle(): eine Zelle zufällig einer neuen Box zuweisen
	@Override
	public void shuffle() {
		int localcount = 0;
		
		//erste 21 Durchläfufe
		for(int z = 0; z <4000; z++) { 
			Cell[][] rollbackCells = new Cell[9][9];
				for (int i = 0; i < this.cells.length; i++) {
					for (int j = 0; j < this.cells[i].length; j++) {
						rollbackCells[i][j] = new Cell(0,0,0,0);
						rollbackCells[i][j].setBox(cells[i][j].getBox());
					}
			}
			int rand1 = (int) (Math.floor(Math.random() * 8.9999));
			int rand2 = (int) (Math.floor(Math.random() * 8.9999));
			proofEdgesAndSetNewBox(rand1, rand2);
			localcount++;
			if (proofNrCells() == false) {
//				System.out.println("Box-Array ausgeschöpft - Rollback ");
				for (int i = 0; i < this.cells.length; i++) {
					for (int j = 0; j < this.cells[i].length; j++) {
						cells[i][j].setBox(rollbackCells[i][j].getBox());
					}
				}
			} else {
//				System.out.println("Box-Array korrekt - Apply ");
			}
//			System.out.println("Count: " + localcount);
		}
		
		//Durchläufe bis Array mit je 9 gefüllt ist
		while (nineNrCells() == false) {
			Cell[][] rollbackCells = new Cell[9][9];
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
					rollbackCells[i][j] = new Cell(0,0,0,0);
					rollbackCells[i][j].setBox(cells[i][j].getBox());
				}
		}
		int rand1 = (int) (Math.floor(Math.random() * 8.9999));
		int rand2 = (int) (Math.floor(Math.random() * 8.9999));
		proofEdgesAndSetNewBox(rand1, rand2);
		localcount++;
		if (proofNrCells() == false) {
//			System.out.println("Box-Array ausgeschöpft - Rollback ");
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
					cells[i][j].setBox(rollbackCells[i][j].getBox());
				}
			}
		} else {
//			System.out.println("Box-Array korrekt - Apply ");
		}
//		System.out.println("Count: " + localcount);
		}
		
		
		// Farben vergeben
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j].getBox() == 1) {
					cells[i][j].setBoxcolor("B8860B");
				} else if (cells[i][j].getBox() == 2) {
					cells[i][j].setBoxcolor("7B68EE");
				} else if (cells[i][j].getBox() == 3) {
					cells[i][j].setBoxcolor("7FFF00");
				} else if (cells[i][j].getBox() == 4) {
					cells[i][j].setBoxcolor("8FBC8F");
				}else if (cells[i][j].getBox() == 5) {
					cells[i][j].setBoxcolor("8B008B");
				}else if (cells[i][j].getBox() == 6) {
					cells[i][j].setBoxcolor("B22222");
				}else if (cells[i][j].getBox() == 7) {
					cells[i][j].setBoxcolor("E6E6FA");
				}else if (cells[i][j].getBox() == 8) {
					cells[i][j].setBoxcolor("20B2AA");
				}else if (cells[i][j].getBox() == 9) {
					cells[i][j].setBoxcolor("FFE4C4");
				}
			}
		}
	}
	
		public boolean proofNrCells() {
			int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			int box;
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
				box = cells[i][j].getBox();
				cells1to9[box - 1] += 1;
				}
			}
			for (int nk = 0; nk < 9; nk++) {
//				System.out.println((nk + 1) + " -.- " + cells1to9[nk] + " -.- ");
				if (cells1to9[nk] > 10 || cells1to9[nk] < 8) {
//					System.out.println(" ! Invalid Array { "+ cells1to9[0]+"-"+ cells1to9[1]+"-"+ cells1to9[2]+"-"+ cells1to9[3]+"-"+ cells1to9[4]+"-"+ cells1to9[5]+"-"+ cells1to9[6]+"-"+ cells1to9[7]+"-"+cells1to9[8]+"}");
					return false;
				}
			}
//			System.out.println(" Valid Array { "+ cells1to9[0]+"-"+ cells1to9[1]+"-"+ cells1to9[2]+"-"+ cells1to9[3]+"-"+ cells1to9[4]+"-"+ cells1to9[5]+"-"+ cells1to9[6]+"-"+ cells1to9[7]+"-"+cells1to9[8]+"}");
			return true;
		}

		public boolean nineNrCells() {
			int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			int box;
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
				box = cells[i][j].getBox();
				cells1to9[box - 1] += 1;
				}
			}

			for (int nk = 0; nk < 9; nk++) {
//				System.out.println((nk + 1) + " -.- " + cells1to9[nk] + " -.- ");
				if (cells1to9[nk] != 9) {
					System.out.println(" Invalid 9-Array { "+ cells1to9[0]+"-"+ cells1to9[1]+"-"+ cells1to9[2]+"-"+ cells1to9[3]+"-"+ cells1to9[4]+"-"+ cells1to9[5]+"-"+ cells1to9[6]+"-"+ cells1to9[7]+"-"+cells1to9[8]+"}");					
					return false;
				}
			}
//			System.out.println(" Valid 9-Array { "+ cells1to9[0]+"-"+ cells1to9[1]+"-"+ cells1to9[2]+"-"+ cells1to9[3]+"-"+ cells1to9[4]+"-"+ cells1to9[5]+"-"+ cells1to9[6]+"-"+ cells1to9[7]+"-"+cells1to9[8]+"}");
			return true;
		}

		public int proofEdgesAndSetNewBox(int i, int j) {
			boolean isPossible = false;
//			System.out.println(" radnom Cell I: "+i+", J: "+j); 
			
			//oben, unten, rechts, links
			int direction = (int) (Math.floor(Math.random()*3.9999));
//			System.out.println(" radnom Direction: "+direction); 
			
			if (direction == 0 && i < 8) {
				if (cells[i][j].getBox() != cells[i + 1][j].getBox()) {
					int newBoxVal = cells[i + 1][j].getBox();		
					int oldBoxVal = cells[i][j].getBox();
					cells[i][j].setBox(cells[i + 1][j].getBox());
					if (isConnected() == false) {
//						System.out.println("not connected");
						cells[i][j].setBox(oldBoxVal);
					} else {
//						System.out.println("connected");
//						System.out.println("Cell["+i+"]["+j+"] mit "+oldBoxVal+" bekommt neue Box-Value: " + newBoxVal + " von Cell["+(i+1)+"]["+j+"]");
						return newBoxVal;
					}
				}
			}
			
			if (direction == 1 && i > 0) {
				if (cells[i][j].getBox() != cells[i - 1][j].getBox()) {
					int newBoxVal = cells[i - 1][j].getBox();
					int oldBoxVal = cells[i][j].getBox();
					cells[i][j].setBox(cells[i - 1][j].getBox());
					if (isConnected() == false) {
//						System.out.println("not connected");
						cells[i][j].setBox(oldBoxVal);
					} else {
//						System.out.println("connected");
//						System.out.println("Cell["+i+"]["+j+"] mit "+oldBoxVal+" bekommt neue Box-Value: " + newBoxVal + " von Cell["+(i-1)+"]["+j+"]");
						return newBoxVal;
					}
				}
			}
			
			if (direction == 2 && j < 8) {
				if (cells[i][j].getBox() != cells[i][j+1].getBox()) {
					int newBoxVal = cells[i][j+1].getBox();
					int oldBoxVal = cells[i][j].getBox();
					cells[i][j].setBox(cells[i][j+1].getBox());
					if (isConnected() == false) {
//						System.out.println("not connected");
						cells[i][j].setBox(oldBoxVal);
					} else {
//						System.out.println("connected");
//						System.out.println("Cell["+i+"]["+j+"] mit "+oldBoxVal+" bekommt neue Box-Value: " + newBoxVal + " von Cell["+i+"]["+(j+1)+"]");
						return newBoxVal;
					}
				}
			}
			
			if (direction == 3 && j > 0) {
				if (cells[i][j].getBox() != cells[i][j-1].getBox()) {
					int newBoxVal = cells[i][j-1].getBox();
					int oldBoxVal = cells[i][j].getBox();
					cells[i][j].setBox(cells[i][j-1].getBox());
					if (isConnected() == false) {
//						System.out.println("not connected");
						cells[i][j].setBox(oldBoxVal);
					} else {
//						System.out.println("connected");
//						System.out.println("Cell["+i+"]["+j+"] mit "+oldBoxVal+" bekommt neue Box-Value: " + newBoxVal + " von Cell["+i+"]["+(j-1)+"]");
						return newBoxVal;
					}
				}
			}
			return 0;
		}

		private boolean isConnected() {
					
			int[][] cellsmr = new int[9][9];
			for (int m = 0; m< cellsmr.length; m++) {
				for (int n = 0; n < cellsmr[m].length; n++) {
					cellsmr[m][n] = 0;
					
				}
			}
			
			int toNine = 0;
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {

					if (cellsmr[i][j] == 0) {
						toNine++;
						cellsmr[i][j] = 1;
						toNine = isInternalConnected(i, j, 1, cellsmr);
						if (toNine < 7) {
							return false;
						}
					}

				}
			}

			return true;
		}

		private int isInternalConnected(int i, int j, int toNine, int[][] cellsmr) {
			if (i < 8) {
				if (cellsmr[i+1][j] == 0) {
					if (cells[i][j].getBox() == cells[i + 1][j].getBox()) {
						cellsmr[i + 1][j] = 1;
						toNine = isInternalConnected(i + 1, j, toNine, cellsmr)+1;
					}
				}
			}
			if (i > 0) {
				if (cellsmr[i-1][j] == 0) {
					if (cells[i][j].getBox() == cells[i - 1][j].getBox()) {
						cellsmr[i - 1][j] = 1;
						toNine = isInternalConnected(i - 1, j, toNine, cellsmr)+1;
					}
				}
			}
			if (j < 8) {
				if (cellsmr[i][j+1] == 0) {
					if (cells[i][j].getBox() == cells[i][j + 1].getBox()) {
						cellsmr[i][j + 1] = 1;
						toNine = isInternalConnected( i,  j+1,  toNine, cellsmr)+1;
						}
				}
			}
			if (j > 0) {
				if (cellsmr[i][j-1] == 0) {
					if (cells[i][j].getBox() == cells[i][j - 1].getBox()) {
						cellsmr[i][j - 1] = 1;
						toNine = isInternalConnected( i,  j-1,  toNine, cellsmr)+1;
					}
				}
			}
			return toNine;
		}
		
	
	public void printFelder() {
//		for (int i = 0; i < 81; i++) {
//			if (i %9 == 0) {
//				System.out.println("  ");
//			}
//			int val = this.cells[i].getBox();
//				System.out.print(val+" ");
//		}
	}

	@Override
	public void difficulty() {
		int diff = this.difficulty;
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


	
}