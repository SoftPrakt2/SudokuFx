package logic;

import java.util.prefs.Preferences;

public class SharedStoragePreferences {
	
    Preferences prefs = Preferences.userRoot().node(getClass().getName());

    public Preferences getStoragePrefs() {
        return prefs;
    }
}