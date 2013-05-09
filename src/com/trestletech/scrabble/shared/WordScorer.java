package com.trestletech.scrabble.shared;

/**
 * Scores Boggle words based on pre-defined table.
 * @author jalle6
 *
 */
public class WordScorer {		
	
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
