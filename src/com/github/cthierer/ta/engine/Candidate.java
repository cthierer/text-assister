package com.github.cthierer.ta.engine;

/**
 * Prediction candidate to auto-complete a text fragment.  
 */
public class Candidate implements ICandidate {
	
	/**
	 * The predicted word represented by this Candidate. 
	 */
	private final String word;
	
	/**
	 * The confidence that this candidate is the correct match. 
	 */
	private final int confidence;
	
	/**
	 * @param word			Predicted word represented by this Candidate. 
	 * @param confidence	Numeric confidence that this candidate is correct. 
	 */
	public Candidate(String word, int confidence) {
		this.word = word; 
		this.confidence = confidence;
	}
	
	@Override
	public Integer getConfidence() {
		return confidence;
	}

	@Override
	public String getWord() {
		return this.word;
	}
	
	@Override 
	public String toString() {
		return getWord() + " (" + getConfidence() + ")";
	}
}
