package application;



import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class OverviewStage {
	
	
	Stage window;
	Scene overview;
	BorderPane pane;
	
	TableView<File> tableview = new TableView<>();
	TableColumn<File, String> col = new TableColumn<>("name");
	
	ObservableList<File> fileList = FXCollections.observableArrayList();
	
	
	
	
	
	
	public Stage showOverview(String title, String message) {
		window = new Stage();
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5,5,5,50));
		
		window.setTitle(title);
		window.setWidth(500);
		pane = new BorderPane();
		overview = new Scene(layout,500,500);
		window.setScene(overview);
		
		
		
		
		Label label = new Label("Please select a savegame");
		
		
		
		Label chosenLabel = new Label("Bitte wähle einen Spielstand");
		
		Button load = new Button("Load");
		load.setOnAction(e -> openFileChooser());
		
		pane.setCenter(tableview);
			
		String s = "sfsdf";
		
	
		
		layout.getChildren().addAll(label, tableview,load);
		
		
		
		col.setCellValueFactory(new PropertyValueFactory<>("name")); 
		tableview.setItems(fileList);
	
			
		return window;
	}
	
	
	
	
	

	
	

	
	
	
	
	public void openFileChooser() {
		FileChooser fc = new FileChooser();
		File defaultDirectory = new File("C:/users/grube/Desktop/sudoku");
		fc.setInitialDirectory(defaultDirectory);
	
		
		fc.getExtensionFilters().addAll(
	  
		new FileChooser.ExtensionFilter("TXT", "*.txt")
		
		
	
		);
        
	//	tableview.getItems().add(defaultDirectory);
	
		Stage window = GUI.getStage();
		File selectedFile = fc.showOpenDialog(window);
		System.out.println(selectedFile.getName());
		
		fileList.add(selectedFile);
		
        }
	
	
}
	
	

