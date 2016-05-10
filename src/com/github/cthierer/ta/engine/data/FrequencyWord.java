package com.github.cthierer.ta.engine.data;

public class FrequencyWord extends Word {

	/**
	 * The number of occurrences of this word found during training. 
	 */
	private int frequency; 
	
	public FrequencyWord(String word) {
		super(word);
		this.frequency = 0;
	}
	
	/**
	 * @return The frequency that this word occurred in a training passage. 
	 */
	public int getFrequency() {
		return this.frequency;
	}

	/**
	 * Increment the frequency count by 1. 
	 */
	public void incrementFrequency() {
		this.frequency += 1;
	}
	
}
