package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import application.BasicGameBuilder;
import application.MainMenu;
import application.SudokuGameBuilder;
import controller.GameController;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.Gamestate;
import logic.SudokuLogic;

@ExtendWith(ApplicationExtension.class)
public class GUITest  {
	
	
	
	
//
//
//	    @Test
//	    public void test() {
//	    	GameController mainMenu = new GameController(new SudokuGameBuilder(),  new SudokuLogic(Gamestate.OPEN, 0, 0, false));
//	    	mainMenu.calculateGameTime();
//	    //	mainMenu.createGame(3);
//	    }
}
