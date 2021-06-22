package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

	@BeforeEach
	public void setUp() {
		FreeFormLogic model = new FreeFormLogic(Gamestate.OPEN,0,0);
		FreeFormGameBuilder scene = new FreeFormGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
	}
	
	@Test
	void testConstructorWithDifferentGameTypes() {
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN,0,0);
		SamuraiGameBuilder scene = new SamuraiGameBuilder(model);
		scene.initializeGame();
		controller = new GameController(scene, model);
		
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN,0,0);
		SudokuGameBuilder sceneSudoku = new SudokuGameBuilder(modelSudoku);
		sceneSudoku.initializeGame();
		controller = new GameController(sceneSudoku, modelSudoku);
	}

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
	
	@Test
	void testHintHandlerWithUnsolvable() {
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN,0,0);
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
	
//	@Test
//	void testCheckHandlerWithoutConflict() {
//		controller.getModel().setDifficulty(3);
//		controller.createGame();
//	}
	
	
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
	
	@Test
	void testResetHandler() {
		controller.createGame();
		controller.getModel().getCells()[1][1].setValue(1);
		controller.getsudokuField()[1][1].setText("1");
		controller.connectWithModel();
		assertEquals(1, controller.getModel().getCells()[1][1].getValue());
		assertTrue(controller.getsudokuField()[1][1].getText().equals("1"));
		controller.resetHandler(action);
		assertEquals(0, controller.getModel().getCells()[1][1].getValue());
		assertTrue(controller.getsudokuField()[1][1].getText().equals(""));
	}
	
	@Test
	void testConnectWithModel() {
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		System.out.println(controller.getModel().getCells()[1][1].getValue());
//		assertTrue(controller.getsudokuField()[1][1].getText().equals(""));
	}
	
	@Test
	void testCompareResultTrue() {
		controller.createGame();
		assertTrue(controller.compareResult());
		assertEquals(Gamestate.OPEN, controller.getModel().getGamestate());
	}
	
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
	
	@Test
	void testAutoSolveWithoutConflicts() {
		controller.getModel().setDifficulty(3);
		controller.createGame();
		controller.autoSolveHandler(action);
		assertEquals(Gamestate.AUTOSOLVED, controller.getModel().getGamestate());
	}

	@Test
	void testAutoSolveWithConflicts() {
		controller.createGame();
		controller.getsudokuField()[1][1].setText("1");
		controller.getsudokuField()[1][2].setText("1");
		controller.autoSolveHandler(action);
		assertEquals(Gamestate.CONFLICT, controller.getModel().getGamestate());
	}
	
	@Test
	void testAutoSolveWithUnsolvable() {
		SudokuLogic modelSudoku = new SudokuLogic(Gamestate.OPEN,0,0);
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
	
	
	
	@Test
	void testEmptyArrays() {
		controller.emptyArrays();
		assertEquals(controller.getsudokuField()[0][0].getText(),"");
	}
	
	@Test
	void testConnectArrays() {
		controller.getModel().setDifficulty(0);
		controller.createGame();
		controller.getModel().getCells()[1][1].setFixedNumber(true);
		controller.connectArrays();
		assertFalse(controller.getsudokuField()[0][0].isDisabled());
		assertTrue(controller.getsudokuField()[1][1].isDisabled());
	}
	
}