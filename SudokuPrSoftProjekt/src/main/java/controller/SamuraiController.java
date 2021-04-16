package controller;

import application.BasicGameBuilder;
import application.SudokuField;
import javafx.event.ActionEvent;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

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
	
		System.out.println("hi");
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
					if(fields[i][j].getText().equals("0"))
					fields[i][j].setText("1");


}}}
		
	

	@Override
	public void enableEditHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void easyHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediumHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hardHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newGameHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoSolveHandler(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
