package logic;

/**
 * This class defines the different gamestates a game can be in
 * 
 * @author gruber, bakaschev, fuchsluger
 *
 */

public enum Gamestate {
	AUTOSOLVED, OPEN, DONE, INCORRECT, CONFLICT, UNSOLVABLE, CREATING, DRAWING, NOFORMS, NOTENOUGHNUMBERS,
	 MANUALCONFLICT;
}
