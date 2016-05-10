package com.github.cthierer.ta.engine;

import java.util.List;

/**
 * Allows searching for likely next words for auto-complete. 
 */
public interface IAutocompleteProvider {

	/**
	 * @param fragment	The search fragment to attempt to auto-complete. 
	 * @return List of auto-complete candidates, ordered by confidence. 
	 */
	List<ICandidate> getWords(String fragment);
	
	/**
	 * Trains the algorithm with the provided passage. 
	 * @param passage	The phrase to use to train the auto-complete algorithm.
	 */
	void train(String passage);
}
