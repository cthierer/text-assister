package com.github.cthierer.ta.engine.data;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Collection of Words that can be looked up by exact match or 
 * near-match (find all words that start with the provided fragment, ignoring 
 * case). 
 * @param <T>	The type of Word stored in this dictionary. 
 */
public class AutocompleteDictionary<T extends IWord> {

	/**
	 * Represents the first non-printing character in ASCII that occurs after 
	 * the last printing character ('z'). 
	 */
	private static final char LAST_CHARACTER = (char) ((int) 'z' + 1);
	
	/**
	 * The underlying collection of Words, stored in a map where the 
	 * key is a transformed word, and the map is sorted alphabetically. 
	 */
	private final TreeMap<String, T> words; 
	
	public AutocompleteDictionary() {
		
		/*
		 * Instantiate a Map that is sorted using the custom comparator for 
		 * sorting Candidate words. See the definition of the 
		 * CandidateWordComparator class below.  
		 */
		
		this.words = new TreeMap<String, T>(new PartialWordComparator());
	}
	
	/**
	 * Include a Word in the dictionary. This overwrites an existing 
	 * Word with the same name, if one exists. 
	 * @param word The Word to add to the dictionary. 
	 */
	public void add(T word) {
		if (word == null) {
			return;
		}
		
		/*
		 * Candidates are keyed by the word they describe. They must be 
		 * formatted to ignore case and punctuation.
		 */
		
		this.words.put(this.formatKey(word.getWord()), word);
	}
	
	/**
	 * Retrieve a Word identified by the provided word (exact match, 
	 * ignoring case and punctuation). 
	 * @param word	The word to lookup. 
	 * @return The Word describing the specified word, or null if no 
	 *  matching Word was found in this dictionary. 
	 */
	public T get(String word) {
		return this.words.get(this.formatKey(word));
	}
	
	/**
	 * Get all similar matches to a fragment. This returns all Words in 
	 * the dictionary that start with the provided fragment, ignoring case. 
	 * For example, "TH" would match "the", "this", and "their".
	 * @param fragment	The word fragment to use to lookup similar Words. 
	 * @return A collection of matching Words, sorted alphabetically by word. 
	 */
	public Collection<T> getMatches(String fragment) {
		if (fragment == null) {
			return null;
		}
		
		/*
		 * Use the submap property of a Map, which returns a set of the map 
		 * between the provided values. In this case, we use the fragment 
		 * parameter as the starting point (inclusive), and find all keys that 
		 * match up until that fragment, concatenated with the last 
		 * printable character (inclusive). (Because the second parameter in 
		 * submap is actually treated to be exclusive, in order to form an 
		 * inclusive set, need to add the first non-printing character after 
		 * the last printing character to the end of the fragment string.) 
		 */
		
		final String keyFragment = fragment.toLowerCase();
		final Map<String, T> matches = this.words.subMap(keyFragment, 
			keyFragment + LAST_CHARACTER);
		
		return matches.values();
	}
	
	/**
	 * Convert a string into a key value to store in the map. 
	 * @param fromStr	The string to convert. 
	 * @return The fromStr parameter, converted to lower case with all 
	 *  punctuation removed. 
	 */
	private String formatKey(String fromStr) {
		if (fromStr == null) {
			throw new IllegalArgumentException();
		}
		
		return fromStr.toLowerCase().replaceAll("\\W", "");
	}
	
	/**
	 * A custom Comparator that works on String values. The second argument 
	 * to the compare function is treated as a partial string value, which 
	 * is compared against the first argument. The two values are considered 
	 * an exact match if the first argument starts with the second argument; 
	 * otherwise, the arguments are sorted in natural order. 
	 */
	private class PartialWordComparator implements Comparator<String> {
		
		private final Comparator<String> naturalComparator = Comparator.naturalOrder();

		@Override
		public int compare(String compareTo, String value) {
			if (compareTo == null || value == null) {
				return naturalComparator.compare(compareTo, value);
			}	 
			
			if (compareTo.length() >= value.length()) {
				
				/*
				 *  Value is too long; cannot be a substring of compareTo.
				 *  Use the natural comparator for strings.
				 */
				
				return naturalComparator.compare(compareTo, value);
			}
			
			return naturalComparator.compare(compareTo, 
				value.substring(0, compareTo.length()));
		}
		
	}
}
