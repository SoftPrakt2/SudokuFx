package logic;

import java.util.prefs.Preferences;

/**
 * this class is needed to allow the implementation of a user specific consecutive number of game ids
 * These game ids are needed as unique identifiers for games which are saved or exported using the
 * {@link application.SudokuStorage} class
 * 
 * @author grube
 *
 */

public class SharedStoragePreferences {
	
    Preferences prefs = Preferences.userRoot().node(getClass().getName());

  
	public Preferences getStoragePrefs() {
        return prefs;
    }
}
