package logic;

import java.util.prefs.Preferences;

public class SharedStoragePreferences {

	int sudokuSaveCounter = 0;
	int samuraiSaveCounter = 0;
	int freeFormSaveCounter = 0;

	public String directoryPath = "/D:/SudokuGames";

	Preferences prefs = Preferences.userRoot().node(getClass().getName());

	public void setStoragePreference(String path) {
		prefs.put("DirectoryPath", path);
	}

	public String getPreferedDirectory() {
		return prefs.get("DirectoryPath", "nothingfound");
	}

	public Preferences getStoragePrefs() {
		return prefs;
	}

}
