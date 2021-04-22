package controller;

import application.GUI;
import application.MainMenu;
import javafx.event.ActionEvent;

public abstract class BasicController {
	
	
	
	
	public abstract void createGameHandler(ActionEvent e);
	
	public abstract void enableEditHandler(ActionEvent e);
	
	public abstract void easyHandler(ActionEvent e);
	
	public abstract void mediumHandler(ActionEvent e);
	
	public abstract void hardHandler(ActionEvent e);
	
	public abstract void newGameHandler(ActionEvent e);
	
	public abstract void checkHandler(ActionEvent e);
	
	public abstract void saveHandler(ActionEvent e);
	
	public abstract void autoSolveHandler(ActionEvent e);
	
	public abstract void setDifficulty(int difficulty);
	
	
	
	
	public void switchToMainMenu(ActionEvent e) {
		
		
		//so bleibt das spiel in der scene wenn man einmal auf hauptmenü geht und dann wieder zurück
		GUI.getStage().setScene(GUI.getMainMenu().getScene());
		
		//wenn man will das es neu startet müsste man mainmenu initialize machen
	}

	
}
