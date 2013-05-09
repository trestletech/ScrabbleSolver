package com.trestletech.scrabble.server;

public interface Dictionary {
	public void addWord(String word);
	public SearchResult contains(String word);
}
