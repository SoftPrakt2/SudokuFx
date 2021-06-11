package application;

import java.util.stream.Stream;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesStage {
	
	
	protected Scene rulesScene;
	protected VBox rulesContainer;
	protected Label rulesHeader;

	
	protected Label uiDesignDeveloperLabel;
	protected Label sudokuDeveloperLabel;
	protected Label samuraiDeveloperLabel;
	protected Label freeFormDeveloperLabel;
	protected Label versionLabel;
	protected Label usedSoftwareLabel;
	
	
	
	protected FontAwesome fontAwesome;
	
	public void showRulePopUp() {
		Stage window = new Stage();

		window.setTitle("Sudoku Rules");
		
	

		window.setOnCloseRequest(e -> 
			window.close()
		);
		
		
		window.setX(GUI.getStage().getX() + GUI.getStage().getWidth() + 5);
		window.setY(GUI.getStage().getY());
		
		Scene scene = createRulesScene();
		window.setScene(scene);
		window.show();
		
	}
	
	
	
	public Scene createRulesScene() {
		fontAwesome = new FontAwesome();
		
		rulesContainer = new VBox();
		rulesScene = new Scene(rulesContainer,480,200);
		rulesScene.getStylesheets().add(getClass().getResource("/CSS/sudoku.css").toExternalForm());
		rulesContainer.getStyleClass().add("customBackgroundColor");
		
		
		rulesContainer.getChildren().addAll(createGameModeExplanationBox());
		rulesContainer.setSpacing(15);
	
		styleLabels();
		return rulesScene;
	}
	
	
	
	public VBox createGameModeExplanationBox() {
		VBox gameModeExplanationBox = new VBox();
		gameModeExplanationBox.setSpacing(5);
		
		HBox headerBox = new HBox();
		headerBox.setPadding(new Insets(0,0,5,0));
		rulesHeader = new Label("SudokuFx");
		rulesHeader.getStyleClass().add("ruleHeaderLabel");
		headerBox.getChildren().add(rulesHeader);
		
		
		
		HBox versionBox = new HBox();
		versionLabel = new Label("Version: 2021-07");
		versionBox.getChildren().add(versionLabel);
		
		HBox usedSoftwareBox = new HBox();
		usedSoftwareLabel = new Label("Includes Software like JavaFx, GSON and Java");
		usedSoftwareBox.getChildren().add(usedSoftwareLabel);
		
		
		HBox uiDesignBox = new HBox();
		uiDesignDeveloperLabel = new Label("UI Design by Lukas Gruber");
		uiDesignBox.getChildren().add(uiDesignDeveloperLabel);
		
		HBox sudokuSamuraiDeveloperBox = new HBox();
		sudokuDeveloperLabel = new Label("Sudoku und Samurai Game Logic by Rafael Bakaschev and Simon Fuchsluger");
		sudokuSamuraiDeveloperBox.getChildren().add(sudokuDeveloperLabel);
		
	
		
		HBox freeFormDeveloperBox = new HBox();
		freeFormDeveloperLabel = new Label("FreeForm Game Logic by Rafael Bakaschev and Simon Fuchsluger");
		freeFormDeveloperBox.getChildren().add(freeFormDeveloperLabel);
		
		
		
		
		
		
		
		
		gameModeExplanationBox.getChildren().addAll(headerBox,versionBox,usedSoftwareBox,uiDesignBox,sudokuSamuraiDeveloperBox, freeFormDeveloperBox);
		
		return gameModeExplanationBox;
	}
	
	

	

	

	
	
	public void styleLabels() {
		
		Stream.of(versionLabel, usedSoftwareLabel,uiDesignDeveloperLabel, sudokuDeveloperLabel,freeFormDeveloperLabel).forEach(
				label -> label.getStyleClass().add("standardLabel"));
		
		 final SimpleDoubleProperty fontSizeLabelSmall = new SimpleDoubleProperty(10);
		 final SimpleDoubleProperty fontSizeLabelHeader = new SimpleDoubleProperty(10);
		 
		 fontSizeLabelSmall.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(50));
		 fontSizeLabelHeader.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(30));
		 
		 Stream.of(versionLabel, usedSoftwareLabel,uiDesignDeveloperLabel, sudokuDeveloperLabel,freeFormDeveloperLabel).forEach(
					label -> label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabelSmall.asString())));
		 
		 Stream.of(rulesHeader).forEach(
					label -> label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabelHeader.asString())));
	}
	
	
	
	
	
	
	
}
