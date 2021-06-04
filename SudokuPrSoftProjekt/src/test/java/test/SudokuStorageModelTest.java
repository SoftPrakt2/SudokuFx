package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.prefs.Preferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;
import logic.SudokuStorageModel;

public class SudokuStorageModelTest {

	SudokuStorageModel ssmt = new SudokuStorageModel();
	BasicGameLogic model = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
	
	@BeforeEach
	public void setUp() {
		SudokuStorageModel ssmt = new SudokuStorageModel();
		BasicGameLogic model = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
	}

	@Test
	public void testPrep() {		
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
