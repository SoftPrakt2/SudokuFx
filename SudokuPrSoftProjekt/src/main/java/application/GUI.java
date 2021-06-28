package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	
	static Stage window;

	/**
	 * starting scene of the program
	 */
	private Scene mainScene;
	
	
	/**
	 * main UI container for the starting scene
	 */
	static Pane mainPane;

	
	/**
	 * responsible for launching the application 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * This method is called when starting the program
	 * The MainScene of the program is set to the window scene created in the {@link application.MainMenu} class
	 */
	@Override
	public void start(Stage scene) {
		
		MainMenu mainmenu = new MainMenu();
		
		window = scene;
		scene.setMaxWidth(1500);
		scene.setMaxHeight(1500);
		mainmenu.setUpMainMenu();
		mainScene = mainmenu.getScene();

		mainPane = mainmenu.getPane();
		window.setScene(mainScene);
	
	
		mainScene.getStylesheets().add(getClass().getResource("/CSS/sudoku.css").toExternalForm());
	
	
		window.show();
		
		window.setTitle("SudokuFx");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
			
		});

	}
	
	
	/**
	 * auxiliary method which is responsible for adding the CloseWindow PopUp when starting the program 
	 */
	public static void closeProgram() {
		CloseWindowStage closingStage = new CloseWindowStage();
		closingStage.showPopUp();
		if (closingStage.getClosingAnswer()) {
			window.close();
			Platform.exit();
		}
	}

	public static Stage getStage() {
		return window;
	}

	public static Pane getMainMenu() {
		return mainPane;
	}

}
