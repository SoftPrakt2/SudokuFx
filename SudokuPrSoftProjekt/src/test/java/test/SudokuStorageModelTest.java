package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;
import logic.SudokuStorageModel;

class SudokuStorageModelTest {

	SudokuStorageModel ssmt = new SudokuStorageModel();
	BasicGameLogic model = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
	
	@BeforeEach
	public void setUp() {
		SudokuStorageModel ssmt = new SudokuStorageModel();
		BasicGameLogic model = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
	}

	@Test
	void testPrep() {		
//		ssmt.prepareSave(model);
//		ssmt.storeIntoJSON();
//		ssmt.storeIntoFile(new File));
//		ssmt.saveGame(model);
//		ssmt.exportGame(model);
//		ssmt.getImportedGame();
//		ssmt.loadIntoModel();
//		ssmt.convertToJSON(new File));
//		ssmt.calculateGamePoints();
//		ssmt.calculateAverageTimePlayed();
//		ssmt.calculateGameStats();
//		ssmt.getLoadedLogic();
//		ssmt.setLoadedLogic(model);
//		ssmt.setJSONObject(new JSONObject);
//		ssmt.getLoadedGameType();
//		ssmt.getSaveDirectory();
//		ssmt.getOverallGamePoints();
//		ssmt.getAveragePoints();
//		ssmt.getPlayedMinutesOverall();
//		ssmt.getSecondsPlayedOverall();
	}	
}
