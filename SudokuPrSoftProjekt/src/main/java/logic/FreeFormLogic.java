package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.SudokuField;

public class FreeFormLogic extends SudokuLogic {

	static int counter = 0;
	public FreeFormLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gametype = "FreeForm";
		hintCounter = 3;
	}
	

	/**
	 * Überprüft in der Box der übergebenen Reihe und Zeile eine idente Zahl
	 * vorhanden ist.
	 */
	@Override
	public boolean checkBox(int row, int col, int guess) {
		for (int i = 0; i < this.cells.length; i++) {
			for (int j = 0; j < this.cells[i].length; j++) {
				if (cells[i][j].getBox() == cells[row][col].getBox()) {
					if (this.cells[i][j].getValue() == guess) {
						return false;
					}
				}
			}
		}
		return true;
	}

	

	/**
	 * Autogenerator für ein neues Sudoku Befüllt rekursiv das im Hintergrund
	 * liegene Sudoku-Array.
	 */
	static int globalCounter = 0;
	
	@Override
	public boolean createSudoku() {
		if(shuffleCounter == 0) {
			shuffle();
			shuffleCounter++;
		}
		int counter = 0;
		deleteNumbers();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				boolean correctNumber = false;
				while (!correctNumber) {
					counter++;
					globalCounter++;
					int randomNumber = (int) (Math.random() * 9) + 1;
					if (this.cells[i][j].getValue() == 0 && this.valid(i, j, randomNumber)) {
						this.cells[i][j].setValue(randomNumber);
						correctNumber = true;
					}

					if (counter == 20000) {
						counter = 0;
						deleteNumbers();
						i = 0;
						j = 0;
						System.out.println("yeet");
					}

					if (globalCounter > 1000000) {
						globalCounter = 0;
						this.cells = loadPreMadeFreeForm();
						System.out.println("preloadgamemydudeyeeet");
						return true;
					}

				}
			}
		}

		if (!solveSudoku()) {
			createSudoku();
		}

		printCells();
		return true;
	}

	public Cell[][] loadPreMadeFreeForm() {

		SaveModel data = new SaveModel();
		Gson gson = new GsonBuilder().create();

		File[] freeFormDirectory = new File("FreeFormGames").listFiles();

		int rand = (int) (Math.floor(Math.random() * 13.9999));

		try {
			BufferedReader br = new BufferedReader(new FileReader(freeFormDirectory[rand].getAbsoluteFile()));
			data = gson.fromJson(br, SaveModel.class);
			System.out.println("warum du hängen");
			this.setCells(data.getGameArray());

			try {
				br.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.printCells();
		return data.getGameArray();
	}

	public void deleteNumbers() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				this.cells[row][col].setValue(0);
			}
		}
	}

	// shuffle(): eine Zelle zufällig einer neuen Box zuweisen
	public void shuffle() {
		int localcount = 0;

		// erste 21 Durchläfufe
		for (int z = 0; z < 100; z++) {
			Cell[][] rollbackCells = new Cell[9][9];
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
					rollbackCells[i][j] = new Cell(0, 0, 0, 0);
					rollbackCells[i][j].setBox(cells[i][j].getBox());
				}
			}
			int rand1 = (int) (Math.floor(Math.random() * 8.9999));
			int rand2 = (int) (Math.floor(Math.random() * 8.9999));
			proofEdgesAndSetNewBox(rand1, rand2);
			localcount++;
			if (proofNrCells() == false) {
				for (int i = 0; i < this.cells.length; i++) {
					for (int j = 0; j < this.cells[i].length; j++) {
						cells[i][j].setBox(rollbackCells[i][j].getBox());
					}
				}
			}
		}

		// Durchläufe bis Array mit je 9 gefüllt ist
		while (nineNrCells() == false) {
			Cell[][] rollbackCells = new Cell[9][9];
			for (int i = 0; i < this.cells.length; i++) {
				for (int j = 0; j < this.cells[i].length; j++) {
					rollbackCells[i][j] = new Cell(0, 0, 0, 0);
					rollbackCells[i][j].setBox(cells[i][j].getBox());
				}
			}
			int rand1 = (int) (Math.floor(Math.random() * 8.9999));
			int rand2 = (int) (Math.floor(Math.random() * 8.9999));
			proofEdgesAndSetNewBox(rand1, rand2);
			localcount++;
			if (proofNrCells() == false) {
				for (int i = 0; i < this.cells.length; i++) {
					for (int j = 0; j < this.cells[i].length; j++) {
						cells[i][j].setBox(rollbackCells[i][j].getBox());
					}
				}
			}
		}

		// Farben vergeben
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j].getBox() == 1) {
					cells[i][j].setBoxcolor("97c1a9");
				} else if (cells[i][j].getBox() == 2) {
					cells[i][j].setBoxcolor("cab08b");

				} else if (cells[i][j].getBox() == 3) {
					cells[i][j].setBoxcolor("dfd8ab");

				} else if (cells[i][j].getBox() == 4) {
					cells[i][j].setBoxcolor("d5a1a3");

				} else if (cells[i][j].getBox() == 5) {
					cells[i][j].setBoxcolor("80adbc");

				} else if (cells[i][j].getBox() == 6) {
					cells[i][j].setBoxcolor("adb5be");

				} else if (cells[i][j].getBox() == 7) {
					cells[i][j].setBoxcolor("eaeee0");

				} else if (cells[i][j].getBox() == 8) {
					cells[i][j].setBoxcolor("957DAD");

				} else if (cells[i][j].getBox() == 9) {
					cells[i][j].setBoxcolor("FFDFD3");
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
			if (cells1to9[nk] > 10 || cells1to9[nk] < 8) {
				return false;
			}
		}
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
			if (cells1to9[nk] != 9) {
				return false;
			}
		}
		return true;
	}

	public int proofEdgesAndSetNewBox(int i, int j) {

		// oben, unten, rechts, links
		int direction = (int) (Math.floor(Math.random() * 3.9999));

		if (direction == 0 && i < 8) {
			if (cells[i][j].getBox() != cells[i + 1][j].getBox()) {
				int newBoxVal = cells[i + 1][j].getBox();
				int oldBoxVal = cells[i][j].getBox();
				cells[i][j].setBox(cells[i + 1][j].getBox());
				if (isConnected() == false) {
					cells[i][j].setBox(oldBoxVal);
				} else {
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
					cells[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
			}
		}

		if (direction == 2 && j < 8) {
			if (cells[i][j].getBox() != cells[i][j + 1].getBox()) {
				int newBoxVal = cells[i][j + 1].getBox();
				int oldBoxVal = cells[i][j].getBox();
				cells[i][j].setBox(cells[i][j + 1].getBox());
				if (isConnected() == false) {
					cells[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
			}
		}

		if (direction == 3 && j > 0) {
			if (cells[i][j].getBox() != cells[i][j - 1].getBox()) {
				int newBoxVal = cells[i][j - 1].getBox();
				int oldBoxVal = cells[i][j].getBox();
				cells[i][j].setBox(cells[i][j - 1].getBox());
				if (isConnected() == false) {
					cells[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isConnected() {

		int[][] cellsmr = new int[9][9];
		for (int m = 0; m < cellsmr.length; m++) {
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
			if (cellsmr[i + 1][j] == 0) {
				if (cells[i][j].getBox() == cells[i + 1][j].getBox()) {
					cellsmr[i + 1][j] = 1;
					toNine = isInternalConnected(i + 1, j, toNine, cellsmr) + 1;
				}
			}
		}
		if (i > 0) {
			if (cellsmr[i - 1][j] == 0) {
				if (cells[i][j].getBox() == cells[i - 1][j].getBox()) {
					cellsmr[i - 1][j] = 1;
					toNine = isInternalConnected(i - 1, j, toNine, cellsmr) + 1;
				}
			}
		}
		if (j < 8) {
			if (cellsmr[i][j + 1] == 0) {
				if (cells[i][j].getBox() == cells[i][j + 1].getBox()) {
					cellsmr[i][j + 1] = 1;
					toNine = isInternalConnected(i, j + 1, toNine, cellsmr) + 1;
				}
			}
		}
		if (j > 0) {
			if (cellsmr[i][j - 1] == 0) {
				if (cells[i][j].getBox() == cells[i][j - 1].getBox()) {
					cellsmr[i][j - 1] = 1;
					toNine = isInternalConnected(i, j - 1, toNine, cellsmr) + 1;
				}
			}
		}
		return toNine;
	}
}