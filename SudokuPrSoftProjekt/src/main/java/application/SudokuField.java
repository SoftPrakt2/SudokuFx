package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class SudokuField extends TextField {

	private boolean playable = true;
	private String color = "";
	private boolean listeningToColors;
	
	ChangeListener<Boolean> freeFormColorListener;
	
	

	public SudokuField(String txt) {
		super(txt);
		addListener();
		onlyOneNumber();
		enterPressed();
		updateColor();
		ShortcutFriendlyTextField();
		listeningToColors = false;
		this.getStyleClass().add("textfieldBasic");
	}

	private void addListener() {
		
		
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.setText(newValue.replaceAll("[^\\d]", ""));
			}
			if(newValue.equals("0")) {
				this.setText(newValue.replace("0", ""));
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
		this.getStyleClass().remove("textfieldHint");
	};
	
	

	public void ShortcutFriendlyTextField() {
        
        this.addEventHandler(KeyEvent.KEY_RELEASED,new EventHandler<KeyEvent>() {

        	 @Override
	            public void handle(KeyEvent event) {
	                //handle shortcuts if defined
	            	if (!event.getCode().isModifierKey()) {
	            	    Consumer<KeyCombination.Modifier[]> runAccelerator = (modifiers) -> {
	            	        Runnable r = getScene().getAccelerators().get(new KeyCodeCombination(event.getCode(), modifiers));
	            	        if (r != null) {
	            	            r.run();
	            	        }
	            	    };

	            	    List<KeyCombination.Modifier> modifiers = new ArrayList<>();
	            	    if (event.isControlDown()) modifiers.add(KeyCodeCombination.SHORTCUT_DOWN);
	            	    if (event.isShiftDown()) modifiers.add(KeyCodeCombination.SHIFT_DOWN);
	            	    if (event.isAltDown()) modifiers.add(KeyCodeCombination.ALT_DOWN);
	          
	            	    runAccelerator.accept(modifiers.toArray(new KeyCombination.Modifier[modifiers.size()]));
	            	}
	            }
        });
    }
	      
	        
	

//	public void addFreeFormColorListener(ComboBox<Color> cmb) {
//		this.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
//			if (isNowFocused) {
//				this.setColor(cmb.getValue().toString().substring(2));
//				this.setStyle("-fx-background-color: #" + cmb.getValue().toString().substring(2));
//				System.out.println(this.getColor());
//			}
//		});
//	}
	
	public void addFreeFormColorListener(ComboBox<String> cmb) {
		freeFormColorListener = (obs, wasFocused, isNowFocused) -> {
			if (isNowFocused && cmb != null && cmb.getValue()!=null) {
			this.setColor(cmb.getValue());
			System.out.println(cmb.getValue() + "gui");
			
		//	this.setStyle("-fx-background-color: #" + cmb.getValue().toString().substring(2));
			
			}
		};
		
		this.focusedProperty().addListener(freeFormColorListener);
		listeningToColors = true;
	}
	
	
	public void removeFreeFormColorListener() {
		this.focusedProperty().removeListener(freeFormColorListener);
		listeningToColors = false;
	}
	
	
	

	public void setPlayable(boolean playable) {
		this.playable = playable;
	}

	public void setColor(String color) {
		this.color = color;
		this.setStyle("-fx-background-color: #" + color);
	}

	public String getColor() {
		return color;
	}

	public boolean getPlayable() {
		return playable;
	}
	
	public boolean isListeningToColors() {
		return listeningToColors;
	}

}
