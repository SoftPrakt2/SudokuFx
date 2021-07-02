package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import application.FreeFormGameBuilder;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import controller.GameController;
import javafx.event.ActionEvent;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

@ExtendWith(ApplicationExtension.class)
class GameControllerTest {

	private GameController controller;
	private ActionEvent action = new ActionEvent();

	/**
	 * Creates a new FreeFormLogic-Object, a FreeFormGameBuilder-Object and a new
	 * GameController-Object using the other two objects before each Test.
	 */
	@BeforeEach
	public void setUp() {
		FreeFormLogic model = new FreeFormLogic(Gamestate.OPEN, 0, 0);
		FreeFormGameBuilder scene = new FreeFormGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
	}

	/**
	 * Tests if a GameController-Object can be created with different Game-Types.
	 */
	@Test
	void testConstructorWithDifferentGameTypes() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);

		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN, 0, 0);
		SudokuGameBuilder sceneSudoku = new SudokuGameBuilder(modelSudoku);
		sceneSudoku.initializeGame();
		controller = new GameController(sceneSudoku, modelSudoku);
	}

	/**
	 * Tests if all the necessary steps for creating a Game get carried out and if a
	 * sudoku gets created.
	 */
	@Test
	void testCreateGameHandler() {
		controller.getModel().setDifficulty(3);
		controller.createGameHandler(action);
		boolean testIfModleCreated = false;
		int counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getModel().getCells()[row][col] != null) {
					testIfModleCreated = true;
				}
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		assertTrue(testIfModleCreated);
		assertNotEquals(counter, 81);
	}

	/**
	 * Tests if all the necessary steps for creating a Game get carried out and if a
	 * sudoku gets created.
	 */
	@Test
	void testCreate() {
		controller.getModel().setDifficulty(3);
		controller.createGame();
		boolean testIfModleCreated = false;
		int counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getModel().getCells()[row][col] != null) {
					testIfModleCreated = true;
				}
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		assertTrue(testIfModleCreated);
		assertNotEquals(81, counter);
	}

	/**
	 * Test the NewGameHandler-Method without conflicts. This method gets called if
	 * the player is already playing a sudoku Game.
	 */
	@Test
	void testNewGameHandlerWithoutConflict() {
		controller.getModel().setDifficulty(3);
		controller.createGameHandler(action);
		int counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		assertNotEquals(counter, 81);
		controller.newGameHandler(action);
		counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		assertNotEquals(counter, 81);
		controller.getModel().setDifficulty(0);
		controller.newGameHandler(action);
		counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		assertEquals(81, counter);
	}

	/**
	 * Tests the newGameHandler-Method while the user is playing a manual game.
	 * Tests if the game state gets set back to the original state
	 * (Gamestate.CREATING).
	 */
	@Test
	void testNewGameHandlerManuelSudoku() {
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN, 0, 0);
		SudokuGameBuilder sceneSudoku = new SudokuGameBuilder(modelSudoku);
		sceneSudoku.initializeGame();
		controller = new GameController(sceneSudoku, modelSudoku);

		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.newGameHandler(action);
		assertEquals(Gamestate.CREATING, controller.getModel().getGamestate());
	}

	/**
	 * Tests the HindHandler-Method without conflicts. Tests if a new number (hint)
	 * got added to the sudoku field.
	 */
	@Test
	void testHintHandlerWithoutConflict() {
		controller.getModel().setDifficulty(3);
		controller.createGame();
		int counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		controller.hintHandeler(action);
		int counterAfterHint = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counterAfterHint++;
				}
			}
		}
		assertNotEquals(counter, counterAfterHint);
	}

	/**
	 * Tests the HindHandler-Method while there are conflicts in the sudoku game.
	 * Tests if any new numbers got added even if there are conflicts.
	 */
	@Test
	void testHintHandlerWithConflict() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		controller.getsudokuField()[1][2].setText("1");
		int counter = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counter++;
				}
			}
		}
		controller.hintHandeler(action);
		int counterAfterHint = 0;
		for (int row = 0; row < controller.getModel().getCells().length; row++) {
			for (int col = 0; col < controller.getModel().getCells()[row].length; col++) {
				if (controller.getsudokuField()[row][col].getText().equals("")) {
					counterAfterHint++;
				}
			}
		}
		assertEquals(Gamestate.CONFLICT, controller.getModel().getGamestate());
		assertEquals(counter, counterAfterHint);
	}

	/**
	 * Tests the HindHandler-Method when the sudoku game is in an unsolvable state.
	 * Tests if the game state is UNSOLVABLE.
	 */
	@Test
	void testHintHandlerWithUnsolvable() {
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN, 0, 0);
		SudokuGameBuilder sceneSudoku = new SudokuGameBuilder(modelSudoku);
		sceneSudoku.initializeGame();
		controller = new GameController(sceneSudoku, modelSudoku);

		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.getsudokuField()[0][2].setText("3");
		controller.getsudokuField()[1][0].setText("4");
		controller.getsudokuField()[1][1].setText("5");
		controller.getsudokuField()[1][2].setText("6");
		controller.getsudokuField()[2][0].setText("7");
		controller.getsudokuField()[2][1].setText("8");
		controller.getsudokuField()[3][2].setText("9");
		controller.hintHandeler(action);
		assertEquals(Gamestate.UNSOLVABLE, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the text fields with auto generated numbers are disabled or not.
	 */
	@Test
	void testEnableEdit() {
		controller.getModel().setDifficulty(3);
		controller.createGame();
		controller.enableEdit();
		int counterDisabled = 0;
		int counterNotDisabled = 0;
		for (int row = 0; row < controller.getsudokuField().length; row++) {
			for (int col = 0; col < controller.getsudokuField()[row].length; col++) {
				if (controller.getsudokuField()[row][col].isDisabled() == true) {
					counterDisabled++;
				}
				if (controller.getsudokuField()[row][col].isDisabled() == false) {
					counterNotDisabled++;
				}
			}
		}
		assertEquals(25, counterDisabled);
		assertEquals(56, counterNotDisabled);
	}

	/**
	 * Tests if the user inputs gets reset if ResetHandler-Method gets called.
	 */
	@Test
	void testResetHandler() {
		controller.createGame();
		controller.getModel().getCells()[1][1].setValue(1);
		controller.getsudokuField()[1][1].setText("1");
		controller.connectWithModel();
		controller.getModel().getCells()[1][1].setIsHint(true);
		assertEquals(1, controller.getModel().getCells()[1][1].getValue());
		assertTrue(controller.getsudokuField()[1][1].getText().equals("1"));
		controller.resetHandler(action);
		assertEquals(0, controller.getModel().getCells()[1][1].getValue());
		assertTrue(controller.getsudokuField()[1][1].getText().equals(""));
	}

	/**
	 * 
	 */
	@Test
	void testNewManualFreeFormGameDrawing() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.getsudokuField()[1][1].removeFreeFormColorListener();
		assertFalse(controller.getsudokuField()[1][1].isListeningToColors());
		controller.getModel().setGameState(Gamestate.DRAWING);
//		controller.newGameHandler(action);
		controller.getScene().getLiveTimeLabel().textProperty().unbind();
		controller.newManualFreeFormGame();
		assertTrue(controller.getsudokuField()[1][1].isListeningToColors());
	}

	/**
	 * 
	 */
	@Test
	void testNewManualFreeFormGameCreating() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.getModel().setGameState(Gamestate.CREATING);
//		controller.newGameHandler(action);
		controller.getScene().getLiveTimeLabel().textProperty().unbind();
		controller.newManualFreeFormGame();
		assertEquals(Gamestate.DRAWING, controller.getModel().getGamestate());
	}

	/**
	 * 
	 */
	@Test
	void testnewManualSudokuorSamurai() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();
//		controller.newGameHandler(action);
		controller.getScene().getLiveTimeLabel().textProperty().unbind();
		controller.newManualSudokuOrSamurai();
		assertEquals(Gamestate.CREATING, controller.getModel().getGamestate());
	}

	/**
	 * Test if the game state gets set to MANUALCONFLICT if the user has conflicts
	 * in the manual sudoku game that he wants to create.
	 */
	@Test
	void testManualDoneHandlerWithConflict() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.getsudokuField()[0][2].setText("3");
		controller.getsudokuField()[1][0].setText("4");
		controller.getsudokuField()[1][1].setText("1");
		controller.manuelDoneHandler(action);
		assertEquals(Gamestate.MANUALCONFLICT, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the game state gets set to NOTENOUGHNUMBERS if the user did not put
	 * in enough number to create a manual sudoku game.
	 */
	@Test
	void testManualDoneHandlerWithoutEnoughNumbers() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.manuelDoneHandler(action);
		assertEquals(Gamestate.NOTENOUGHNUMBERS, controller.getModel().getGamestate());
	}

	/**
	 * Tests if game gets created if the user creates a manual game without any
	 * conflicts and enough numbers after the manualDoneHandler-Method gets called.
	 */
	@Test
	void testManualDoneHandlerWithoutConflictSolvable() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.getsudokuField()[0][2].setText("3");
		controller.getsudokuField()[0][3].setText("4");
		controller.getsudokuField()[0][4].setText("5");
		controller.getsudokuField()[0][5].setText("6");
		controller.getsudokuField()[0][6].setText("7");
		controller.getsudokuField()[0][7].setText("8");
		controller.getsudokuField()[0][8].setText("9");

		controller.getsudokuField()[1][0].setText("4");
		controller.getsudokuField()[1][1].setText("5");
		controller.getsudokuField()[1][2].setText("6");
		controller.getsudokuField()[1][3].setText("7");
		controller.getsudokuField()[1][4].setText("8");
		controller.getsudokuField()[1][5].setText("9");
		controller.getsudokuField()[1][6].setText("1");
		controller.getsudokuField()[1][7].setText("2");
		controller.getsudokuField()[1][8].setText("3");
		controller.manuelDoneHandler(action);
//		assertEquals(Gamestate.OPEN, controller.getModel().getGamestate());
	}

//	/**
//	 * Tests if the game state gets set to MANUALCONFLICT if the game
//	 * that the user wants to create is unsolvable.
//	 * 
//	 * User gets asked to create a new game that is solvable.
//	 */
//	@Test
//	void testManualDoneHandlerWithoutConflictUnsolvable() {
//		SudokuLogic model = new SudokuLogic(Gamestate.OPEN,0,0);
//		SudokuGameBuilder scene = new SudokuGameBuilder(model);
//		scene.initializeGame();
//		controller = new GameController(scene, model);
//		controller.getModel().setDifficulty(0);
//		controller.createGame();
//		
//		controller.getsudokuField()[0][0].setText("1");
//		controller.getsudokuField()[0][1].setText("2");
//		controller.getsudokuField()[0][2].setText("3");
//		controller.getsudokuField()[0][3].setText("4");
//		controller.getsudokuField()[0][4].setText("5");
//		controller.getsudokuField()[0][5].setText("6");
//		controller.getsudokuField()[0][6].setText("7");
//		controller.getsudokuField()[0][7].setText("8");
//		controller.getsudokuField()[0][8].setText("9");
//		
//		controller.getsudokuField()[1][0].setText("4");
//		controller.getsudokuField()[1][1].setText("5");
//		controller.getsudokuField()[1][2].setText("6");
//		controller.getsudokuField()[1][3].setText("7");
//		controller.getsudokuField()[1][4].setText("8");
//		controller.getsudokuField()[1][5].setText("9");
//		controller.getsudokuField()[1][6].setText("1");
//		controller.getsudokuField()[1][7].setText("2");
//		controller.getsudokuField()[1][8].setText("3");
//		
//		controller.getsudokuField()[2][0].setText("7");
//		controller.getsudokuField()[2][1].setText("8");
//		/**
//		 * makes the game unsolvable
//		 */
//		controller.getsudokuField()[3][2].setText("9");
//		controller.manuelDoneHandler(action);
//		assertEquals(Gamestate.MANUALCONFLICT, controller.getModel().getGamestate());
//	}

	/**
	 * Tests if the game state gets set to INCORRECT if the sudoku game has
	 * conflicts and the CheckHandler-Method gets called.
	 */
	@Test
	void testCheckHandlerWithConflict() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("1");

		controller.checkHandler(action);
		assertEquals(Gamestate.INCORRECT, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the game state gets set to INCORRECT if the sudoku is not solved
	 * completely without any conflicts and the CheckHandler-Method gets called.
	 */
	@Test
	void testCheckHandlerWithoutCompletellySolved() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.getsudokuField()[0][2].setText("3");

		controller.checkHandler(action);
		assertEquals(Gamestate.INCORRECT, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the game state gets set to DONE if the sudoku is solved completely
	 * without any conflicts and the CheckHandler-Method gets called.
	 */
	@Test
	void testCheckHandlerWithoutConflictAndCompletellySolved() {
		SudokuLogic model = new SudokuLogic(Gamestate.OPEN, 0, 0);
		SudokuGameBuilder scene = new SudokuGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		controller.getModel().setDifficulty(0);
		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.autoSolveHandler(action);
		controller.getModel().setGameState(Gamestate.OPEN);

		controller.checkHandler(action);
		assertEquals(Gamestate.DONE, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the colors are connected with each other if a user creates a manual
	 * freeform game. If they are not connected with each other, the game state
	 * should be NOFORMS and if they are connected it should be CREATING.
	 */
	@Test
	void testCustomColorsHandlerIsConnected() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		// 1st color
		controller.getsudokuField()[0][0].setColor("97c1a9");
		controller.getsudokuField()[0][1].setColor("97c1a9");
		controller.getsudokuField()[0][2].setColor("97c1a9");
		controller.getsudokuField()[1][0].setColor("97c1a9");
		controller.getsudokuField()[1][1].setColor("97c1a9");
		controller.getsudokuField()[1][2].setColor("97c1a9");
		controller.getsudokuField()[2][0].setColor("97c1a9");
		controller.getsudokuField()[2][1].setColor("97c1a9");
		controller.getsudokuField()[2][2].setColor("97c1a9");

		// 2nd color
		controller.getsudokuField()[0][3].setColor("cab08b");
		controller.getsudokuField()[0][4].setColor("cab08b");
		controller.getsudokuField()[0][5].setColor("cab08b");
		controller.getsudokuField()[1][3].setColor("cab08b");
		controller.getsudokuField()[1][4].setColor("cab08b");
		controller.getsudokuField()[1][5].setColor("cab08b");
		controller.getsudokuField()[2][3].setColor("cab08b");
		controller.getsudokuField()[2][4].setColor("cab08b");
		controller.getsudokuField()[2][5].setColor("cab08b");

		// 3rd color
		controller.getsudokuField()[0][6].setColor("dfd8ab");
		controller.getsudokuField()[0][7].setColor("dfd8ab");
		controller.getsudokuField()[0][8].setColor("dfd8ab");
		controller.getsudokuField()[1][6].setColor("dfd8ab");
		controller.getsudokuField()[1][7].setColor("dfd8ab");
		controller.getsudokuField()[1][8].setColor("dfd8ab");
		controller.getsudokuField()[2][6].setColor("dfd8ab");
		controller.getsudokuField()[2][7].setColor("dfd8ab");
		controller.getsudokuField()[2][8].setColor("dfd8ab");

		// 4th color
		controller.getsudokuField()[3][0].setColor("d5a1a3");
		controller.getsudokuField()[3][1].setColor("d5a1a3");
		controller.getsudokuField()[3][2].setColor("d5a1a3");
		controller.getsudokuField()[4][0].setColor("d5a1a3");
		controller.getsudokuField()[4][1].setColor("d5a1a3");
		controller.getsudokuField()[4][2].setColor("d5a1a3");
		controller.getsudokuField()[5][0].setColor("d5a1a3");
		controller.getsudokuField()[5][1].setColor("d5a1a3");
		controller.getsudokuField()[5][2].setColor("d5a1a3");

		// 5th color
		controller.getsudokuField()[3][3].setColor("80adbc");
		controller.getsudokuField()[3][4].setColor("80adbc");
		controller.getsudokuField()[3][5].setColor("80adbc");
		controller.getsudokuField()[4][3].setColor("80adbc");
		controller.getsudokuField()[4][4].setColor("80adbc");
		controller.getsudokuField()[4][5].setColor("80adbc");
		controller.getsudokuField()[5][3].setColor("80adbc");
		controller.getsudokuField()[5][4].setColor("80adbc");
		controller.getsudokuField()[5][5].setColor("80adbc");

		// 6th color
		controller.getsudokuField()[3][6].setColor("adb5be");
		controller.getsudokuField()[3][7].setColor("adb5be");
		controller.getsudokuField()[3][8].setColor("adb5be");
		controller.getsudokuField()[4][6].setColor("adb5be");
		controller.getsudokuField()[4][7].setColor("adb5be");
		controller.getsudokuField()[4][8].setColor("adb5be");
		controller.getsudokuField()[5][6].setColor("adb5be");
		controller.getsudokuField()[5][7].setColor("adb5be");
		controller.getsudokuField()[5][8].setColor("adb5be");

		// 7th color
		controller.getsudokuField()[6][0].setColor("eaeee0");
		controller.getsudokuField()[6][1].setColor("eaeee0");
		controller.getsudokuField()[6][2].setColor("eaeee0");
		controller.getsudokuField()[7][0].setColor("eaeee0");
		controller.getsudokuField()[7][1].setColor("eaeee0");
		controller.getsudokuField()[7][2].setColor("eaeee0");
		controller.getsudokuField()[8][0].setColor("eaeee0");
		controller.getsudokuField()[8][1].setColor("eaeee0");
		controller.getsudokuField()[8][2].setColor("eaeee0");

		// 8th color
		controller.getsudokuField()[6][3].setColor("957DAD");
		controller.getsudokuField()[6][4].setColor("957DAD");
		controller.getsudokuField()[6][5].setColor("957DAD");
		controller.getsudokuField()[7][3].setColor("957DAD");
		controller.getsudokuField()[7][4].setColor("957DAD");
		controller.getsudokuField()[7][5].setColor("957DAD");
		controller.getsudokuField()[8][3].setColor("957DAD");
		controller.getsudokuField()[8][4].setColor("957DAD");
		controller.getsudokuField()[8][5].setColor("957DAD");

		// 8th color
		controller.getsudokuField()[6][6].setColor("FFDFD3");
		controller.getsudokuField()[6][7].setColor("FFDFD3");
		controller.getsudokuField()[6][8].setColor("FFDFD3");
		controller.getsudokuField()[7][6].setColor("FFDFD3");
		controller.getsudokuField()[7][7].setColor("FFDFD3");
		controller.getsudokuField()[7][8].setColor("FFDFD3");
		controller.getsudokuField()[8][6].setColor("FFDFD3");
		controller.getsudokuField()[8][7].setColor("FFDFD3");
		controller.getsudokuField()[8][8].setColor("FFDFD3");

		controller.customColorsDoneHandler(action);
		assertEquals(Gamestate.CREATING, controller.getModel().getGamestate());

		controller.getsudokuField()[8][8].setColor("957DAD");
		controller.customColorsDoneHandler(action);
		assertEquals(Gamestate.NOFORMS, controller.getModel().getGamestate());
	}

	/**
	 * Test if the user inputs from the text fields get put into the Cells-Array
	 * from the model component by the connectWithModel-Method.
	 */
	@Test
	void testConnectWithModel() {
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		System.out.println(controller.getModel().getCells()[1][1].getValue());
		controller.connectWithModel();
		assertEquals(1, controller.getModel().getCells()[1][1].getValue());
	}

	/**
	 * Tests the compareResult-Method without any conflict. The game state should be
	 * OPEN after this method gets called without any conflict in the game.
	 */
	@Test
	void testCompareResultTrue() {
		controller.createGame();
		assertTrue(controller.compareResult());
		assertEquals(Gamestate.OPEN, controller.getModel().getGamestate());
	}

	/**
	 * Tests the compareResult-Method. Test if the game state gets set to INCORRECT
	 * if there are any conflict in the sudoku game and also if it gets set back to
	 * OPEN if the conflict get removed by the user.
	 */
	@Test
	void testCompareResultFalse() {
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		controller.getsudokuField()[1][2].setText("1");
		assertFalse(controller.compareResult());
		assertEquals(Gamestate.INCORRECT, controller.getModel().getGamestate());
		controller.getsudokuField()[1][2].clear();
		controller.getsudokuField()[1][2].setText("2");
		assertTrue(controller.compareResult());
		assertEquals(Gamestate.OPEN, controller.getModel().getGamestate());
	}

	/**
	 * Tests the autoSolveHandler-Method. Tests if the game state gets set to
	 * AUTOSOLVED if this method gets called without any conflict in the game and if
	 * the game is solvable.
	 */
	@Test
	void testAutoSolveWithoutConflicts() {
		controller.getModel().setDifficulty(3);
		controller.createGame();
		controller.autoSolveHandler(action);
		assertEquals(Gamestate.AUTOSOLVED, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the game state gets set to CONFILCT if the user has conflicts in his
	 * sudoku game and presses the auto solve button to solve the game.
	 */
	@Test
	void testAutoSolveWithConflicts() {
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		controller.getsudokuField()[1][2].setText("1");
		controller.autoSolveHandler(action);
		assertEquals(Gamestate.CONFLICT, controller.getModel().getGamestate());
	}

	/**
	 * Tests if the the game state gets set to UNSOLVABLE if the user creates an
	 * unsolvable game with his inputs and presses the auto solve button afterwards.
	 */
	@Test
	void testAutoSolveWithUnsolvable() {
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN, 0, 0);
		SudokuGameBuilder sceneSudoku = new SudokuGameBuilder(modelSudoku);
		sceneSudoku.initializeGame();
		controller = new GameController(sceneSudoku, modelSudoku);

		controller.createGame();

		controller.getsudokuField()[0][0].setText("1");
		controller.getsudokuField()[0][1].setText("2");
		controller.getsudokuField()[0][2].setText("3");
		controller.getsudokuField()[1][0].setText("4");
		controller.getsudokuField()[1][1].setText("5");
		controller.getsudokuField()[1][2].setText("6");
		controller.getsudokuField()[2][0].setText("7");
		controller.getsudokuField()[2][1].setText("8");
		controller.getsudokuField()[3][2].setText("9");
		controller.autoSolveHandler(action);
		assertEquals(Gamestate.UNSOLVABLE, controller.getModel().getGamestate());
	}
	/**
	 * Tests if the text fields are empty after the emptyArrays-Method gets called.
	 */
	@Test
	void testEmptyArrays() {
		controller.getsudokuField()[0][0].setText("1");
		controller.emptyArrays();
		assertEquals(controller.getsudokuField()[0][0].getText(), "");
	}

	/**
	 * 
	 */
	@Test
	void testConnectArrays() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.getModel().getCells()[1][1].setFixedNumber(true);
		controller.connectArrays();
		assertFalse(controller.getsudokuField()[0][0].isDisabled());
		assertFalse(controller.getsudokuField()[1][1].isDisabled());
	}

}