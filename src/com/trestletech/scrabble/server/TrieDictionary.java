package com.trestletech.scrabble.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.inject.Inject;

public class TrieDictionary implements Dictionary{
	
	Trie trie;
	
	@Inject
	public TrieDictionary(){
		trie = new Trie();
		
	}

	@Override
	public void addWord(String word) {
		trie.insert(word);
	}

	@Override
	public SearchResult contains(String word) {
		return trie.search(word);
	}
	
}
