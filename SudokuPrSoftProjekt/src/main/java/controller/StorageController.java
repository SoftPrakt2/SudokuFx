package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.GameOverview;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import application.SudokuTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SaveModel;
import logic.SudokuStorage;

/**
 * This class is the controller for the {@link application.GameOverview} and
 * {@link logic.SudokuStorage} class it handles the output shown in the table
 * view of the {@link application.GameOverview} class and actions within and
 * regarding this tableview
 * 
 * @author grube
 *
 */
public class StorageController {

	private BasicGameBuilder gameBuilder;
	private BasicGameLogic gameModel;
	private SudokuStorage storageModel;
	private GameOverview storage;
	private File[] fileDirectory = new File("SaveFiles").listFiles();

	/**
	 * The ObservableList associated with the
	 * {@link application.GameOverview#getTableView()} this list contains
	 * {@link application.BasicGameLogic} objects
	 * BasicGameLogic objects are stored in this list to allow the use of polymorphism later on when determing the
	 * exact gametype
	 */
	private ObservableList<BasicGameLogic> jsonObservableList;

	/**
	 * The different table columns of the
	 * {@link application.GameOverview#getTableView()} which will later be filled
	 * with informations from saved games
	 * 
	 */
	private TableColumn<BasicGameLogic, String> gameTypecolumn;
	private TableColumn<BasicGameLogic, String> difficultycolumn;
	private TableColumn<BasicGameLogic, Integer> pointscolumn;
	private TableColumn<BasicGameLogic, String> playtimecolumn;
	private TableColumn<BasicGameLogic, Gamestate> gamestatecolumn;
	private TableColumn<BasicGameLogic, Integer> gameidcolumn;

	
	public StorageController(GameOverview storage) {
		this.storage = storage;
		storageModel = new SudokuStorage();
	}

	/**
	 * This method is used to load a saved Sudoku game into a GameUI 
	 * Depending on the exact type of the {@link #gameModel} object the corresponding GameBuilder
	 * will be initialized 
	 * Depending on the gamestate of {@link #gameModel} different UI objects have to be enabled or disabled
	 * 
	 * @param e action of the user in the UI
	 */
	public void handleLoadAction(ActionEvent e) {

		if(storage.getTableView().getSelectionModel().getSelectedItem() != null) {
		
		gameModel = storage.getTableView().getSelectionModel().getSelectedItem();

		if (gameModel.getGametype().equals("Sudoku")) {
			gameBuilder = new SudokuGameBuilder(gameModel);
		}
		if (gameModel.getGametype().equals("Samurai")) {
			gameBuilder = new SamuraiGameBuilder(gameModel);
		}
		if (gameModel.getGametype().equals("FreeForm")) {
			gameBuilder = new FreeFormGameBuilder(gameModel);
		}

			
		
		gameBuilder.initializeGame();

		if (!gameModel.getGamestate().equals(Gamestate.CREATING) && !gameModel.getGamestate().equals(Gamestate.DRAWING)
				&& !gameModel.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !gameModel.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)
				&& !gameModel.getGamestate().equals(Gamestate.NOFORMS)) {
				
			if(gameModel.getGamestate().equals(Gamestate.AUTOSOLVED) || gameModel.getGamestate().equals(Gamestate.DONE)) {
				gameBuilder.getLiveTimeLabel().setText(String.format("%02d:%02d", gameModel.getMinutesplayed(),gameModel.getSecondsplayed()));
			} else {
			
			gameModel.initializeTimer();
			gameModel.getLiveTimer().start();
			gameBuilder.getLiveTimeLabel().textProperty().bind(Bindings.concat(gameModel.getTimeProperty()));
			}
			gameBuilder.getGameInfoLabel().setText("Points: " + gameModel.getGamepoints() + " Difficulty: " + gameModel.getDifficultystring());
		}

		if (gameModel.getGamestate().equals(Gamestate.CREATING)
				|| gameModel.getGamestate().equals(Gamestate.MANUALCONFLICT)
				|| gameModel.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)) {
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
			gameBuilder.getToolBar().getItems().add(3, gameBuilder.getCustomNumbersDone());
			gameBuilder.getCustomNumbersDone().setVisible(true);
			gameBuilder.disablePlayButtons();

		}

		if (gameModel.getGamestate().equals(Gamestate.DRAWING) || gameModel.getGamestate().equals(Gamestate.NOFORMS)) {
			gameBuilder.getColorBox().setVisible(true);
			gameBuilder.getColorsDoneButton().setVisible(true);
			gameBuilder.disablePlayButtons();
		}

		gameBuilder.getGameNotificationLabel().setText(gameModel.getGameText());

		alignArrays();
		alignWindowToGameSize();
		}
	}

	/**
	 * This method initializes the different table columns and adds them to the
	 * {@link application.GameOverview#getTableView()}
	 */
	@SuppressWarnings("unchecked")
	public void setUpTableView() {
		gameTypecolumn = new TableColumn<>("GameType");
		difficultycolumn = new TableColumn<>("Difficulty");
		pointscolumn = new TableColumn<>("Points");
		playtimecolumn = new TableColumn<>("PlayTime");
		gamestatecolumn = new TableColumn<>("Gamestate");
		gameidcolumn = new TableColumn<>("GameID");
		storage.getTableView().getColumns().addAll(gameidcolumn, gameTypecolumn, difficultycolumn, pointscolumn,
				playtimecolumn, gamestatecolumn);
		gameTypecolumn.setSortable(false);
		difficultycolumn.setSortable(false);
		pointscolumn.setSortable(false);
		playtimecolumn.setSortable(false);
		gamestatecolumn.setSortable(false);
		gameidcolumn.setSortable(false);
	}

	/**
	 * This method fills the {@link application.GameOverview#getTableView()} with
	 * informations from the saved Files in the specified directory 
	 * To do so the directory is iterated through and each file in it will be converted to an
	 * object of the {@link logic.SaveModel} class 
	 * Afterwards the informations of the SaveModel object will be loaded into the {@link #gameModel} object which
	 * is an object of {@link application.BasicGameLogic} class
	 */
	public void fillTableVew() {
		jsonObservableList = FXCollections.observableArrayList();

		SaveModel savedGame;

		if (fileDirectory != null) {
			for (File child : fileDirectory) {
				if (child.getName().endsWith(".json")) {
					savedGame = storageModel.convertFileToSaveModel(child.getAbsoluteFile());
					gameModel = storageModel.loadIntoModel(gameModel, savedGame);
					jsonObservableList.add(gameModel);
				}
			}
			gameTypecolumn.setCellValueFactory(new PropertyValueFactory<>("gametype"));
			difficultycolumn.setCellValueFactory(new PropertyValueFactory<>("difficultystring"));
			pointscolumn.setCellValueFactory(new PropertyValueFactory<>("gamepoints"));
			playtimecolumn.setCellValueFactory(new PropertyValueFactory<>("playtimestring"));
			gamestatecolumn.setCellValueFactory(new PropertyValueFactory<>("gamestate"));
			gameidcolumn.setCellValueFactory(new PropertyValueFactory<>("gameid"));

		}
		storage.getTableView().setItems(jsonObservableList);
		calculateGameStats();
	}

	/**
	 * This method is used to delete an object from the {@link #jsonObservableList}
	 * and the file directory
	 * 
	 * @param action of the user in the UI
	 */
	public void deleteEntry(ActionEvent e) {

		int deleteIndex = storage.getTableView().getSelectionModel().getSelectedIndex();
		if(deleteIndex >= 0) {
		jsonObservableList.remove(deleteIndex);

		if (fileDirectory[deleteIndex].exists()) {
			try {

				Files.delete(fileDirectory[deleteIndex].toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		fileDirectory = new File("SaveFiles").listFiles();
		}
	}

	/**
	 * This method is used to align the values which are shown in the
	 * {@link application.BasicGameBuilder#getTextField()} with the values inside
	 * the {@link #gameModel} {@link application.BasicGameLogic#getCells()} array
	 */
	public void alignArrays() {
		SudokuTextField[][] sudokuField = gameBuilder.getTextField();

		
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (gameModel instanceof FreeFormLogic && !gameModel.getCells()[j][i].getBoxcolor().equals("")) {
					sudokuField[i][j].setColor(gameModel.getCells()[j][i].getBoxcolor());
				}
				if (gameModel.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(gameModel.getCells()[j][i].getValue()));
				}

				if (gameModel.getCells()[j][i].getFixedNumber() && gameModel.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setDisable(true);
					sudokuField[i][j].getStyleClass().remove("textfieldBasic");
					sudokuField[i][j].getStyleClass().add("textfieldLocked");
				
				}
				

				else if (gameModel.getCells()[j][i].isHint() && gameModel.getCells()[j][i].getValue() != 0) {
					
					
					sudokuField[i][j].getStyleClass().add("textfieldHint");
					sudokuField[i][j].setDisable(true);
				}

			}
		}
		// depending on the game state a solution of the game should be created in the
		// background
		if (!gameModel.getGamestate().equals(Gamestate.CREATING) && !gameModel.getGamestate().equals(Gamestate.DRAWING)
				&& !gameModel.getGamestate().equals(Gamestate.NOFORMS)
				&& !gameModel.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !gameModel.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)
				)
			gameModel.setSavedResults(gameModel.createBackgroundSolution());
	}
	
	

	

	/**
     * This method is used to align the programs window size to the size variables
     * defined in the {@link #gameBuilder} object
     */
    public void alignWindowToGameSize() {
         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            GUI.getStage().setHeight(gameBuilder.getHeight());
            GUI.getStage().setWidth(gameBuilder.getWidth());
            GUI.getStage().getScene().setRoot(gameBuilder.getGameUIRoot());

             GUI.getStage().setX((screenBounds.getWidth() - gameBuilder.getSceneWidth()) / 2);
             GUI.getStage().setY((screenBounds.getHeight() - gameBuilder.getSceneHeight()) / 2);
        storage.getStage().close();
    }

	/**
	 * This method is used to calculate the different game results shown in the
	 * {@link application.GameOverview} window 
	 * The results are depended on the content in the {@link application.GameOverview#getTableView()} thus for each
	 * result a binding is created which contains the specific result of the calculation 
	 * Lastly each binding is then bound to the {@link application.GameOverview} specific label for the result
	 */
	public void calculateGameStats() {

		IntegerBinding totalGamePoints = Bindings.createIntegerBinding(() -> {
			int total = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				total = total + savedModel.getGamepoints();
			}
			return total;
		}, storage.getTableView().getItems());

		IntegerBinding averagePoints = Bindings.createIntegerBinding(() -> {
			int total = 0;
			int counter = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				total = total + savedModel.getGamepoints();
				counter++;
			}
			if (counter == 0) {
				counter = 1;
			}
			return Math.floorDiv(total, counter);
		}, storage.getTableView().getItems());

		StringBinding overAllPlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				playTime += savedModel.getMinutesplayed() * 60 + savedModel.getSecondsplayed();
			}

			long minPlayed = playTime / 60;
			long secPlayed = playTime % 60;

			time = String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());

		StringBinding averagePlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			float counter = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				playTime += savedModel.getMinutesplayed() * 60 + savedModel.getSecondsplayed();
				counter++;
			}
			if (counter == 0)
				counter = 1;
			playTime = (long) (playTime / counter);
			long minPlayed = playTime / 60;
			long secPlayed = playTime % 60;

			time = String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());

		storage.getOverallPointsLabel().textProperty().bind(totalGamePoints.asString());
		storage.getAveragePointsResultLabel().textProperty().bind(averagePoints.asString());
		storage.getOverallTimeResultLabel().textProperty().bind(overAllPlayTime);
		storage.getAverageTimeResultLabel().textProperty().bind(averagePlayTime);
	}

}
