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
	
	/**
	 * before each test a new empty logic model is set up
	 */
	@BeforeEach
	public void setUp(){
		model = new SamuraiLogic(Gamestate.OPEN, 0 , 0);
		model.setUpLogicArray();
	}
	
	/**
	 * getters and setters
	 */
	@Test
	void testSetCells() {
		SamuraiLogic model2 = new SamuraiLogic(Gamestate.OPEN, 0 , 0);
		model2.setUpLogicArray();
		model2.getCells()[1][1].setValue(1);
		 model.setCells(model2.getCells());
		 assertEquals(model2.getCells()[1][1],model.getCells()[1][1]);
	}
	
	
	@Test
	void testGetCells() {
		Cell[][] a = model.getCells();
		  assertArrayEquals(model.getCells(),a);
	}
	

    @Test
    void testSetDifficulty() {
        model.setDifficulty(5);
        assertEquals(5,model.getDifficulty());
    }
    
    @Test
    void testGetDifficulty() {
    	model.setDifficulty(7);
        assertEquals(7,model.getDifficulty());
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
        assertEquals(50, model.getSecondsplayed());
    }
    @Test
    void testSetSecondsPlayed() {
        model.setSecondsPlayed(50);
        assertEquals(50,model.getSecondsplayed());
    }

    @Test
    void testGetMinutesPlayed() {
        model.setMinutesPlayed(3);
        assertEquals(3,model.getMinutesplayed());
    }
    @Test
    void testSetMinutesPlayed() {
        model.setMinutesPlayed(3);
        assertEquals(3,model.getMinutesplayed());
    }
    
    @Test
    void testGetGamePoints() {
        assertEquals(10,model.getGamepoints());
    }
    @Test
    void testSetGamePoints() {
        model.setGamePoints(11);
        assertEquals(11,model.getGamepoints());
    }
    
    
    @Test
    void testSetDifficultyString7() {
    	model.setDifficulty(7);
        model.setDifficultyString();
        assertEquals( "Easy", model.getDifficultystring());
    }
    @Test
    void testSetDifficultyString5() {
    	model.setDifficulty(5);
        model.setDifficultyString();
        assertEquals("Medium", model.getDifficultystring());
    }
    @Test
    void testSetDifficultyString3() {
    	model.setDifficulty(3);
        model.setDifficultyString();
        assertEquals( "Hard",model.getDifficultystring());
    }
    @Test
    void testSetDifficultyString0() {
    	model.setDifficulty(0);
        model.setDifficultyString();
        assertEquals("Manual", model.getDifficultystring());
    }
    
    @Test
    void testGetGameID() {
        model.setGameID(1);
        assertEquals(1,model.getGameid());
    }
    @Test
    void testSetGameID() {
        model.setGameID(1);
        assertEquals(1,model.getGameid());
    }
    

    
    @Test
    void testGetGameText() {
        model.setGameState(Gamestate.OPEN);
      //  assertEquals(model.getGameText(), "Game ongoing!");
        model.setGameState(Gamestate.DONE);
        assertEquals( "Congratulations you won!",model.getGameText());
        model.setGameState(Gamestate.INCORRECT);
        assertEquals( "Sorry your Sudoku is not correct yet",model.getGameText());
        model.setGameState(Gamestate.AUTOSOLVED);
        assertEquals("Autosolved",model.getGameText());
        model.setGameState(Gamestate.CONFLICT);
//        assertEquals(model.getGameText(), "Please remove the conflicts before autosolving");
//        model.setGameState(Gamestate.UNSOLVABLE);
 //       assertEquals(model.getGameText(), "Unsolvable Sudoku! New Solution generated");  
    }
    
    @Test
    void testGetGameState() {
        model.setGameState(Gamestate.OPEN);
        assertEquals(Gamestate.OPEN,model.getGamestate());
    }
    @Test
    void testGetGameTyp() {
    	 assertEquals("Samurai",model.getGametype());
    }
    

    


}