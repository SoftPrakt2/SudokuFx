//package test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import application.MainMenu;
//import application.SudokuGameBuilder;
//import controller.GameController;
//import javafx.application.Application;
//import javafx.stage.Stage;
//import logic.BasicGameLogic;
//import logic.Gamestate;
//import logic.SamuraiLogic;
//import logic.SudokuLogic;
//
//public class GameControllerTest {
//	
//	BasicGameLogic model;
//	SudokuGameBuilder game;
//	GameController controller;
//	
//	@BeforeEach
//	public void setUp(){
//		model = new SamuraiLogic(Gamestate.OPEN, 0 , 0 , false);
//		model.setUpLogicArray();
//		game = new SudokuGameBuilder(model);
//		game.initializeGame();
//		controller = game.getController();
//	}
//	
//
//	@Test
//	public void start(){
//	
//		// *setUpLogicArray() *
//		model.setUpLogicArray();
//		assertTrue(model.getCells()[0][0].getValue() == 0);
//		controller.createGame();
//	}
//}
//
