package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * The CustomColorPicker class is responsible for the creation of a UI object which allows
 * the user to fill a FreeForm playboard with colors when the appropriate
 * gamemode is selected
 * 
 * @author grube
 */

public class CustomColorPicker {

	/**
	 * 
	 * Initializes the UI object ComboBox,which contains a list of predefined colors.
	 * The colors will be shown as dropdown field of rectangles inside the UI
	 * 
	 * @return the created ComboBox
	 */
	public ComboBox<String> createColorPicker() {
		ComboBox<String> cmb = new ComboBox<>();

		ObservableList<String> data = FXCollections.observableArrayList("97c1a9", "cab08b", "dfd8ab", "d5a1a3",
				"80adbc", "adb5be", "eaeee0", "957DAD", "FFDFD3");

		cmb.setItems(data);

		Callback<ListView<String>, ListCell<String>> factory = new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ColorRectCell();
			}
		};
	
		cmb.setCellFactory(factory);

		Callback<ListView<String>, ListCell<String>> factoryTooltip = new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new ColorRectTooltipCell();
			}
		};
		cmb.setButtonCell(factoryTooltip.call(null));

		return cmb;
	}

	static class ColorRectTooltipCell extends ColorRectCell {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				Tooltip.install(this.getParent(), new Tooltip(item));
			}
		}
	}

	/**
	 * Changes the color code strings to rectangles with the corresponding color to ensure a better
	 * usabilty in the game UI.
	 * @author grube
	 *
	 */
	static class ColorRectCell extends ListCell<String> {
		@Override
		/**
		 * colors the rectangles with the corresponding colorcode 
		 */
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Rectangle rect = new Rectangle(10, 10);
			if (item != null) {
				rect.setFill(Color.web(item));
				setGraphic(rect);
			}
		}

	}

}
