package com.github.cthierer.ta.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.cthierer.ta.engine.data.AutocompleteDictionary;
import com.github.cthierer.ta.engine.data.FrequencyWord;

/**
 * Implement the requirements of the autocomplete engine. Specifically, allow 
 * training of the engine using a phrase, and retrieving all words that match 
 * a word fragment, ordered by confidence. 
 */
public class AutocompleteProvider implements IAutocompleteProvider {
	
	/**
	 * Underlying dictionary of words that can be matched against. 
	 */
	private AutocompleteDictionary<FrequencyWord> dictionary;
	
	public AutocompleteProvider() {
		dictionary = new AutocompleteDictionary<FrequencyWord>();
	}
	
	@Override
	public List<ICandidate> getWords(String fragment) {
		if (fragment == null) {
			return new ArrayList<ICandidate>();
		}
		
		final Collection<FrequencyWord> matches = this.dictionary.getMatches(fragment);
		final List<ICandidate> candidates = new ArrayList<ICandidate>();
		
		/*
		 * Convert word into candidate. 
		 * This is done in this fashion, so that confidence can eventually 
		 * be calculated on demand using additional factors. For example, 
		 * tracking the order that words may occur in, where confidence may 
		 * not always be constant depending on past history of words. 
		 */
		
		for (FrequencyWord word : matches) {
			candidates.add(new Candidate(word.getWord(), word.getFrequency()));
		}
		
		// sort the words ordered by confidence 
		Collections.sort(candidates, new CandidateConfidenceComparator());
		
		return candidates;
	}

	@Override
	public void train(String passage) {
		if (passage == null) {
			return;
		}
		
		// split a string using whitespace
		final String[] words = passage.split("\\s+");
		
		for (String word : words) {
			FrequencyWord candidate = this.dictionary.get(word);
			
			if (candidate == null) {
				// candidate for this word was not found; create one and add it
				candidate = new FrequencyWord(word);
				this.dictionary.add(candidate);
			}
			
			// count the occurrence of this word 
			candidate.incrementFrequency();			
		}
	}
	
	/**
	 * Sort candidates by confidence in descending order. 
	 */
	private class CandidateConfidenceComparator implements Comparator<ICandidate> {

		/**
		 * Underlying comparator for comparing integer values. 
		 */
		private final Comparator<Integer> intComparator = Comparator.naturalOrder();
		
		@Override
		public int compare(ICandidate value, ICandidate compareTo) {
			if (value == null) {
				return compareTo == null ? 0 : -1;
			} else if (compareTo == null) {
				return 1;
			}
			
			// swap the order of arguments to achieve descending order 
			return intComparator.compare(compareTo.getConfidence(), value.getConfidence());
		}
		
	}
}
