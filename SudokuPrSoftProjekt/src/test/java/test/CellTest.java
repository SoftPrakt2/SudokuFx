package test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Cell;

class CellTest {
	
	Cell cell;
	
    @BeforeEach
    public void setUp() {
        cell = new Cell(0, 1, 3, 9);
    }

	/**
	 * getters and setters
	 */
    @Test
    void testGetIsReal() {
        assertTrue(cell.getFixedNumber());
    }

    @Test
    void testGetRow() {
        assertEquals(0,cell.getRow());
    }

    @Test
    void testGetCol() {
        assertEquals(1,cell.getCol());
    }

    @Test
    void testGetBox() {
        assertEquals(3,cell.getBox());
    }

    @Test
    void testGetValue() {
        assertEquals(9,cell.getValue());
    }


    @Test
    void testSetIsReal() {
        cell.setFixedNumber(false);
        assertFalse(cell.getFixedNumber());
    }

    @Test
    void testSetRow() {
        cell.setRow(5);
        assertEquals(5,cell.getRow());
    }

    @Test
    void testSetCol() {
        cell.setCol(5);
        assertEquals(5,cell.getCol());
    }

    @Test
    void testSetBox() {
        cell.setBox(1);
        assertEquals(1,cell.getBox());
    }

    @Test
    void testSetValue() {
        cell.setValue(5);
        assertEquals(5,cell.getValue());
    }

	/**
	 * toString
	 */
    @Test
    void testToString() {
        String output = "Feld [col=" + cell.getCol() + ", row=" + cell.getRow() + ", box=" + cell.getBox()
                + ", value=" + cell.getValue() + "]";
        assertEquals(output, cell.toString());
    }
}
