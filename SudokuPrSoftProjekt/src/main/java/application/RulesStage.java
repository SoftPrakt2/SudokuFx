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
	protected Label buttonExplanationHeader;
	protected Label difficultyExplanationHeader;
	
	protected Label sudokuLabel;
	protected Label samuraiLabel;
	protected Label freeFormLabel;
	protected VBox buttonExplanations;
	
	
	protected Label checkButtonExplanation;
	protected Label autoSolveExplanation;
	protected Label hintExplanation;
	
	protected Label easyLabel;
	protected Label mediumLabel;
	protected Label hardLabel;
	protected Label manualLabel;
	
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
		rulesScene = new Scene(rulesContainer,400,400);
		rulesScene.getStylesheets().add("css/sudoku.css");
		rulesContainer.getStyleClass().add("customBackgroundColor");
		
		
		rulesContainer.getChildren().addAll(createGameModeExplanationBox(),createButtonExplanationBox(), createDifficultyExplanationBox());
		rulesContainer.setSpacing(15);
	
		alignLabelsWithWindowSize();
		return rulesScene;
	}
	
	
	
	public VBox createGameModeExplanationBox() {
		VBox gameModeExplanationBox = new VBox();
		
		
		HBox rulesHeaderBox = new HBox();
		rulesHeaderBox.setPadding(new Insets(0,0,5,0));
		rulesHeader = new Label("Available Gamemodes");
		rulesHeader.getStyleClass().add("ruleHeaderLabel");
		rulesHeaderBox.getChildren().add(rulesHeader);
		
		HBox sudokuLabelBox = new HBox();
	
		sudokuLabel = new Label("Sudoku: Classic 9x9 Sudoku Puzzle");
		sudokuLabel.getStyleClass().add("standardLabel");
		sudokuLabelBox.getChildren().add(sudokuLabel);
		
		HBox samuraiLabelBox = new HBox();
	
		samuraiLabel = new Label("Samurai: Sudoku Puzzle consisting of five 9x9 Sudoku Puzzles");
		samuraiLabel.getStyleClass().add("standardLabel");
		samuraiLabelBox.getChildren().add(samuraiLabel);
		
		
		HBox freeFormLabelBox = new HBox();
		
		freeFormLabel = new Label("Freeform: 9x9 Sudoku Puzzle with abstract forms as boxes");
		freeFormLabel.getStyleClass().add("standardLabel");
		freeFormLabelBox.getChildren().add(freeFormLabel);
		
		
		gameModeExplanationBox.getChildren().addAll(rulesHeaderBox,sudokuLabelBox,samuraiLabelBox, freeFormLabelBox);
		
		return gameModeExplanationBox;
	}
	
	
	public VBox createButtonExplanationBox() {
		
		Glyph checkGraphic = fontAwesome.create(FontAwesome.Glyph.CHECK);
		Glyph hintGraphic = fontAwesome.create(FontAwesome.Glyph.SUPPORT);
		Glyph autosolveGraphic = fontAwesome.create(FontAwesome.Glyph.CALCULATOR);
		
		buttonExplanations = new VBox();
		buttonExplanations.setPadding(new Insets(15,0,0,0));
		
		
		HBox buttonExplanationHeaderBox = new HBox();
		buttonExplanationHeader = new Label("Button Functionalities");
		buttonExplanationHeader.getStyleClass().add("ruleHeaderLabel");
		buttonExplanationHeaderBox.setPadding(new Insets(0,0,5,0));
		buttonExplanationHeaderBox.getChildren().add(buttonExplanationHeader);
		
		
		HBox checkButtonExplanationBox = new HBox();
		checkButtonExplanationBox.setSpacing(5);
		Button checkButtonrepresentation = new Button();
		checkButtonrepresentation.setMinSize(5,5);
		checkButtonrepresentation.setGraphic(checkGraphic);
		checkButtonExplanation = new Label("Press this Button to see if your Sudoku is correct");
		checkButtonExplanation.getStyleClass().add("standardLabel");
		checkButtonExplanationBox.getChildren().addAll(checkButtonrepresentation, checkButtonExplanation);
		
		
		HBox autoSolveButtonExplanationBox = new HBox();
		autoSolveButtonExplanationBox.setSpacing(5);
		Button autoSolveButtonrepresentation = new Button();
		autoSolveButtonrepresentation.setMinSize(5,5);
		autoSolveButtonrepresentation.setGraphic(autosolveGraphic);
		autoSolveExplanation = new Label("Press this Button to solve your Sudoku automatically");
		autoSolveExplanation.getStyleClass().add("standardLabel");
		autoSolveButtonExplanationBox.getChildren().addAll(autoSolveButtonrepresentation, autoSolveExplanation);
		
		HBox hintButtonExplanationBox = new HBox();
		hintButtonExplanationBox.setSpacing(5);
		Button hintButtonrepresentation = new Button();
		hintButtonrepresentation.setMinSize(5,5);
		hintButtonrepresentation.setGraphic(hintGraphic);
		hintExplanation = new Label("Press this Button to show a hint");
		hintExplanation.getStyleClass().add("standardLabel");
		hintButtonExplanationBox.getChildren().addAll(hintButtonrepresentation, hintExplanation);
		
		buttonExplanations.getChildren().addAll(buttonExplanationHeaderBox, checkButtonExplanationBox, autoSolveButtonExplanationBox, hintButtonExplanationBox);
		
		return buttonExplanations;
	}
	
	
	public VBox createDifficultyExplanationBox() {
		VBox difficultyExplanationBox = new VBox();
		difficultyExplanationBox.setPadding(new Insets(15,0,0,0));
		
		HBox difficultyExplanationHeaderBox = new HBox();
		difficultyExplanationHeaderBox.setPadding(new Insets(0,0,5,0));
		difficultyExplanationHeader = new Label("Available Difficulties");
		difficultyExplanationHeaderBox.getStyleClass().add("ruleHeaderLabel");
		difficultyExplanationHeaderBox.getChildren().add(difficultyExplanationHeader);
		
		HBox easyModeLabelBox = new HBox();
		easyLabel = new Label("Easy: 9x9 Sudokus: 45 Numbers are Shown | Samurai: 189");
		easyLabel.getStyleClass().add("standardLabel");
		easyModeLabelBox.getChildren().add(easyLabel);
		
		HBox mediumModeLabelBox = new HBox();
		mediumLabel = new Label("Medium: 9x9 Sudokus: 35 Numbers are Shown | Samurai: 169");
		mediumLabel.getStyleClass().add("standardLabel");
		mediumModeLabelBox.getChildren().add(mediumLabel);
		
		
		HBox hardModeLabelBox = new HBox();
		hardLabel = new Label("Hard: 9x9 Sudokus: 25 Numbers are Shown | Samurai: 139");
		hardLabel.getStyleClass().add("standardLabel");
		hardModeLabelBox.getChildren().add(hardLabel);
		
		
	
		difficultyExplanationBox.getChildren().addAll(difficultyExplanationHeaderBox, easyModeLabelBox, mediumModeLabelBox, hardModeLabelBox);
		alignLabelsWithWindowSize();
		return difficultyExplanationBox;
	}
	

	
	
	public void alignLabelsWithWindowSize() {

		 final SimpleDoubleProperty fontSizeLabelSmall = new SimpleDoubleProperty(10);
		 final SimpleDoubleProperty fontSizeLabelHeader = new SimpleDoubleProperty(10);
		 
		 fontSizeLabelSmall.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(65));
		 fontSizeLabelHeader.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(40));
		 
		 Stream.of(sudokuLabel, samuraiLabel,freeFormLabel,checkButtonExplanation,autoSolveExplanation,hintExplanation,easyLabel, mediumLabel, hardLabel).forEach(
					label -> label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabelSmall.asString())));
		 
		 Stream.of(rulesHeader,buttonExplanationHeader,difficultyExplanationHeader).forEach(
					label -> label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabelHeader.asString())));
		 
	}
	
	
	
	
	
	
	
}
