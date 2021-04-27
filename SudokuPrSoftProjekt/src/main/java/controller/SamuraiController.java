package controller;

import application.BasicGameBuilder;
import application.SudokuField;
import javafx.event.ActionEvent;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;

public class SamuraiController extends BasicController {
	
	
	BasicGameBuilder scene;


	int difficulty = 0;
	BasicGameLogic model;
	SudokuField[][] fields;
	
	public SamuraiController(BasicGameBuilder scene) {
		this.scene = scene;
		this.model = new SamuraiLogic(Gamestate.OPEN,0.0,false);
		fields = scene.getTextField();
	}
	
	

	@Override
	public void createGameHandler(ActionEvent e) {
	
	}








	@Override
	public void newGameHandler(ActionEvent e) {
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
					fields[i][j].setText("2");
			}
		}
	}




	@Override
	public void checkHandler(ActionEvent e) {
	
		
	}








	@Override
	public void saveHandler(ActionEvent e) {
		
		
	}








	@Override
	public void autoSolveHandler(ActionEvent e) {
		
		
	}












	






	@Override
	public void manuelDoneHandler(ActionEvent e) {
		
		
	}








	@Override
	public void resetHandler(ActionEvent e) {
	
		
	}








	@Override
	public void createGame(int difficulty) {
		// TODO Auto-generated method stub
		
	}
		
	

	
}
