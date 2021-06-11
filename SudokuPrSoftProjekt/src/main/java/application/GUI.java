package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	

	static Stage window;

	private Scene mainScene;
	static Pane mainPane;

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage scene) {
		
		MainMenu mainmenu = new MainMenu();
		
		// test commit
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
			// consume heißt führe den schließvorgang fort und es wird nicht immer
			// geschlossen nur wenn yes gedrückt wird
			e.consume();
			closeProgram();
			
		});

	}

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
