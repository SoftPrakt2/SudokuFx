package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.SharedStoragePreferences;

class SharedStoragePreferencesTest {

	SharedStoragePreferences ssp = new SharedStoragePreferences();
	@BeforeEach
	public void setUp() {
		SharedStoragePreferences ssp = new SharedStoragePreferences();
	}

	@Test
	void testSetStoragePreference() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		assertEquals(ssp.getPreferedDirectory(), "/D:/SudokuGames");
	}	
	
	@Test
	void testGetPreferedDirectory() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		assertEquals(ssp.getPreferedDirectory(), "/D:/SudokuGames");
	}	

	@Test
	void testGetStoragePrefs() {		
		ssp.setStoragePreference("/D:/SudokuGames");
		System.out.println(ssp.getStoragePrefs());
		assertEquals(ssp.getStoragePrefs().toString(), "User Preference Node: /logic.SharedStoragePreferences");
	}	
}
