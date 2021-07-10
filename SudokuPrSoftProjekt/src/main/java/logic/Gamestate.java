package logic;

/**
 * This class defines the different game states a game can be in during a sudoku game.
 * 
 * @author bakaschev,
 *
 */

public enum Gamestate {
	AUTOSOLVED, OPEN, DONE, INCORRECT, CONFLICT, UNSOLVABLE, CREATING, DRAWING, NOFORMS, NOTENOUGHNUMBERS,
	 MANUALCONFLICT;
}

