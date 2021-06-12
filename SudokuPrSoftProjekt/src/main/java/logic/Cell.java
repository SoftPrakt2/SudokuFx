package logic;

import com.google.gson.annotations.Expose;

public class Cell {

	// Spalte 1 - 9
	@Expose int col;

	// Reihe 1 - 9
	@Expose int row;

	// Box 1 - 9
	@Expose int box;

	// Wert 1 - 9
	@Expose int value;

	// Manuelle Eingabe
	@Expose boolean isReal;

	// Color color
	@Expose String boxcolor;

	 public Cell(int row, int col, int box, int value) {
	        super();
	        this.row = row;
	        this.col = col;
	        this.box = box;
	        this.value = value;
	        this.isReal = true;
	        this.boxcolor = "";
	    }

	    public boolean getFixedNumber() {
	        return this.isReal;
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
	       
	      
	        if(boxcolor.equals("97c1a9")) {
	        	this.setBox(1);
	        }
	        if(boxcolor.equals("cab08b")) {
	        	this.setBox(2);
	        }
	        if(boxcolor.equals("dfd8ab")) {
	        	this.setBox(3);
	        }
	        if(boxcolor.equals("d5a1a3")) {
	        	this.setBox(4);
	        }
	        if(boxcolor.equals("80adbc")) {
	        	this.setBox(5);
	        }
	        if(boxcolor.equals("adb5be")) {
	        	this.setBox(6);
	        }
	        if(boxcolor.equals("eaeee0")) {
	        	this.setBox(7);
	        }
	        if(boxcolor.equals("957DAD")) {
	        	this.setBox(8);
	        }
	        if(boxcolor.equals("FFDFD3")) {
	        	this.setBox(9);
	        }
	       
	       this.boxcolor = boxcolor;
	    }
	    

	    @Override
	    public String toString() {
	        return "Feld [col=" + col + ", row=" + row + ", box=" + box + ", value=" + value + "]";
	    }
	}