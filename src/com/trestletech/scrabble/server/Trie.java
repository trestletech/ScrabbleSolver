package com.trestletech.scrabble.server;

/**
 * Taken from http://ken-soft.com/2012/03/20/java-trie-prefix-tree/ 
 *
 */
public class Trie {
	 
	private ObjectTrie<Character> trie;
 
	public Trie() {
		trie = new ObjectTrie<Character>(' ');
	}
 
	public void insert(String s) {
		trie.insert(toArray(s));
	}
 
	public SearchResult search(String s) {
		return trie.search(toArray(s));
	}
 
	public int numberEntries() {
		return trie.numberEntries();
	}
 
	private Character[] toArray(String s) {
		Character[] cArray = new Character[s.length()];
		for(int i = 0; i < cArray.length; i++) {
			cArray[i] = s.charAt(i);
		}
		return cArray;
	}
 
	public String toString() {
		return trie.toString();
	}
 
}