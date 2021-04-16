package application;

import java.util.stream.Stream;

import controller.MainMenuController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class MainMenu {
	
	
	static BorderPane pane = new BorderPane();
	static Scene mainScene = new Scene(pane,600,600);
	private Button sudoku;
	private Button samurai;
	private Button freeform;
	
	
	public Scene setUpMainMenu() {
		
		
		HBox buttonMenu = new HBox();
	    buttonMenu.setSpacing(145);
		buttonMenu.setPrefWidth(100);
		
		sudoku = new Button("Sudoku");
		
		samurai = new Button("Samura");
		
		freeform = new Button("Freeform");
		
		
		MainMenuController controller = new MainMenuController(this);
		
		sudoku.setOnAction(controller::handleToSudoku);
		samurai.setOnAction(controller::handleToSamurai);
		freeform.setOnAction(controller::handleToFreeForm);
		
		//design for buttons
		 Stream.of(sudoku, samurai, freeform).forEach(button -> 
		    button.getStyleClass().add("button1"));
		
		//größe der Buttons
		 Stream.of(sudoku, samurai, freeform).forEach(button -> 
		    button.setMinWidth(buttonMenu.getPrefWidth()));
		 
		buttonMenu.getChildren().addAll(sudoku,samurai,freeform);
		
		Label welcomeLabel = new Label("Welcome to our Sudoku Game, please choose a gamemode");
		welcomeLabel.setFont(new Font("Arial",20));
		
		pane.setBottom(buttonMenu);
		pane.setCenter(welcomeLabel);
		
		mainScene.getStylesheets().add("main/resources/CSS/sudoku.css");
		
		
		
		return mainScene;
	}
	
	
	public static Scene getMainMenu() {
		return mainScene;
	}
	
	
}
