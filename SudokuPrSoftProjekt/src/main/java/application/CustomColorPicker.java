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

public class CustomColorPicker {
	

	protected ComboBox<String> cmb;

	
	//erstellt einen ColorPicker mit vordefinierten Farben
	public ComboBox<String> createColorPicker() {
	cmb = new ComboBox<>();
	
     ObservableList<String> data = FXCollections.observableArrayList(
           "B8860B");

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


  static class ColorRectCell extends ListCell<String>{
       @Override
       public void updateItem(String item, boolean empty){
           super.updateItem(item, empty);
           Rectangle rect = new Rectangle(10,10);
           if(item != null){
               rect.setFill(Color.web(item));
               setGraphic(rect);
       }
   }
	
  }
	
}
