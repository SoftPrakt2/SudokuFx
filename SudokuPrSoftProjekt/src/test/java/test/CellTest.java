package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Cell;

public class CellTest {
	
	Cell cell;
	
    @BeforeEach
    public void setUp() {
        cell = new Cell(0, 1, 3, 9);
    }

    //Getter
    @Test
    public void testGetIsReal() {
        assertTrue(cell.getIsReal());
    }

    @Test
    public void testGetRow() {
        assertEquals(cell.getRow(), 0);
    }

    @Test
    public void testGetCol() {
        assertEquals(cell.getCol(), 1);
    }

    @Test
    public void testGetBox() {
        assertEquals(cell.getBox(), 3);
    }

    @Test
    public void testGetValue() {
        assertEquals(cell.getValue(), 9);
    }


    //Setter
    @Test
    public void testSetIsReal() {
        cell.setIsReal(false);
        assertFalse(cell.getIsReal());
    }

    @Test
    public void testSetRow() {
        cell.setRow(5);
        assertEquals(cell.getRow(), 5);
    }

    @Test
    public void testSetCol() {
        cell.setCol(5);
        assertEquals(cell.getCol(), 5);
    }

    @Test
    public void testSetBox() {
        cell.setBox(1);
        assertEquals(cell.getBox(), 1);
    }

    @Test
    public void testSetValue() {
        cell.setValue(5);
        assertEquals(cell.getValue(), 5);
    }

    @Test
    public void testToString() {
        String output = "Feld [col=" + cell.getCol() + ", row=" + cell.getRow() + ", box=" + cell.getBox()
                + ", value=" + cell.getValue() + "]";
        assertEquals(cell.toString(), output);
    }
}
