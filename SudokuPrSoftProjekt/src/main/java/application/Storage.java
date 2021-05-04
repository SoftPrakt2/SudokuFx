package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Storage {


	FileChooser fileChooser;
	JSONObject jsonObject;
	JSONParser parser = new JSONParser();
	
	
	public FileChooser setUpFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file");
		
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		return fileChooser;
	}
	
	public JSONObject getJSONFile(File file) {
		
		try {
			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
			jsonObject = (JSONObject) obj;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return jsonObject;
	}
	
}