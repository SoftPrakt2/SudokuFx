package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {

	static Stage window;

	Scene mainScene;
	MainMenu mainmenu = new MainMenu();
	static Pane mainPane;

	// this is just a test

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage scene) {
		

		// test commit
		window = scene;
		scene.setMaxWidth(1500);
		scene.setMaxHeight(1500);
		mainmenu.setUpMainMenu();
		mainScene = mainmenu.getScene();
		
		
		mainPane = mainmenu.getPane();
		window.setScene(mainScene);
		mainScene.getStylesheets().add("css/sudoku.css");
		window.show();

		window.setTitle("SudokuFx");
		window.setOnCloseRequest(e -> {
			// consume heißt führe den schließvorgang fort und es wird nicht immer
			// geschlossen nur wenn yes gedrückt wird
			e.consume();
			closeProgram();
		});

	}

	public static void closeProgram() {
		CloseWindowStage c = new CloseWindowStage();
		Boolean answer = c.showPopUp("Closing", "Are you sure that you want to close the program?");
		if (answer)
			window.close();
	}

	public static Stage getStage() {
		return window;
	}

	public static Pane getMainMenu() {
		return mainPane;
	}

}
