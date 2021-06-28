package application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * This class is an extension of the JavaFx TextField SudokuTextFields are enhanced
 * with several listeners to be better suitable for the purpose of a sudokugame
 * 
 * @author grube
 *
 */
public class SudokuTextField extends TextField {

	/**
	 * color of a SudokuField
	 */
	private String color = "";

	/**
	 * variable which defines if a SudokuField is currently listening to color
	 * background changes
	 */
	private boolean listeningToColors;

	/**
	 * defines if a SudokuTextField is currently colored
	 */
	private boolean isColored;

	private ChangeListener<Boolean> freeFormColorListener;

	/**
	 * Constructor of a SudokuTextfield, all listener are injected to an object when its initialized
	 * @param txt from the super TextField class
	 */
	public SudokuTextField(String txt) {
		super(txt);
		addOnlyNumbers();
		onlyOneNumber();
		updateColor();
		
		shortcutFriendlyTextField();
		listeningToColors = false;
	

	}

	/**
	 * Adds a listener to the Sudokufields textproperty to ensure that only numbers
	 * from 1-9 are insertable to a SudokuField
	 */
	public final void addOnlyNumbers() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.setText(newValue.replaceAll("[^\\d]", ""));

			}
			if (newValue.equals("0")) {
				this.setText(newValue.replace("0", ""));

			}
		});
	}

	/**
	 * Adds a listener to ensure that only one digit is insertable in a SudokuField
	 */
	public final void onlyOneNumber() {
		this.textProperty().addListener((observable, oldV, newV) -> {
			if (newV.length() > 1)
				this.setText(oldV);
		});
	}

	/**
	 * Adds a listener to ensure that the Value of the SudokuFields textproperty is
	 * updated
	 */
	public final void updateText() {
		this.textProperty().addListener((observable, oldV, newV) -> this.setText(newV));
	}

	/**
	 * Adds a listener to ensure that the color of a SudokuFields textproperty is
	 * black after inserting a new number
	 */
	public final void updateColor() {
		this.textProperty().addListener((observable, oldV, newV) -> {

			this.getStyleClass().remove("textfieldWrong");
			this.getStyleClass().remove("textfieldHint");

		});
	}

	/**
	 * application.BasicGameBuilder#getColorBox() This method is needed to ensure
	 * that the Shortcuts which are defined in
	 * {@link application.BasicGameBuilder#defineShortCuts()} work when the user is
	 * inside a SudokuField Without this method the SudokuField does not respond to
	 * the Shortcuts
	 */
	public final void shortcutFriendlyTextField() {

		this.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// handle shortcuts if defined
				if (!event.getCode().isModifierKey()) {
					Consumer<KeyCombination.Modifier[]> runAccelerator = modifiers -> {
						Runnable r = getScene().getAccelerators()
								.get(new KeyCodeCombination(event.getCode(), modifiers));
						if (r != null) {
							r.run();
						}
					};
					List<KeyCombination.Modifier> modifiers = new ArrayList<>();
					if (event.isControlDown())
						modifiers.add(KeyCodeCombination.SHORTCUT_DOWN);
					if (event.isShiftDown())
						modifiers.add(KeyCodeCombination.SHIFT_DOWN);
					if (event.isAltDown())
						modifiers.add(KeyCodeCombination.ALT_DOWN);

					runAccelerator.accept(modifiers.toArray(new KeyCombination.Modifier[modifiers.size()]));
				}
			}
		});
	}

	/**
	 * This method ensures that SudokuFields have the ability to receive the
	 * selected color of a {@link application.CustomColorPicker#cmb} and change the
	 * background color of itself according to the selected color in the Colorpicker
	 * 
	 * @param cmb
	 */
	public final void addFreeFormColorListener(ComboBox<String> cmb) {
		freeFormColorListener = (obs, wasFocused, isNowFocused) -> {
			if (isNowFocused == true && cmb != null && cmb.getValue() != null) {
				this.setColor(cmb.getValue());
			}
		};

		this.focusedProperty().addListener(freeFormColorListener);
		listeningToColors = true;
	}

	/**
	 * Removes the listener added to a SudokuField from the method
	 * {@link #addFreeFormColorListener(ComboBox)}
	 */
	public void removeFreeFormColorListener() {
		this.focusedProperty().removeListener(freeFormColorListener);
		listeningToColors = false;
	}

	/**
	 * Sets the background color of a sudokutextfield
	 * 
	 * @param color of the background
	 */
	public void setColor(String color) {
		this.color = color;
		if (!color.equals("")) {
			this.setStyle("-fx-background-color: #" + color);
			isColored = true;
		} else {
			isColored = false;
		}

	}

	public String getColor() {
		return color;
	}

	public boolean isListeningToColors() {
		return listeningToColors;
	}

	public boolean isColored() {
		return isColored;
	}

}
