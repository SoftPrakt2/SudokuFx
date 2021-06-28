package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * extends BasicGameLogic
 * implements all abstract methods of BasicGameLogic
 *
 */
public class FreeFormLogic extends SudokuLogic {

	static int counter = 0;

	/**
	 *  constructor for creating a FreeFormLogic-Object
	 * @param gamestate
	 * @param minutesPlayed
	 * @param secondsPlayed
	 * @param isCorrect
	 */
	public FreeFormLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super(gamestate, minutesPlayed, secondsPlayed);
		setCells(new Cell[9][9]);
		setGametype("FreeForm");
		setNumbersToBeSolvable(17);
		this.setSavedResults(new int[this.getCells().length][this.getCells().length]);
	}
	

	/**
	 * Checks if the number already exists in the box depending on shape of the box
	 */
	@Override
	public boolean checkBox(int row, int col, int guess) {
		for (int i = 0; i < this.getCells().length; i++) {
			for (int j = 0; j < this.getCells()[i].length; j++) {
				if (getCells()[i][j].getBox() == getCells()[row][col].getBox() && this.getCells()[i][j].getValue() == guess) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 *counts runs of loops in createSudoku() 
	 *indicates when a methods must break loop
	 *to prevent deadlocks
	 */
	private int globalCounter = 0;
	
	/**
	 *autogenerates new sudoku 
	 *fills in numbers recursive
	 */
	@Override
	public boolean createSudoku() {
		if (shuffleCounter == 0) {
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
					int randomNumber = r.nextInt(9) + 1;
					if (this.getCells()[i][j].getValue() == 0 && this.valid(i, j, randomNumber)) {
						this.getCells()[i][j].setValue(randomNumber);
						correctNumber = true;
					}
					if (counter == 20000) {
						counter = 0;
						deleteNumbers();
						i = 0;
						j = 0;
					}

					if (globalCounter > 2000000) {
						globalCounter = 0;
						setCells(loadPreMadeFreeForm());
						System.out.println("loaded game");
						connectToSavedResults();
						return true;
					}
				}
			}
		}
		if (!solveSudoku()) {
			createSudoku();
		}
		globalCounter = 0;
		connectToSavedResults();
		return true;
	}
	
	/**
	 * loads freeform from the directory (when autogenerated freefrom sudoku can not be generated in time)
	 * @return freefrom as Cell[][] value
	 */
	public Cell[][] loadPreMadeFreeForm() {
		SaveModel data = new SaveModel();
		Gson gson = new GsonBuilder().create();

		File[] freeFormDirectory = new File("FreeFormGames").listFiles();

		int rand = r.nextInt(13) + 1;

		try {
			BufferedReader br = new BufferedReader(new FileReader(freeFormDirectory[rand].getAbsoluteFile()));
			data = gson.fromJson(br, SaveModel.class);
			this.setCells(data.getGameArray());
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data.getGameArray();
	}
	
	
	/**
	 * resets all values to 0
	 */
	public void deleteNumbers() {
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				this.getCells()[row][col].setValue(0);
			}
		}
	}

	/**
	 * generating random shapes by allocating random box-values to cells
	 */
	public void shuffle() {

		/**
		 * first mandatory number of loops 
		 * to gernerate random shapes 
		 * to prevent quick freefrom solutions that deviate hardly from a  normal sudoku shape  
		 */
		for (int z = 0; z < 100; z++) {
			Cell[][] rollbackCells = new Cell[9][9];
			for (int i = 0; i < this.getCells().length; i++) {
				for (int j = 0; j < this.getCells()[i].length; j++) {
					rollbackCells[i][j] = new Cell(0, 0, 0, 0);
					rollbackCells[i][j].setBox(getCells()[i][j].getBox());
				}
			}
			int rand1 = r.nextInt(9);
			int rand2 = r.nextInt(9);
			proofEdgesAndSetNewBox(rand1, rand2);
			if (!proofNrCells()) {
				for (int i = 0; i < this.getCells().length; i++) {
					for (int j = 0; j < this.getCells()[i].length; j++) {
						getCells()[i][j].setBox(rollbackCells[i][j].getBox());
					}
				}
			}
		}

		/**
		 * following loops 
		 * to gernerate random shapes
		 * untli every box/shape gets exaclty 9 cells
		 */
		while (!nineNrCells()) {
			Cell[][] rollbackCells = new Cell[9][9];
			for (int i = 0; i < this.getCells().length; i++) {
				for (int j = 0; j < this.getCells()[i].length; j++) {
					rollbackCells[i][j] = new Cell(0, 0, 0, 0);
					rollbackCells[i][j].setBox(getCells()[i][j].getBox());
				}
			}
			int rand1 = r.nextInt(9);
			int rand2 = r.nextInt(9);
			proofEdgesAndSetNewBox(rand1, rand2);
			if (!proofNrCells()) {
				for (int i = 0; i < this.getCells().length; i++) {
					for (int j = 0; j < this.getCells()[i].length; j++) {
						getCells()[i][j].setBox(rollbackCells[i][j].getBox());
					}
				}
			}
		}

		/**
		 * boxes get color values
		 */
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getCells()[i][j].getBox() == 1) {
					getCells()[i][j].setBoxcolor("97c1a9");
				} else if (getCells()[i][j].getBox() == 2) {
					getCells()[i][j].setBoxcolor("cab08b");

				} else if (getCells()[i][j].getBox() == 3) {
					getCells()[i][j].setBoxcolor("dfd8ab");

				} else if (getCells()[i][j].getBox() == 4) {
					getCells()[i][j].setBoxcolor("d5a1a3");

				} else if (getCells()[i][j].getBox() == 5) {
					getCells()[i][j].setBoxcolor("80adbc");

				} else if (getCells()[i][j].getBox() == 6) {
					getCells()[i][j].setBoxcolor("adb5be");

				} else if (getCells()[i][j].getBox() == 7) {
					getCells()[i][j].setBoxcolor("eaeee0");

				} else if (getCells()[i][j].getBox() == 8) {
					getCells()[i][j].setBoxcolor("957DAD");

				} else if (getCells()[i][j].getBox() == 9) {
					getCells()[i][j].setBoxcolor("FFDFD3");
				}
			}

		}
	}
	
	/**
	 * @return true/false 
	 * if one shape/box contains more than 10 or less than 8 cells than false
	 * to prevent very high and very low cells incidences from one box that can lead to a deadlock
	 */
	public boolean proofNrCells() {
		int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int box;
		for (int i = 0; i < this.getCells().length; i++) {
			for (int j = 0; j < this.getCells()[i].length; j++) {
				box = getCells()[i][j].getBox();
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
	
	/**
	 * @return  true/false 
	 * 	 if every shape/box contains exactly 9 cells than true
	 * to proof playable sudoku form
	 */
	public boolean nineNrCells() {
		int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int box;
		for (int i = 0; i < this.getCells().length; i++) {
			for (int j = 0; j < this.getCells()[i].length; j++) {
				box = getCells()[i][j].getBox();
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

	/**
	 * @param i is coordinate x of cell array
	 * @param j is coordinate y of cell array
	 * concerned cell[i][j] gets new box value form one cell from one of the 4 edges every cell has
	 * proofs if edge is not at the rim of the sudoku field 
	 * @return new box-value that the concerned cell receives 
	 */
	public int proofEdgesAndSetNewBox(int i, int j) {

		int direction = r.nextInt(4);

		if (direction == 0 && i < 8 && getCells()[i][j].getBox() != getCells()[i + 1][j].getBox()) {
				int newBoxVal = getCells()[i + 1][j].getBox();
				int oldBoxVal = getCells()[i][j].getBox();
				getCells()[i][j].setBox(getCells()[i + 1][j].getBox());
				if (!isConnected()) {
					getCells()[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
		}

		if (direction == 1 && i > 0 && getCells()[i][j].getBox() != getCells()[i - 1][j].getBox()) {
				int newBoxVal = getCells()[i - 1][j].getBox();
				int oldBoxVal = getCells()[i][j].getBox();
				getCells()[i][j].setBox(getCells()[i - 1][j].getBox());
				if (!isConnected()) {
					getCells()[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
		}

		if (direction == 2 && j < 8 && getCells()[i][j].getBox() != getCells()[i][j + 1].getBox()) {
				int newBoxVal = getCells()[i][j + 1].getBox();
				int oldBoxVal = getCells()[i][j].getBox();
				getCells()[i][j].setBox(getCells()[i][j + 1].getBox());
				if (!isConnected()) {
					getCells()[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
		}

		if (direction == 3 && j > 0 && getCells()[i][j].getBox() != getCells()[i][j - 1].getBox()) {
				int newBoxVal = getCells()[i][j - 1].getBox();
				int oldBoxVal = getCells()[i][j].getBox();
				getCells()[i][j].setBox(getCells()[i][j - 1].getBox());
				if (!isConnected()) {
					getCells()[i][j].setBox(oldBoxVal);
				} else {
					return newBoxVal;
				}
		}
		return 0;
	}

	/**
	 * proofs if all cells of all shapes/boxes are connected and no fragments ('cell islands') occur
	 * @return true is connected / false - is not connected
	 */
	@Override
	public boolean isConnected() {
		int[][] cellsmr = new int[9][9];
		for (int m = 0; m < cellsmr.length; m++) {
			for (int n = 0; n < cellsmr[m].length; n++) {
				cellsmr[m][n] = 0;

			}
		}

		int toNine = 0;
		for (int i = 0; i < this.getCells().length; i++) {
			for (int j = 0; j < this.getCells()[i].length; j++) {

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
	
	/**
	 * recursive method where every cell checks if it is or it is not connected (by same box-value) with neighboring cells
	 * @param i is coordinate x of cell array
	 * @param j is coordinate y of cell array
	  * @param cellsmr is cell-array
	  * @param toNine counts all connected cells of one box
	 * @return number of connected cells (ideally 8(base+8=9))
	 */
	private int isInternalConnected(int i, int j, int toNine, int[][] cellsmr) {
		if (i < 8 && cellsmr[i + 1][j] == 0 && getCells()[i][j].getBox() == getCells()[i + 1][j].getBox()) {
			cellsmr[i + 1][j] = 1;
			toNine = isInternalConnected(i + 1, j, toNine, cellsmr) + 1;
		}
		if (i > 0 && cellsmr[i - 1][j] == 0 && getCells()[i][j].getBox() == getCells()[i - 1][j].getBox()) {
			cellsmr[i - 1][j] = 1;
			toNine = isInternalConnected(i - 1, j, toNine, cellsmr) + 1;
		}
		if (j < 8 && cellsmr[i][j + 1] == 0 && getCells()[i][j].getBox() == getCells()[i][j + 1].getBox()) {
			cellsmr[i][j + 1] = 1;
			toNine = isInternalConnected(i, j + 1, toNine, cellsmr) + 1;
		}
		if (j > 0 && cellsmr[i][j - 1] == 0 && getCells()[i][j].getBox() == getCells()[i][j - 1].getBox()) {
			cellsmr[i][j - 1] = 1;
			toNine = isInternalConnected(i, j - 1, toNine, cellsmr) + 1;
		}
		return toNine;
	}
	
	@Override
	public boolean proofFilledOut() {
		int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		String boxcolor;
		for (int d = 0; d < this.getCells().length; d++) {
			for (int h= 0; h < this.getCells()[d].length; h++) {
				boxcolor = this.getCells()[d][h].getBoxcolor();
								
		        if(boxcolor.equals("97c1a9")) {	cells1to9[0] += 1;    }
		        if(boxcolor.equals("cab08b")) {	cells1to9[1] += 1;    }
		        if(boxcolor.equals("dfd8ab")) {  	cells1to9[2] += 1;     }
		        if(boxcolor.equals("d5a1a3")) {  	cells1to9[3] += 1;     }
		        if(boxcolor.equals("80adbc")) {        	cells1to9[4] += 1;	        }
		        if(boxcolor.equals("adb5be")) {	cells1to9[5] += 1;   }
		        if(boxcolor.equals("eaeee0")) {    	cells1to9[6] += 1;   }
		        if(boxcolor.equals("957DAD")) {	cells1to9[7] += 1;   }
		        if(boxcolor.equals("FFDFD3")) {	cells1to9[8] += 1;   }
			}
		}
		for (int nk = 0; nk < 9; nk++) {
			if (cells1to9[nk] != 9) {
				return false;
			}
		}
		return true;
	}
}