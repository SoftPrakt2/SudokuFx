//package test;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.framework.junit5.ApplicationExtension;
//
//import application.BasicGameBuilder;
//import application.FreeFormGameBuilder;
//import application.MainMenu;
//import application.SudokuGameBuilder;
//import controller.ModeController;
//import javafx.event.ActionEvent;
//import javafx.scene.Node;
//import javafx.scene.control.Toggle;
//import logic.BasicGameLogic;
//import logic.FreeFormLogic;
//import logic.Gamestate;
//import logic.SudokuLogic;
//
//
//@ExtendWith(ApplicationExtension.class)
//class ModeControllerTest {
//
//	
//	MainMenu menu;
//	ModeController controller;
//	ActionEvent actionEvent = new ActionEvent();
//	
//	@BeforeEach
//	void setUp() {
//		menu = new MainMenu();
//		menu.setUpMainMenu();
//		controller = new ModeController(menu);
//		actionEvent = new ActionEvent();
//	}
//	
//	
//	
//	@Test
//	void testHandleToSudoku() {
//		controller.handleToSudoku(actionEvent);
//		assertEquals("Sudoku",controller.getModel().getGametype());
//		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
//	}
//	
//	@Test
//	void testHandleToSamurai() {
//		controller.handleToSamurai(actionEvent);
//		assertEquals("Samurai",controller.getModel().getGametype());
//		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
//	}
//	
//	@Test
//	void testHandleToFreeForm() {
//		controller.handleToFreeForm(actionEvent);
//		assertEquals("FreeForm",controller.getModel().getGametype());
//		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
//	}
//	
//	@Test
//	void testHandleEasy() {
//		controller.handleToSudoku(actionEvent);
//		controller.handleEasy(actionEvent);
//		assertEquals(7,controller.getSelectedDifficulty());
//	}
//	
//	@Test
//	void testHandleMedium() {
//		controller.handleToSudoku(actionEvent);
//		controller.handleMedium(actionEvent);
//		assertEquals(5,controller.getSelectedDifficulty());
//	}
//	
//	@Test
//	void testHandleHard() {
//		controller.handleToSudoku(actionEvent);
//		controller.handleHard(actionEvent);
//		assertEquals(3,controller.getSelectedDifficulty());
//	}
//	
//	@Test
//	void testUnlockGameActionButtonsSudoku() {
//		controller.handleToSudoku(actionEvent);
//		controller.getGameBuilder().disablePlayButtons();
//		controller.getGameBuilder().getCustomNumbersDone().setVisible(true);
//		controller.unlockGameActionButtons();
//		assertFalse(controller.getGameBuilder().getCustomNumbersDone().isVisible());
//	}
//	
//	@Test
//	void testUnlockGameActionButtonsFreeForm() {
//		controller.handleToFreeForm(actionEvent);
//		controller.getGameBuilder().disablePlayButtons();
//		controller.getGameBuilder().getColorBox().setVisible(true);
//		controller.getGameBuilder().getColorsDoneButton().setVisible(true);
//		controller.unlockGameActionButtons();
//		assertFalse(controller.getGameBuilder().getColorsDoneButton().isVisible());
//	}
//	
//	
//	
//	
//	@Test
//	void testRemoveSelectedToggle() {
//		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
//			toggle.setSelected(true);
//		}
//		for(Toggle toggle : menu.getPlayModeToggle().getToggles()) {
//			toggle.setSelected(true);
//		}
//		
//		controller.removeSelectedToggles();	
//		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
//			assertFalse(toggle.isSelected());
//		}
//		for(Toggle toggle : menu.getPlayModeToggle().getToggles()) {
//			assertFalse(toggle.isSelected());
//		}
//	}
//	
//	@Test
//	void testDisableDifficultyButtons() {
//		controller.disableDifficultyButtons();
//		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
//			Node button = (Node) toggle;
//			
//			assertTrue(button.isDisabled());
//		}
//	}
//	
//	@Test
//	void testHandleManualFreeForm() {
//		BasicGameLogic model = new FreeFormLogic(Gamestate.OPEN, 0, 0);
//		BasicGameBuilder game = new FreeFormGameBuilder(model);
//		controller.setDifficulty(0);
//		controller.setModel(model);
//		controller.setGameScene(game);
//		game.initializeGame();
//		controller.handleManual(actionEvent);
//		assertEquals(Gamestate.DRAWING,model.getGamestate());
//	}
//	
//	
//	@Test
//	void testHandleManualSudoku() {
//		BasicGameLogic model = new SudokuLogic(Gamestate.OPEN, 0, 0);
//		BasicGameBuilder game = new SudokuGameBuilder(model);
//		controller.setDifficulty(0);
//		controller.setModel(model);
//		controller.setGameScene(game);
//		game.initializeGame();
//		controller.handleManual(actionEvent);
//		assertEquals(Gamestate.CREATING,model.getGamestate());
//	}
//	
//	@Test
//	void testUnlockPlayActions() {
//		controller.handleToSudoku(actionEvent);
//		
//	Stream.of(controller.getGameBuilder().getHintButton(), controller.getGameBuilder().getAutoSolveButton(), controller.getGameBuilder().getCheckButton())
//	.forEach(button -> {
//			button.setDisable(true);
//	});
//	controller.unlockGameActionButtons();
//	assertFalse(controller.getGameBuilder().getHintButton().isDisabled());
//}
//	
//	@Test
//	void testCheckIfManualWasPressed() {
//			controller.handleToSudoku(actionEvent);
//			controller.setDifficulty(0);
//			controller.checkIfManualWasPressed(actionEvent);
//	}
//}
