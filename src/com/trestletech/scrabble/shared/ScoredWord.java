package com.trestletech.scrabble.shared;

/**
 * Stores the Boggle score associated with each word. Often computed by WordScorer.
 * @author jalle6
 *
 */
public class ScoredWord {
	private String word;
	private int score;
		
	public ScoredWord() {
		
	}
	public ScoredWord(String word, int score) {
		super();
		this.word = word;
		this.score = score;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
