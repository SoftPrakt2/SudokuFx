//package test;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.api.FxToolkit;
//import org.testfx.assertions.api.Assertions;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import application.GUI;
//import application.MainMenu;
//import application.SudokuGameBuilder;
//import controller.GameController;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import logic.Gamestate;
//import logic.SudokuLogic;
//
//@ExtendWith(ApplicationExtension.class)
//  class GUITest  {
//	
//	//private Button button;
//	
//
//    /**
//     * Will be called with {@code @Before} semantics, i. e. before each test method.
//     *
//     * @param stage - Will be injected by the test runner.
//     */
//	
//	@BeforeAll
//	public static void setupSpec() throws Exception {
//	
//	
//	            System.setProperty("testfx.robot", "glass");
//	         
//	            System.setProperty("prism.text", "t2k");
//	            System.setProperty("java.awt.headless", "true");
//	         //   FxToolkit.registerPrimaryStage();
//	        }
//	 
//    /**
//     * Will be called with {@code @Before} semantics, i. e. before each test method.
//     *
//     * @param stage - Will be injected by the test runner.
//     */
//    @Start
//    private void start(Stage stage) {
//    //	final String[] args = new String[];
//     //   GUI.main(args);
//    }
//
//    /**
//     * @param robot - Will be injected by the test runner.
//     */
//
//
//   /**
//    * @param robot - Will be injected by the test runner.
//    */
//   
//
//
//
//	
//	
//	
//	
//
//
//	    @Test
//	    public void test() {
//	    	
//	    	SudokuLogic logic = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
//	    	SudokuGameBuilder sud = new SudokuGameBuilder(logic);
//	    	sud.initializeGame();
//	    	GameController mainMenu = new GameController(sud,logic);
//	    	
//	    	mainMenu.createGame();
//	    	mainMenu.enableEdit();
//	    }
//}
