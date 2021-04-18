package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RulesStage {
	
	public void showPopUp(String title) {
		Stage window = new Stage();

		String rules = "\r\nThe objective is: to complete a sudoku grid by filling in missing digits from 1 to 9, \r\n"
				+ "without using the numbers more than once in each row, column and grid.\r\n"
				+ "\r\n"
				+ "Rules:\r\n"
				+ "1. Only use the numbers 1 - 9 \r\n"
				+ "2. Only use each number once in each row, columna and grid \r\n";		
		
		// kann hinteres windows nicht klicken
		window.setTitle(title);
		window.setMinWidth(500);
		Label label = new Label();
		label.setText(rules);

		 window.setOnCloseRequest(e-> {
				window.close();
			});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
