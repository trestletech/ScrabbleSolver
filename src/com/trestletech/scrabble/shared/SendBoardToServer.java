package com.trestletech.scrabble.shared;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import com.trestletech.scrabble.shared.SendBoardToServerResult;

public class SendBoardToServer extends
		UnsecuredActionImpl<SendBoardToServerResult> {

	private String[][] boardToServer;

	@SuppressWarnings("unused")
	private SendBoardToServer() {
		// For serialization only
	}

	public SendBoardToServer(String[][] boardToServer) {
		this.boardToServer = boardToServer;
	}

	public String[][] getBoardToServer() {
		return boardToServer;
	}
}
