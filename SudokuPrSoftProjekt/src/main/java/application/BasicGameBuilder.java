package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PlatformLogger.Level;

import controller.BasicController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class BasicGameBuilder {
	
	protected VBox leftMenu;
	 protected Button play;
	 protected Button check;
	 protected Button owngame;
	protected  Button autosolve;
	protected Button create;
	
	  private long startTime;
	
	  ToggleButton hint;
	
	  
	  
	  SudokuField[][] textFields;
	  GridPane playBoard;
	  
	  
	  //change back to void
	  public abstract void initializeScene();
	 
	 
	 //vielleicht besser in den gamebuilderklassen direkt die boards zu zeichnen?
	 public abstract GridPane createBoard();
	 
	
	 //creates Vbox and adds several buttons to scene
	public void createPlayButtons(BorderPane pane) {
		
		
		  	leftMenu = new VBox(10);
		    leftMenu.setPrefWidth(100);
		    
		    play = new Button("Play");
		    hint = new ToggleButton("Hint");
		    hint.setOnAction(e -> showHint(pane));
		    check = new Button("Check");
		    autosolve = new Button("Autosolve");
		    create = new Button("Create Game");
		    owngame = new Button("Custom Game");
		
		    
		    Stream.of(play, hint, check, autosolve,create,owngame).forEach(button -> 
		    button.getStyleClass().add("button1"));
		   
		    
		    Stream.of(play, hint, check, autosolve,create,owngame).forEach(button -> 
		    button.setMinWidth(leftMenu.getPrefWidth()));
		    
		    
		    leftMenu.getChildren().addAll(play, hint,autosolve,create, check,owngame);
		    leftMenu.setAlignment(Pos.CENTER_LEFT);
		    pane.setLeft(leftMenu);
	}
	
	
	
	protected HBox hintBox;
	protected Label hintLabel;
	
public void showHint(BorderPane pane) {
		
		hintBox = new HBox(5);
	    hintBox.setVisible(false);
	    hintLabel = new Label("The next number is: ");
	    hintBox.getChildren().add(hintLabel);
	    hintBox.setAlignment(Pos.CENTER_RIGHT);
	    pane.setRight(hintBox);
	  
	   if(hint.isSelected()) {
		   hintBox.setVisible(true);
	   }
	  
   }

	
	protected Menu helpMenu;
	protected MenuBar menuBar;	
	protected MenuItem save;
	protected MenuItem load;
	protected Menu overView;
	protected Menu saveMenu;
	protected MenuItem newGameItem;
	protected Menu newGame;
	protected MenuItem recently;
	protected Menu difficultyMenu;
	
	
	protected Menu changeGameMode;
	protected MenuItem sudokuMode;
	protected MenuItem samuraiMode;
	protected MenuItem freeFormMode;
	
	protected ToggleGroup difficultyToggle;
	
	protected RadioMenuItem easy;
	protected RadioMenuItem medium;
	protected RadioMenuItem hard;
	protected OverviewStage o;
	
	Label label = new Label("easy");


public void createMenuBar(BorderPane pane) {
		
		//menuBar for the scene
		menuBar = new MenuBar();
		pane.setTop(menuBar);
		
		//Help eintrag mit rules menu
		helpMenu = new Menu("Help");
		helpMenu.getItems().add(new MenuItem("Rules"));
		
		menuBar.getMenus().addAll(helpMenu);
		
	
		//savemenu mit save und load optionen
		saveMenu = new Menu("Savegame"); 
		save = new MenuItem("Save");
		load = new MenuItem("Load");
		
		load.setOnAction(e-> openFile());
		
		
		saveMenu.getItems().addAll(save,load);
		menuBar.getMenus().add(saveMenu);
		
		
		//newgame menu eintrag
		newGame = new Menu("New Game");
		newGameItem = new MenuItem("Restart");
		newGame.getItems().add(newGameItem);
		
		
		//overview Menu opens overview window on click
		overView = new Menu("Overview");
		recently = new MenuItem("Recently Played");
		overView.getItems().add(recently);
		menuBar.getMenus().add(overView);
		recently.setOnAction(e -> {
			 o = new OverviewStage();
			o.showOverview("Played Games","Played Games");
		});
		
		
		
		//menü für schwierigkeitsgrad einstellung, standardmäßig ist easy eingestellt
		difficultyMenu = new Menu("Difficulty");
		difficultyToggle = new ToggleGroup();
		easy = new RadioMenuItem(label.getText());
		medium = new RadioMenuItem("Medium");
		hard = new RadioMenuItem("Hard");

		easy.setToggleGroup(difficultyToggle);
		easy.setSelected(true);
		
		medium.setToggleGroup(difficultyToggle);
		hard.setToggleGroup(difficultyToggle);
		difficultyMenu.getItems().addAll(easy, medium, hard);
		menuBar.getMenus().add(difficultyMenu);
		
		menuBar.getMenus().add(newGame);
		
		
		changeGameMode = new Menu("Change Mode");
		sudokuMode = new MenuItem("Sudoku Mode");
		samuraiMode = new MenuItem("Samurai Mode");
		freeFormMode = new MenuItem("FreeForm Mode");
		changeGameMode.getItems().addAll(sudokuMode,samuraiMode,freeFormMode);
		menuBar.getMenus().add(changeGameMode);
		
		
          
}

		VBox backMenu;
		Button back;
		
		MainMenu mainmenu = new MainMenu();

public void createBackButton(BorderPane pane) {
	 	backMenu = new VBox();
	    back = new Button("Main menu");
	 
	//    back.setOnAction(e -> GUI.getStage().setScene(mainmenu.inizializeMainMenu()));
	    back.setOnAction(e -> GUI.getStage().setScene(MainMenu.getMainMenu()));
	    backMenu.getChildren().add(back);
	    backMenu.setAlignment(Pos.CENTER);
	    pane.setBottom(backMenu);
	    back.getStyleClass().add("buttonmainmenu");
	    
	  
}

	

	public Button getBackButton() {
		return back;
	}




public File openFile() {
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Choose a Sudoku");
	File defaultDirectory = new File("d:/sudoku");
	chooser.setInitialDirectory(defaultDirectory);
	Stage window = GUI.getStage();
	File selectedFile = chooser.showOpenDialog(window);
	
	return selectedFile;
}

public long getStartTime() {
	return startTime;
}

public void setStartTime(long startTime) {
	this.startTime = startTime;
}


public abstract SudokuField[][] getTextField();
	

	public abstract Scene getScene();

	
	
}