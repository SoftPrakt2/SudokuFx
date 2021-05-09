package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class GameControllerTest {

	@Test
	public void start(){
		SamuraiLogic model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);

		// *setUpLogicArray() *
		model.setUpLogicArray();
		assertTrue(model.getCells()[0][0].getValue() == -1);
	}
}