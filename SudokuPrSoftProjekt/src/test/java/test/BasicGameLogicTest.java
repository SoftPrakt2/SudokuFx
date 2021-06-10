package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.BasicGameLogic;
import logic.Cell;
import logic.Gamestate;
import logic.SamuraiLogic;

class BasicGameLogicTest {
	
	BasicGameLogic model;
	
	@BeforeEach
	public void setUp(){
		model = new SamuraiLogic(Gamestate.OPEN, 0 , 0 , false);
		model.setUpLogicArray();
	}
	

	@Test
	void testSetCells() {
		SamuraiLogic model2 = new SamuraiLogic(Gamestate.OPEN, 0 , 0 , false);
		model2.setUpLogicArray();
		model2.getCells()[1][1].setValue(1);
		 model.setCells(model2.getCells());
		 assertEquals(model.getCells()[1][1], model2.getCells()[1][1]);
	}
	
	
	@Test
	void testGetCells() {
		Cell[][] a = model.getCells();
		  assertArrayEquals(a, model.getCells());
	}
	

    @Test
    void testSetDifficulty() {
        model.setDifficulty(5);
        assertEquals(model.getDifficulty(), 5);
    }
    
    @Test
    void testGetDifficulty() {
    	model.setDifficulty(7);
        assertEquals(model.getDifficulty(), 7);
    }
    
//    @Test
//    public void testGetLoadedMinutes() {
//        model.setLoadedMinutes(30);
//        assertEquals(model.getLoadedMinutes(), 30);
//    }
//    @Test
//    public void testSetLoadedMinutes() {
//        model.setLoadedMinutes(30);
//        assertEquals(model.getLoadedMinutes(), 30);
//    }
//
//
//    @Test
//    public void testGetLoadedSeconds() {
//        model.setLoadedSeconds(30);
//        assertEquals(model.getLoadedSeconds(), 30);
//    }
//    @Test
//    public void testSetLoadedSeconds() {
//        model.setLoadedSeconds(30);
//        assertEquals(model.getLoadedSeconds(), 30);
//    }

    @Test
    void testGetSecondsPlayed() {
        model.setSecondsPlayed(50);
        assertEquals(model.getSecondsplayed(), 50);
    }
    @Test
    void testSetSecondsPlayed() {
        model.setSecondsPlayed(50);
        assertEquals(model.getSecondsplayed(), 50);
    }

    @Test
    void testGetMinutesPlayed() {
        model.setMinutesPlayed(3);
        assertEquals(model.getMinutesplayed(), 3);
    }
    @Test
    void testSetMinutesPlayed() {
        model.setMinutesPlayed(3);
        assertEquals(model.getMinutesplayed(), 3);
    }
    
    @Test
    void testGetGamePoints() {
        assertEquals(model.getGamepoints(), 10);
    }
    @Test
    void testSetGamePoints() {
        model.setGamePoints(11);
        assertEquals(model.getGamepoints(), 11);
    }
    
    
    @Test
    void testSetDifficultyString7() {
    	model.setDifficulty(7);
        model.setDifficultyString();
        assertEquals(model.getDifficultystring(), "Easy");
    }
    @Test
    void testSetDifficultyString5() {
    	model.setDifficulty(5);
        model.setDifficultyString();
        assertEquals(model.getDifficultystring(), "Medium");
    }
    @Test
    void testSetDifficultyString3() {
    	model.setDifficulty(3);
        model.setDifficultyString();
        assertEquals(model.getDifficultystring(), "Hard");
    }
    @Test
    void testSetDifficultyString0() {
    	model.setDifficulty(0);
        model.setDifficultyString();
        assertEquals(model.getDifficultystring(), "Manual");
    }
    
    @Test
    void testGetGameID() {
        model.setGameID(1);
        assertEquals(model.getGameid(), 1);
    }
    @Test
    void testSetGameID() {
        model.setGameID(1);
        assertEquals(model.getGameid(), 1);
    }
    
    
    
    @Test
    void testGetStartTime() {
        model.setStartTime(0);
        assertEquals(model.getStartTime(), 0);
    }
    @Test
    void testSetStartTime() {
        model.setStartTime(0);
        assertEquals(model.getStartTime(), 0);
    }
    
    @Test
    void testGetGameText() {
        model.setGameState(Gamestate.OPEN);
      //  assertEquals(model.getGameText(), "Game ongoing!");
        model.setGameState(Gamestate.DONE);
        assertEquals(model.getGameText(), "Congratulations you won!");
        model.setGameState(Gamestate.INCORRECT);
        assertEquals(model.getGameText(), "Sorry your Sudoku is not correct yet");
        model.setGameState(Gamestate.AUTOSOLVED);
        assertEquals(model.getGameText(), "Autosolved");
        model.setGameState(Gamestate.CONFLICT);
//        assertEquals(model.getGameText(), "Please remove the conflicts before autosolving");
//        model.setGameState(Gamestate.UNSOLVABLE);
 //       assertEquals(model.getGameText(), "Unsolvable Sudoku! New Solution generated");  
    }
    
    @Test
    void testGetGameState() {
        model.setGameState(Gamestate.OPEN);
        assertEquals(model.getGamestate(), Gamestate.OPEN);
    }
    @Test
    void testGetGameTyp() {
    	 assertEquals(model.getGametype(), "Samurai");
    }
    
    @Test
    void testCalculateGameTime() {
//    	model.setMinutesPlayed(2);
//    	model.setSecondsPlayed(30);
//    	
//    	
//		long time;
//		long endTime = System.currentTimeMillis();
//		time = (endTime - model.getStartTime()) / 1000;
////		time += model.getLoadedminutes() * 60 + model.getLoadedseconds();
//		model.setSecondsPlayed(time);
//		if (time > 60) {
//			model.setMinutesPlayed(time / 60);
//			model.setSecondsPlayed(time % 60);
//		}
//		
//        assertEquals(model.calculateGameTime(), time);
    }
    


}
