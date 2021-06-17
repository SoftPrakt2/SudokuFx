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
 *  CloseWindowStage is used to generate a PopUp which asks the user if he/she wants to close the program
 *  @author grube
 *
 */
public class CloseWindowStage {
	
	/**
	 * Answer of the user if he wants to close the program
	 */
	private boolean closingAnswer;
	
	
	private Button yesButton;
	private Button noButton;
	private Stage window;
	
	
	
	/**
	 * Creates a Stage which displays the closing request
	 */
	 public void showPopUp() {
			window = new Stage();
			
			window.setX(GUI.getStage().getX() + GUI.getStage().getWidth() + 5);
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
	  * Defines the actions for the {@link #yesButton} and {@link #noButton}
	  */
	 public void setClosingAnswer() {
		 yesButton.setOnAction(e -> {
			 closingAnswer = true;
				window.close();
			});
			
			noButton.setOnAction(e -> {
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


