package com.github.cthierer.ta.engine.data;

public class Word implements IWord {
	
	/**
	 * The predicted word represented by this Candidate. 
	 */
	private final String word;
	
	/**
	 * @param word			Predicted word represented by this Candidate. 
	 */
	public Word(String word) {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		
		this.word = this.transformWord(word); 
	}

	@Override
	public String getWord() {
		return this.word;
	}
	
	@Override 
	public String toString() {
		return getWord();
	}
	
	/**
	 * Sanitize and transform a word value. This converts the string to lower 
	 * case.   
	 * @param word	The word to transform. 
	 * @return The transformed word. 
	 */
	protected String transformWord(String word) {
		if (word == null) {
			return null;
		}
		
		return word.toLowerCase();
	}

}
