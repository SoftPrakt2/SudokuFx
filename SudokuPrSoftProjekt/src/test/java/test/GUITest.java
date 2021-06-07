//package test;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxToolkit;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import application.SudokuGameBuilder;
//import controller.GameController;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//import logic.Gamestate;
//import logic.SudokuLogic;
//
//@ExtendWith(ApplicationExtension.class)
//class GUITest {
//
//	private Button button;
//	SudokuLogic logic;
//	SudokuGameBuilder sud;
//	GameController controller;
//
//	/**
//	 * Will be called with {@code @Before} semantics, i. e. before each test method.
//	 *
//	 * @param stage - Will be injected by the test runner.
//	 */
//
//	@BeforeAll
//	public static void setupSpec() throws Exception {
//
//		System.setProperty("testfx.robot", "glass");
//		System.setProperty("testfx.headless", "true");
//		System.setProperty("prism.order", "sw");
//		System.setProperty("prism.text", "t2k");
//		System.setProperty("java.awt.headless", "true");
//
//		System.setProperty("testfx.robot", "glass");
//
//		System.setProperty("prism.text", "t2k");
//		System.setProperty("java.awt.headless", "true");
//		FxToolkit.registerPrimaryStage();
//	}
//
//	/**
//	 * Will be called with {@code @Before} semantics, i. e. before each test method.
//	 *
//	 * @param stage - Will be injected by the test runner.
//	 */
//	@Start
//	private void start(Stage stage) {
//		// final String[] args = new String[];
//		// GUI.main(args);
//		logic = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
//		sud = new SudokuGameBuilder(logic);
//		sud.initializeGame();
//		controller = sud.getController();
//	}
//
//	/**
//	 * @param robot - Will be injected by the test runner.
//	 */
//
//	/**
//	 * @param robot - Will be injected by the test runner.
//	 */
//
//	@Test
//	public void test() {
//		controller.createGame();
//		controller.autoSolveHandler(new ActionEvent());
//		
//	}
//
//	
//}
