//package test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.File;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import controller.StorageController;
//import logic.Gamestate;
//import logic.SaveModel;
//import logic.SudokuLogic;
//import logic.SudokuStorage;
//
//
//class StorageTest {
//
//    SaveModel saveModel;
//    SudokuLogic sudoku;
//    SudokuStorage storage;
//    StorageController controller;
//    
//
//    @BeforeEach
//    void setUp() throws Exception {
//        saveModel = new SaveModel();
//        sudoku = new SudokuLogic(Gamestate.OPEN,0,0);
//        storage = new SudokuStorage();
//      
//        sudoku.setGameState(Gamestate.OPEN);
//        sudoku.setDifficulty(3);
//        sudoku.setUpLogicArray();
//        sudoku.createSudoku();
//        sudoku.difficulty();
//        sudoku.setGamePoints(10);
//        sudoku.setGametype("Sudoku");
//        sudoku.setSecondsPlayed(7);
//        sudoku.setMinutesPlayed(1);
//        sudoku.setDifficultyString();
//        sudoku.setGameID(1380);
//        saveModel = storage.setInformationsToStore(sudoku);
//        storage.saveGame(sudoku);
//    }
//
//
//    /**
//     * tests if a game is correctly saved and written into the projects save directory
//     */
//    @Test
//    void testsaveGame() {
//    	File[] fileDirectory = new File("SaveFiles").listFiles();
//        assertEquals(saveModel.getGametype(),sudoku.getGametype());
//
//      
//        for(File f : fileDirectory) {
//            if(storage.convertFileToSaveModel(f).getGameId() == 1380) {
//                assertEquals(1380,storage.convertFileToSaveModel(f).getGameId());
//            }
//        }
//       
//    }
//    
//    
//   @AfterEach
//    void deletefileCreatedForTest() {
//	   File[] fileDirectory = new File("SaveFiles").listFiles();
//	   SaveModel saveModel;
//	   
//    	 for(File f : fileDirectory) {
//    		 saveModel = storage.convertFileToSaveModel(f);
//    		   if(saveModel.getGameId() == 1380) {
//            	
//            	 if(f.delete())
//                 {
//                     System.out.println("File deleted successfully");
//                 }
//                 else
//                 {
//                     System.out.println("Failed to delete the file");
//                 }
//         }
//    	 }
//    }
//    
//
//
//}