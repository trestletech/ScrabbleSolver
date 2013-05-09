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
	public static boolean isValidBoard(Character[][] board) {
		if (board[0].length != BoardSettings.BOARD_SIZE){
			return false;
		}
		for (Character[] row : board){
			if (row.length != BoardSettings.BOARD_SIZE){
				return false;
			}
			
			for (Character c : row){
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
	
	public static boolean isValidEntry(Character ch){
		if (!Character.isLetter(ch)){
			return false;
		}
		return true;
	}
}
