package application;


import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class SudokuField extends TextField{
	
	
	public SudokuField(String txt) {
		super(txt);
		addListener();
		onlyOneNumber();
		enterPressed();
	}

	 private void addListener() {
	        this.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (!newValue.matches("\\d*")) {
	                this.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        });
	    }
	 
	 
	 
	 public void onlyOneNumber() {
		 this.textProperty().addListener(
			        (observable,oldV,newV)-> {
			            if(newV.length() > 1) this.setText(oldV);
			        }
			);
	 }
	 
	public void enterPressed() {
		this.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				System.out.println(this.getText());
			}
	});
	 
	 
	}
	
	public void updateText() {
		this.textProperty().addListener((observable,oldV,newV) -> 
		this.setText(newV));
	};
		
		
		
	


	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

