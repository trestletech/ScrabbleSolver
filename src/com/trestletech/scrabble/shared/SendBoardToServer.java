package com.trestletech.scrabble.shared;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import com.trestletech.scrabble.shared.SendBoardToServerResult;

public class SendBoardToServer extends
		UnsecuredActionImpl<SendBoardToServerResult> {

	private Character[][] boardToServer;

	@SuppressWarnings("unused")
	private SendBoardToServer() {
		// For serialization only
	}

	public SendBoardToServer(Character[][] boardToServer) {
		this.boardToServer = boardToServer;
	}

	public Character[][] getBoardToServer() {
		return boardToServer;
	}
}
