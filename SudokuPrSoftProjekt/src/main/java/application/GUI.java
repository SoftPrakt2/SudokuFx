package application;
	
import java.util.stream.Stream;

import javafx.application.Application;



import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GUI extends Application  {

  static Stage window;
  
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage scene) {
		
		 
		window = scene;
		 
		 window.setScene(new MainMenu().setUpMainMenu());	
		 
		 window.show();
	
	 
	    window.setOnCloseRequest(e-> {
			//consume heißt w führen den schließvorgang fort und es wird nicht immer geschlossen nur wenn yes gedrückt wird
			e.consume();
			closeProgram();
		});
	    
	}
	    //this is test circle
	   
	    private void closeProgram() {
	    	CloseWindowStage c = new CloseWindowStage();
			Boolean answer = c.showPopUp("Closing","Are you sure that you want to close the program?");
			if(answer) window.close();
			
		}    
	    
	    
	    public static Stage getStage() {
	    	return window;
	    }
	    
	   
	
	    
	    
	   
	}
	
	
	