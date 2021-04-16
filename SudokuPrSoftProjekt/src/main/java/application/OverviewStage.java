package application;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class OverviewStage {
	
	
	Stage window;
	Scene overview;
	BorderPane pane;
	
	
	
	
	public void showOverview(String title, String message) {
		window = new Stage();
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5,5,5,50));
		
		window.setTitle(title);
		window.setWidth(500);
		pane = new BorderPane();
		overview = new Scene(layout,500,500);
		window.setScene(overview);
		
		
		
		
		Label label = new Label("Please select a savegame");
		ListView listView = new ListView<>();
		listView.setMaxSize(300,300);
		
		ObservableList games = FXCollections.observableArrayList("Sudoku Difficulty: Easy","Samurai Difficulty: Medium","Freeform Difficulty: Hard");
		
		listView.setItems(games);
		listView.setOrientation(Orientation.VERTICAL);
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		
		Label chosenLabel = new Label("Bitte wähle einen Spielstand");
		
		Button load = new Button("Load");
		
		pane.setCenter(listView);
			
		
		
	
		layout.getChildren().add(label);
		layout.getChildren().addAll(listView,load);
		
		
		
		
		
		
		
		
		
		window.show();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
