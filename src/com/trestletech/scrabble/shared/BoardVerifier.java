package com.trestletech.scrabble.shared;

/**
 * <p>
 * BoardVerifier validates that the board the user enters is valid.
 * </p>
*/
public class BoardVerifier {

	/**
	 * Verify that the board is 
	 * 
	 * @param board the board to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidBoard(String[][] board) {
		if (board[0].length != BoardSettings.BOARD_SIZE){
			return false;
		}
		for (String[] row : board){
			if (row.length != BoardSettings.BOARD_SIZE){
				return false;
			}
			
			for (String c : row){
				if (c == null){
					return false;
				}
				if (!isValidEntry(c)){
					return false;
				}
			}
			
		}
		return true;
	}
	
	public static boolean isValidEntry(String ch){
		if (ch.length() != 1 && !ch.equalsIgnoreCase("qu")){
			return false;
		}
		if (!Character.isLetter(ch.charAt(0)) || ch.equalsIgnoreCase("Q")){
			return false;
		}
		return true;
	}
}
