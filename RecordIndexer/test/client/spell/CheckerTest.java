package client.spell;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import client.spell.Checker;

public class CheckerTest {
	
	private Checker checker;

	@Before
	public void setUp() throws Exception {
		checker = new Checker();
	}
	
	@Test
	public void testUseDictionary() throws IOException{
		// some words
		File file = new File("src/client/spell/words.txt");
		checker.useDictionary(file);
		int size = checker.getDictionary().size();
		assertEquals(17,size);
		
		// no word
		File file2 = new File("src/client/spell/words0.txt");
		checker.useDictionary(file2);
		int size2 = checker.getDictionary().size();
		assertEquals(0,size2);
	}

	@Test
	public void testSuggestSimilarWord() throws IOException{
		checker.useDictionary(new File("src/client/spell/words.txt"));
		
		// exact same word
		checker.suggestSimilarWord("COMPUTER");
		Set<String> result1 = checker.getSuggestions();
		int size1 = result1.size();
		assertEquals(1,size1);
		assertTrue(result1.contains("COMPUTER"));
		
		// get a list
		checker.suggestSimilarWord("MORE");
		Set<String> result2 = checker.getSuggestions();
		int size2 = result2.size();
		assertEquals(4,size2);
		assertFalse(result2.contains("MORE"));
		assertTrue(result2.contains("WORD"));
		assertTrue(result2.contains("HERE"));
		assertTrue(result2.contains("MOST"));
		assertTrue(result2.contains("MOUSE"));
		
		// no similar
		checker.suggestSimilarWord("PAULACHEN");
		Set<String> result3 = checker.getSuggestions();
		int size3 = result3.size();
		assertEquals(0,size3);
	}
	
	@Test(expected=IOException.class)
	public void testInvalidUseDictionary() throws IOException {
		File file = new File("abc.txt");
		checker.useDictionary(file);
	}
	
}
