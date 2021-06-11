package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import application.GUI;
import application.MainMenu;
import controller.ModeController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;


@ExtendWith(ApplicationExtension.class)
class ModeControllerTest {

	
	private MainMenu menu;
	ModeController controller;
	private ActionEvent actionEvent = new ActionEvent();
	
	@BeforeEach
	void setUp() {
		menu = new MainMenu();
		menu.setUpMainMenu();
		controller = new ModeController(menu);
		actionEvent = new ActionEvent();
	}
	
	
	@Test
	void testHandleToSudoku() {
		controller.handleToSudoku(actionEvent);
		assertEquals("Sudoku",controller.getModel().getGametype());
		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
	}
	
	@Test
	void testHandleToSamurai() {
		controller.handleToSamurai(actionEvent);
		assertEquals("Samurai",controller.getModel().getGametype());
		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
	}
	
	@Test
	void testHandleToFreeForm() {
		controller.handleToFreeForm(actionEvent);
		assertEquals("FreeForm",controller.getModel().getGametype());
		assertNotNull(controller.getGameBuilder().getAutoSolveButton());
	}
	
	@Test
	void testHandleEasy() {
		controller.handleToSudoku(actionEvent);
		controller.handleEasy(actionEvent);
		assertEquals(7,controller.getSelectedDifficulty());
	}
	
	@Test
	void testHandleMedium() {
		controller.handleToSudoku(actionEvent);
		controller.handleMedium(actionEvent);
		assertEquals(5,controller.getSelectedDifficulty());
	}
	
	@Test
	void testHandleHard() {
		controller.handleToSudoku(actionEvent);
		controller.handleHard(actionEvent);
		assertEquals(3,controller.getSelectedDifficulty());
	}
	
	@Test
	void testUnlockGameActionButtonsFreeForm() {
		controller.handleToFreeForm(actionEvent);
		controller.unlockGameActionButtons();
		
	}
	
	@Test
	void testRemoveSelectedToggle() {
		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
			toggle.setSelected(true);
		}
		for(Toggle toggle : menu.getPlayModeToggle().getToggles()) {
			toggle.setSelected(true);
		}
		
		controller.removeSelectedToggles();	
		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
			assertFalse(toggle.isSelected());
		}
		for(Toggle toggle : menu.getPlayModeToggle().getToggles()) {
			assertFalse(toggle.isSelected());
		}
	}
	
	@Test
	void testDisableDifficultyButtons() {
		controller.disableDifficultyButtons();
		for(Toggle toggle : menu.getDifficultyToggle().getToggles()) {
			Node button = (Node) toggle;
			
			assertTrue(button.isDisabled());
		}
	}
	

}
