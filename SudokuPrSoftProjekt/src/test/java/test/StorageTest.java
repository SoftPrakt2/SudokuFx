//package test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.File;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
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
//    File[] fileDirectory = new File("SaveFiles").listFiles();
//
//    @BeforeEach
//    void setUp() throws Exception {
//        saveModel = new SaveModel();
//        sudoku = new SudokuLogic(Gamestate.OPEN,0,0);
//        storage = new SudokuStorage();
//    //    sudoku.setUpGameInformations();
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
//    }
//
//
//    /**
//     * tests if a game is correctly saved and written into the projects save directory
//     */
//    @Test
//    void testsaveGame() {
//        saveModel = storage.setInformationsToStore(sudoku);
//        assertEquals(saveModel.getGametype(),sudoku.getGametype());
//
//        storage.saveGame(sudoku);
//        for(File f : fileDirectory) {
//            if(storage.convertFileToSaveModel(f).getGameId() == 1380) {
//                assertEquals(storage.convertFileToSaveModel(f).getGameId(),1380);
//            }
//        }
//    }
//
//    @Test
//    void testLoadIntoModel() {
//        saveModel = storage.setInformationsToStore(sudoku);
//        storage.loadIntoModel(sudoku, saveModel);
//        assertEquals(sudoku.getGameid(),saveModel.getGameId());
//    }
//}