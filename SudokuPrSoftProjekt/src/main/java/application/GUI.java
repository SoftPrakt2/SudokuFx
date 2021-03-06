package application;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
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
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method is called when starting the program The main scene of the program
	 * is set to the window scene created in the {@link application.MainMenu} class
	 * @param scene stage object of the program 
	 */
	@Override
	public void start(Stage scene) {

		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		MainMenu mainmenu = new MainMenu();
		

		window = scene;
		mainmenu.setUpMainMenu();
		mainScene = mainmenu.getScene();
		mainPane = mainmenu.getPane();
		mainScene.getStylesheets().add(getClass().getResource("/CSS/sudoku.css").toExternalForm());
		
		window.setMaxWidth(bounds.getWidth() * 2);
		window.setMaxHeight(bounds.getHeight() * 2);

		window.setX((bounds.getWidth() - mainmenu.getScreenWidth()) / 2);
		window.setY((bounds.getHeight() - mainmenu.getScreenHeight()) / 2);
		window.setScene(mainScene);

		window.setTitle("SudokuFx");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();

		});
		
		createSaveDirectory();

		window.show();

		
	}

	/**
	 * auxiliary method which is responsible for adding the CloseWindow PopUp when
	 * starting the program
	 */
	public static void closeProgram() {
		CloseWindowStage closingStage = new CloseWindowStage();
		closingStage.showPopUp();
		if (closingStage.getClosingAnswer()) {
			window.close();
			Platform.exit();
		}
	}
	
	
	/**
	 * creates the save directory "SaveFiles" of the program if it does not already exist
	 */
	public void createSaveDirectory() {
		File file = new File("SaveFiles");
		 if (file.mkdirs()) {
	            System.out.println("Directory is created!");
	        } else {
	            System.out.println("Directory already exists");
	        }
	}
	
	

	public static Stage getStage() {
		return window;
	}

	public static Pane getMainMenu() {
		return mainPane;
	}

}
