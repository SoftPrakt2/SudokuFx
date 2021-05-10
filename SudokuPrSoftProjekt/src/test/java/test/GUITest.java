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
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//@ExtendWith(ApplicationExtension.class)
//  class GUITest  {
//	
//	//private Button button;
//	
//	
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
//	  
//	private Button button;
//
//    /**
//     * Will be called with {@code @Before} semantics, i. e. before each test method.
//     *
//     * @param stage - Will be injected by the test runner.
//     */
//    @Start
//    private void start(Stage stage) {
//        button = new Button("click me!");
//        button.setId("myButton");
//        button.setOnAction(actionEvent -> button.setText("clicked!"));
//     
//    }
//
//    /**
//     * @param robot - Will be injected by the test runner.
//     */
//    @Test
//    void should_contain_button_with_text(FxRobot robot) {
//        Assertions.assertThat(button).hasText("click me!");
//        // or (lookup by css id):
//       
//    }
//
//   /**
//    * @param robot - Will be injected by the test runner.
//    */
//   
//}
//
