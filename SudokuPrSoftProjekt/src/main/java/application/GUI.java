package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GUI extends Application  {

  static Stage window;


 static Scene mainScene;
 MainMenu mainmenu = new MainMenu();
 

	
  //this is just a test
  
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage scene) {
		
		//test commit 
		window = scene;
		 scene.setMaxWidth(1500);
		 scene.setMaxHeight(1500);
		 mainScene = mainmenu.setUpMainMenu();
				 window.setScene(mainScene);	
		 
			 window.show();
	
		 window.setTitle("SudokuFx");
	    window.setOnCloseRequest(e-> {
			//consume heißt  führe den schließvorgang fort und es wird nicht immer geschlossen nur wenn yes gedrückt wird
			e.consume();
			closeProgram();
		});
	    
	}
	    
	public static void closeProgram() {
	    	CloseWindowStage c = new CloseWindowStage();
			Boolean answer = c.showPopUp("Closing","Are you sure that you want to close the program?");
			if(answer) window.close();
		}    
	    
	    
	    public static Stage getStage() {
	    	return window;
	    }
	    
	    
	    public static Scene getMainMenu() {
	    	return mainScene;
	    }
	
	    
	    
	    
	    
	   
	}
	