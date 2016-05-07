package com.github.cthierer.ta.assister;

/**
 * Defines a prediction candidate.
 */
public interface ICandidate {
	
	/**
	 * @return The auto-complete candidate.
	 */
	String getWord();
	
	/**
	 * @return The confidence for the candidate. 	
	 */
	Integer getConfidence();
}
