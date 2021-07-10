package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 *  CloseWindowStage is used to generate a pop up which asks the user if he/she wants to close the program
 *  @author gruber
 *
 */
public class CloseWindowStage {
	
	/**
	 * answer of the user if he/she wants to close the program
	 */
	private boolean closingAnswer;
	
	
	private Button yesButton;
	private Button noButton;
	private Stage window;
	
	
	/**
	 * creates a stage which displays the closing question
	 */
	 public void showPopUp() {
			window = new Stage();
			
			window.setX(GUI.getStage().getX()  + GUI.getStage().getWidth()-260);
			window.setY(GUI.getStage().getY());
			
			window.setTitle("Closing");
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(260);
			window.setResizable(false);
			
			Label label = new Label();
			label.setText("Do you want to close the program?");
			yesButton = new Button("Yes");
			noButton = new Button("No");
			setClosingAnswer();
			
			
			VBox layout = new VBox(10);
			layout.getChildren().addAll(label, yesButton, noButton);
			layout.setPadding(new Insets(0,0,5,0));
			layout.setAlignment(Pos.CENTER);
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.showAndWait();
			
			
			
		}
	
	 /**
	  * defines the actions for the {@link #yesButton} and {@link #noButton}
	  */
	 public void setClosingAnswer() {
		 yesButton.setOnAction(e -> {
			 closingAnswer = true;
			 	//close the popup
				window.close();
			});
			
			noButton.setOnAction(e -> {
				//close the popup
				closingAnswer = false;
				window.close();	
			});
	 }
	 
	 /**
	  * @return the answer of the user if he/she wants to close the program
	  */
	 public boolean getClosingAnswer() {
		 return closingAnswer;
	 }
	 
	 
	 
}


