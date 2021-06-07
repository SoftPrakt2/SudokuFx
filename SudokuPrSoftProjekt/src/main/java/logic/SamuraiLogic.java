package logic;

public class SamuraiLogic extends BasicGameLogic {

	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		cells = new Cell[21][21];
		gametype = "Samurai";
		hintCounter = 10;
	}

	@Override
	public boolean checkRow(int row, int col, int guess) {
		// ---------------------topLeft---------------------
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (col = 6; col < 15; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkCol(int row, int col, int guess) {
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (row = 6; row < 15; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkBox(int row, int col, int guess) {		
		if(this.cells[row][col].getValue() != -1) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}
		return true;
	}
	

	@Override
	public boolean valid(int row, int col, int guess) {
		if (this.checkRow(row, col, guess) && this.checkCol(row, col, guess) && this.checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	@Override
	public void setUpLogicArray() {
		int box = 1;
		Cell cell;
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (row < 6 && col > 8 && col < 12) {
					cell = new Cell(row, col, box, -1);
					cells[row][col] = cell;
				} else if (row > 8 && row < 12 && col < 6) {
					cell = new Cell(row, col, box, -1);
					cells[row][col] = cell;
				} else if (row > 8 && row < 12 && col > 14) {
					cell = new Cell(row, col, box, -1);
					cells[row][col] = cell;
				} else if (row > 14 && col > 8 && col < 12) {
					cell = new Cell(row, col, box, -1);
					cells[row][col] = cell;
				} else {
					cell = new Cell(row, col, box, 0);
					cells[row][col] = cell;
				}
				box++;
			}
		}
	}

	@Override
	public int[] hint() {
		// TODO Auto-generated method stub
		boolean correctRandom = false;
        int[] coordinates = new int[2];
        int counter = 0;

        int help[][] = new int[21][21];
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[row].length; col++) {
                help[row][col] = this.cells[row][col].getValue();
            }
        }

        this.solveSudoku();

        while (!correctRandom) {
            int randomCol = (int) (Math.floor(Math.random() * 20.9999));
            int randomRow = (int) (Math.floor(Math.random() * 20.9999));
            int randomNumber = (int) (Math.random() * 9) + 1;
            if (this.cells[randomRow][randomCol].getValue() == randomNumber && help[randomRow][randomCol] == 0
                    && this.cells[randomRow][randomCol].getValue() != -1) {
                help[randomRow][randomCol] = randomNumber;
                coordinates[0] = randomRow;
                coordinates[1] = randomCol;
                correctRandom = true;
            }
            counter++;
            if (counter == 10000) {
                coordinates = null;
                break;
            }
        }

        for (int row = 0; row < 21; row++) {
            for (int col = 0; col < 21; col++) {
                this.cells[row][col].setValue(help[row][col]);
            }
        }
        return coordinates;
	}

	@Override
	public void difficulty() {
		int counter = 369;
        if (this.difficulty == 3)
            counter = 230;
        if (this.difficulty == 5)
            counter = 200;
        if (this.difficulty == 7)
            counter = 180;

        if(counter == 369) {
            for (int row = 0; row < this.cells.length; row++) {
                for (int col = 0; col < this.cells[row].length; col++) {
                    if(this.cells[row][col].getValue() != -1) {
                        this.cells[row][col].setIsReal(false);
                        this.cells[row][col].setValue(0);
                    }
                }
            }
            counter = 0;
        }

        while (counter != 0) {
            int randomCol = (int) (Math.floor(Math.random() * 20.9999));
            int randomRow = (int) (Math.floor(Math.random() * 20.9999));
            if (this.cells[randomRow][randomCol].getValue() != 0 && this.cells[randomRow][randomCol].getIsReal()
                    && this.cells[randomRow][randomCol].getValue() != -1) {
                this.cells[randomRow][randomCol].setValue(0);
                this.cells[randomRow][randomCol].setIsReal(false);
                counter--;
            }
        }
	}

	@Override
	public void printCells() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == -1) {
					System.out.print("- ");
				} else {
					System.out.print(this.cells[row][col].getValue() + " ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public Cell[][] getCells() {
		return this.cells;
	}

	@Override
	public void setCell(int row, int col, int guess) {
		this.cells[row][col].setValue(guess);
		this.cells[row][col].setIsReal(true);
	}

	@Override
	public boolean testIfSolved() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isConnected() {
		return false;
	}
}
