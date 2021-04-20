package application;
	
import javafx.application.Application;
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
			//consume heißt w führe den schließvorgang fort und es wird nicht immer geschlossen nur wenn yes gedrückt wird
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
	    
	 
	   
	
	    
	    
	   
	}
	
	
	