package application;

import java.util.stream.Stream;

import org.controlsfx.glyphfont.FontAwesome;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is used to show a little PopUp in the UI displaying information
 * about the programm and its developers
 * 
 * @author gruber
 *
 */
public class AboutStage {

	private Scene rulesScene;
	

	/**
	 * Labels of this scene
	 */
	private Label rulesHeader;
	private Label uiDesignDeveloperLabel;
	private Label sudokuDeveloperLabel;
	private Label freeFormDeveloperLabel;
	private Label versionLabel;
	private Label usedSoftwareLabel;

	protected FontAwesome fontAwesome;
	
	/**
	 * This method is used to instantiate a Stage object 
	 * and show it in the UI
	 */
	public void showRulePopUp() {
		Stage window = new Stage();

		window.setTitle("Sudoku Rules");

		window.setOnCloseRequest(e -> window.close());

		window.setX(GUI.getStage().getX() + GUI.getStage().getWidth() + 5);
		window.setY(GUI.getStage().getY());

		Scene scene = createRulesScene();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * This method initializes a scene which is filled with UI Objects generated in the
	 * {@link #createGameModeExplanationBox()} method
	 * @return the created scene 
	 */
	public Scene createRulesScene() {
		fontAwesome = new FontAwesome();

		VBox rulesContainer = new VBox();
		rulesScene = new Scene(rulesContainer, 480, 200);
		rulesScene.getStylesheets().add(getClass().getResource("/CSS/sudoku.css").toExternalForm());
		rulesContainer.getStyleClass().add("customBackgroundColor");

		rulesContainer.getChildren().addAll(createGameModeExplanationBox());
		rulesContainer.setSpacing(15);

		styleLabels();
		return rulesScene;
	}
	
	/**
	 * This method is used to instantiate the different labels of this class
	 * To ensure correct positioning and scaling of the UI objects with the scene
	 * each label is put into its own Hbox
	 * @return a VBox which contains the UI objects
	 * this returned VBox is the main container of this scene
	 */
	public VBox createGameModeExplanationBox() {
		VBox gameModeExplanationBox = new VBox();
		gameModeExplanationBox.setSpacing(5);

		HBox headerBox = new HBox();
		headerBox.setPadding(new Insets(0, 0, 5, 0));
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

		gameModeExplanationBox.getChildren().addAll(headerBox, versionBox, usedSoftwareBox, uiDesignBox,
				sudokuSamuraiDeveloperBox, freeFormDeveloperBox);

		return gameModeExplanationBox;
	}
	
	
	/**
	 * This method is used to style the different labels with a css styleclass
	 * Furthermore the size of the label will be bound to the scenes width and heightproperties
	 */
	public void styleLabels() {
		Stream.of(versionLabel, usedSoftwareLabel, uiDesignDeveloperLabel, sudokuDeveloperLabel, freeFormDeveloperLabel)
				.forEach(label -> label.getStyleClass().add("standardLabel"));

		final SimpleDoubleProperty fontSizeLabelSmall = new SimpleDoubleProperty(10);
		final SimpleDoubleProperty fontSizeLabelHeader = new SimpleDoubleProperty(10);

		fontSizeLabelSmall.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(50));
		fontSizeLabelHeader.bind(rulesScene.widthProperty().add(rulesScene.heightProperty()).divide(30));

		Stream.of(versionLabel, usedSoftwareLabel, uiDesignDeveloperLabel, sudokuDeveloperLabel, freeFormDeveloperLabel)
				.forEach(label -> label.styleProperty()
						.bind(Bindings.concat("-fx-font-size: ", fontSizeLabelSmall.asString())));

		Stream.of(rulesHeader).forEach(label -> label.styleProperty()
				.bind(Bindings.concat("-fx-font-size: ", fontSizeLabelHeader.asString())));
	}
	

}
