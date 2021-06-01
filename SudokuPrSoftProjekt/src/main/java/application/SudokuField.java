package application;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class SudokuField extends TextField {

	private boolean playable = true;
	private String color;

	public SudokuField(String txt) {
		super(txt);
		addListener();
		onlyOneNumber();
		enterPressed();
		updateColor();
		this.getStyleClass().add("textfieldBasic");
	}

	private void addListener() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}

	public void onlyOneNumber() {
		this.textProperty().addListener((observable, oldV, newV) -> {
			if (newV.length() > 1)
				this.setText(oldV);
		});
	}

	public void enterPressed() {
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				System.out.println(this.getText());
			}
		});

	}

	public void updateText() {
		this.textProperty().addListener((observable, oldV, newV) -> this.setText(newV));
	};

	public void updateColor() {
		this.textProperty().addListener((observable, oldV, newV) ->
		this.getStyleClass().remove("textfieldWrong"));
	};
	
	
	public void addFreeFormColorListener(ComboBox<Color> cmb) {
		this.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (isNowFocused) {
				this.setColor(cmb.getValue().toString().substring(2));
				this.setStyle("-fx-background-color: #" + cmb.getValue().toString().substring(2));
				System.out.println(this.getColor());
			}
		});
	}
	
	

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public boolean getPlayable() {
		return playable;
	}

}
