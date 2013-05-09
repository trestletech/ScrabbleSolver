package com.trestletech.scrabble.shared;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Scores Boggle words based on pre-defined table.
 * @author jalle6
 *
 */
public class WordScorer {		
	
	public static ScoredWord[] scoreWords(String[] words){		
		ScoredWord[] scores = new ScoredWord[words.length];		
		for (int i = 0; i < words.length; i++){
			scores[i] = WordScorer.scoreWord(words[i]);
		}
		
		Arrays.sort(scores);
		
		return scores;
	}
	
	public static ScoredWord scoreWord(String word){
		int score = computeScoreByLength(word);
		return new ScoredWord(word, score);
	}
	
	private static int computeScoreByLength(String word){
		int len = word.length();
		
		//scoring table obtained from http://en.wikipedia.org/wiki/Boggle
		
		if (len < 3){
			return 0;
		} else if (len < 5){
			return 1;
		} else if (len < 6){
			return 2;
		} else if (len < 7){
			return 3;
		} else if (len < 8){
			return 5;
		} else{
			return 11;
		}
	}
}
