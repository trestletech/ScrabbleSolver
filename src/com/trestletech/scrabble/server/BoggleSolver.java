package com.trestletech.scrabble.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import com.google.inject.Inject;
import com.trestletech.scrabble.shared.BoardSettings;

public class BoggleSolver {
	
	private Dictionary dictionary;
	
	@Inject
	public BoggleSolver(Dictionary dictionary){
		this.dictionary = dictionary;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"));
			String line;
			while((line = br.readLine()) != null){
				dictionary.addWord(line.trim().toUpperCase());
			}
		}
		catch (FileNotFoundException e){
			System.err.println(e);
		} catch (IOException e){
			System.err.println(e);
		}
		
	}
	
	public String[] solveBoard(String[][] board){
		List<String> toReturn = new Vector<String>();
		
		List<Cube> cubeBoard = parseBoard(board);
		
		for (Cube cube : cubeBoard){
			Stack<Cube> stack = new Stack<Cube>();		
			stack.push(cube);
			toReturn = identifyWords(toReturn, stack);			
		}
		return (toReturn.toArray(new String[toReturn.size()]));
	}
	
	private List<String> identifyWords(List<String> words, Stack<Cube> stack){		
		String seq = "";
		for (int i = 0; i < stack.size(); i++){
			seq += stack.get(i).getValue();
		}
		
		SearchResult sr = dictionary.contains(seq);
		if (sr == SearchResult.FOUND){
			//TODO: store the words in a hash for quicker lookup.
			if (!words.contains(seq) && seq.length() > 2){
				words.add(seq);
			}			
		}
		
		//see if we need to search the children.
		if (sr == SearchResult.FOUND || sr == SearchResult.NOT_FOUND_WITH_CHILDREN){
			for (Cube conn : stack.peek().getConnections()){
				//check to see that we haven't already visited this cube
				if (!stack.contains(conn)){
					stack.push(conn);
					words = identifyWords(words, stack);
					
					//remove the node from the stack
					stack.pop();
				}
			}
			return words;
		} else{
			//search wasn't found and had no children			
			return words;			
		}
		
	}
	
	
	/**
	 * Parses the board into a graph of cubes.
	 * @param board the board to parse
	 * @return a List of Cubes which have connections to other cubes as defined by the rules 
	 * of connecting cubes in Boggle (adjacent or diagonal)
	 */
	private List<Cube> parseBoard(String[][] board){
		List<Cube> cubes = new Vector<Cube>();
		
		
		//initialize the graph of cubes.
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){			
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){				
				Cube cube = new Cube();
				cube.setValue(board[row][col]);
				cubes.add(cube);
			}
		}
		
		//now define the relationship between the cubes		
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){			
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){			
				Cube cube = lookupCube(row, col, cubes);
				
				//add all possible connections for this cube. Nulls are ignored when trying to add.
				cube.addConnection(lookupCube(row-1, col, cubes));
				cube.addConnection(lookupCube(row-1, col+1, cubes));
				cube.addConnection(lookupCube(row, col+1, cubes));
				cube.addConnection(lookupCube(row+1, col+1, cubes));
				cube.addConnection(lookupCube(row+1, col, cubes));
				cube.addConnection(lookupCube(row+1, col-1, cubes));
				cube.addConnection(lookupCube(row, col-1, cubes));
				cube.addConnection(lookupCube(row-1, col-1, cubes));				
			}
		}
		
		return cubes;
	}
	
	/**
	 * Computes the linear of a cube as it was added to the original list and looks it up.
	 * Returns null if the index is invalid.
	 */
	private Cube lookupCube(int row, int col, List<Cube> cubes){
		if (row < 0 || row >= BoardSettings.BOARD_SIZE){
			return null;
		}
		
		if (col < 0 || col >= BoardSettings.BOARD_SIZE){
			return null;
		}
		
		return cubes.get(row * BoardSettings.BOARD_SIZE + col);
	}
	
}
