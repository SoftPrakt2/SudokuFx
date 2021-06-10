package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Cell;

class CellTest {
	
	Cell cell;
	
    @BeforeEach
    public void setUp() {
        cell = new Cell(0, 1, 3, 9);
    }

    //Getter
    @Test
    void testGetIsReal() {
        assertTrue(cell.getFixedNumber());
    }

    @Test
    void testGetRow() {
        assertEquals(cell.getRow(), 0);
    }

    @Test
    void testGetCol() {
        assertEquals(cell.getCol(), 1);
    }

    @Test
    void testGetBox() {
        assertEquals(cell.getBox(), 3);
    }

    @Test
    void testGetValue() {
        assertEquals(cell.getValue(), 9);
    }


    //Setter
    @Test
    void testSetIsReal() {
        cell.setFixedNumber(false);
        assertFalse(cell.getFixedNumber());
    }

    @Test
    void testSetRow() {
        cell.setRow(5);
        assertEquals(cell.getRow(), 5);
    }

    @Test
    void testSetCol() {
        cell.setCol(5);
        assertEquals(cell.getCol(), 5);
    }

    @Test
    void testSetBox() {
        cell.setBox(1);
        assertEquals(cell.getBox(), 1);
    }

    @Test
    void testSetValue() {
        cell.setValue(5);
        assertEquals(cell.getValue(), 5);
    }

    @Test
    void testToString() {
        String output = "Feld [col=" + cell.getCol() + ", row=" + cell.getRow() + ", box=" + cell.getBox()
                + ", value=" + cell.getValue() + "]";
        assertEquals(cell.toString(), output);
    }
}
