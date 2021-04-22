package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class GUI extends Application  {

  static Stage window;
  static MainMenu mainMenu = new MainMenu();
  static Scene mainMenuScene = mainMenu.setUpMainMenu();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage scene) {
		
		 
		window = scene;
		 scene.setMaxWidth(1500);
		 scene.setMaxHeight(1500);
		
		
		 window.setScene(new MainMenu().setUpMainMenu());	
		 
		 window.show();
	
	 
	    window.setOnCloseRequest(e-> {
			//consume hei�t  f�hre den schlie�vorgang fort und es wird nicht immer geschlossen nur wenn yes gedr�ckt wird
			e.consume();
			closeProgram();
		});
	    
	}
	    
		
	   
	    private void closeProgram() {
	    	CloseWindowStage c = new CloseWindowStage();
			Boolean answer = c.showPopUp("Closing","Are you sure that you want to close the program?");
			if(answer) window.close();
			
		}    
	    
	    
	    public static Stage getStage() {
	    	return window;
	    }
	    
	    
	    public static MainMenu getMainMenu() {
	    	return mainMenu;
	    }
	
	    
	    	
	   
	}
	
	
	