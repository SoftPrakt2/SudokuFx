package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.prefs.Preferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.SharedStoragePreferences;

public class SharedStoragePreferencesTest {

	SharedStoragePreferences ssp = new SharedStoragePreferences();
	@BeforeEach
	public void setUp() {
		SharedStoragePreferences ssp = new SharedStoragePreferences();
	}

	@Test
	public void testSetStoragePreference() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		assertEquals(ssp.getPreferedDirectory(), "/D:/SudokuGames");
	}	
	
	@Test
	public void testGetPreferedDirectory() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		assertEquals(ssp.getPreferedDirectory(), "/D:/SudokuGames");
	}	

	@Test
	public void testGetStoragePrefs() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		System.out.println(ssp.getStoragePrefs());
		assertEquals(ssp.getStoragePrefs().toString(), "User Preference Node: /logic.SharedStoragePreferences");
	}	
}
