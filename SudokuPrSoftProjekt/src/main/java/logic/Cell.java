package logic;

/**
 * This class describes the structure of a Cell object, these objects are used in the {@link logic.BasicGameLogic} classes to create a sudoku model playing field
 * @author grube
 *
 */
public class Cell {

	// column 1 - 9
	int col;

	// row 1 - 9
	int row;

	// box 1 - 9
	int box;

	// value 1 - 9
	int value;

	// fixed Number that can not be changed by a user
	boolean isReal;

	// Cell color
	String boxcolor;
	
	//declares if the number is a system provided hint
	boolean isHint;

	public Cell(int row, int col, int box, int value) {
		super();
		this.row = row;
		this.col = col;
		this.box = box;
		this.value = value;
		this.isReal = true;
		this.boxcolor = "";
		isHint = false;
	}
	
	/**
	 * getter and setter
	 */
	public boolean getFixedNumber() {
		return this.isReal;
	}
	
	public void setIsHint(boolean isHint) {
		this.isHint = isHint;
	}
	
	public boolean isHint() {
		return isHint;
	}

	public void setFixedNumber(boolean newState) {
		this.isReal = newState;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getBox() {
		return box;
	}

	public void setBox(int box) {
		this.box = box;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getBoxcolor() {
		return boxcolor;
	}

	public void setBoxcolor(String boxcolor) {

		if (boxcolor.equals("97c1a9")) {
			this.setBox(1);
		}
		if (boxcolor.equals("cab08b")) {
			this.setBox(2);
		}
		if (boxcolor.equals("dfd8ab")) {
			this.setBox(3);
		}
		if (boxcolor.equals("d5a1a3")) {
			this.setBox(4);
		}
		if (boxcolor.equals("80adbc")) {
			this.setBox(5);
		}
		if (boxcolor.equals("adb5be")) {
			this.setBox(6);
		}
		if (boxcolor.equals("eaeee0")) {
			this.setBox(7);
		}
		if (boxcolor.equals("957DAD")) {
			this.setBox(8);
		}
		if (boxcolor.equals("FFDFD3")) {
			this.setBox(9);
		}

		this.boxcolor = boxcolor;
	}

	@Override
	public String toString() {
		return "Feld [col=" + col + ", row=" + row + ", box=" + box + ", value=" + value + "]";
	}
}