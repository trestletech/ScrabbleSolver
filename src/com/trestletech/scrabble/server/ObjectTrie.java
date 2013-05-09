package com.trestletech.scrabble.server;

/**
 * Taken from http://ken-soft.com/2012/03/20/java-trie-prefix-tree/ 
 *
 */
public class ObjectTrie<T> {
		
	private Node<T> root;
 
	private int numberEntries;
 
	public ObjectTrie(T rootNodeValue) {
		root = new Node<T>(rootNodeValue); // "empty value", usually some "null"  value or "empty string"
		numberEntries = 0;
	}
 
	public void insert(T[] values) {
		Node<T> current = root;
		if (values != null) {
			if (values.length == 0) { // "empty value"
				current.setEndMarker(true);
			}
			for (int i = 0; i < values.length; i++) {
				Node<T> child = current.findChild(values[i]);
				if (child != null) {
					current = child;
				} else {
					current = current.addChild(values[i]);
				}
				if (i == values.length - 1) {
					if (!current.isEndMarker()) {
						current.setEndMarker(true);
						numberEntries++;
					}
				}
			}
		} else {
			System.out.println("Not adding anything");
		}
	}
 
	/**
	 * 
	 * @param values
	 * @return false if the value wasn't found in the tree, true if the value was found in the tree, 
	 * and null if the values wasn't found, but is a prefix to other 
	 */
	public SearchResult search(T[] values) {
		Node<T> current = root;
		for (int i = 0; i < values.length; i++) {
			if (current.findChild(values[i]) == null) {
				return SearchResult.NOT_FOUND_NO_CHILDREN;
			} else {
				current = current.findChild(values[i]);
			}
		}
		/*
		 * Array T[] values found in ObjectTrie. Must verify that the "endMarker" flag
		 * is true
		 */
		if (current.isEndMarker()) {
			return SearchResult.FOUND;
		} else {
			return SearchResult.NOT_FOUND_WITH_CHILDREN;
		}
	}
 
	public int numberEntries() {
		return numberEntries;
	}
 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("number entries: ");
		sb.append(numberEntries);
 
		return sb.toString();
	}
 
}
