//package test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.Rule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.rules.TemporaryFolder;
//
//import application.FreeFormGameBuilder;
//import controller.GameController;
//import logic.FreeFormLogic;
//import logic.Gamestate;
//import logic.SaveModel;
//import logic.SudokuLogic;
//import logic.SudokuStorage;
//
//class SudokuStoargeTest {
//	
//	
//	 @Rule
//     public TemporaryFolder folder = new TemporaryFolder();
//		SudokuLogic model;
//		SaveModel modelToSave;
//		SudokuStorage storage;
//	
//	@BeforeEach
//	public void setUp() {
//		model = new SudokuLogic(Gamestate.OPEN,0,0);
//		modelToSave = new SaveModel();
//		storage = new SudokuStorage();
//		
//		model.setGamePoints(10);
//		model.setGameState(Gamestate.OPEN);
//		model.setDifficultyString();
//		model.setMinutesPlayed(0);
//		model.setSecondsPlayed(0);
//		model.setDifficulty(3);
//		model.setUpGameField();
//	}
//	
//	
//
//	@Test
//	void testSetStoredInformations() {
//		SaveModel savedModel = storage.setInformationsToStore(model);
//		assertEquals(savedModel.getGametype(),model.getGametype());
//	}
//	
//	@Test
//	void testSaveGame() {
//		storage.saveGame(model);
//	}
//
//}