package com.trestletech.scrabble.shared;

import com.gwtplatform.dispatch.shared.Result;

public class SendBoardToServerResult implements Result {

	private String[] response;

	@SuppressWarnings("unused")
	private SendBoardToServerResult() {
		// For serialization only
	}

	public SendBoardToServerResult(String[] response) {
		this.response = response;
	}

	public String[] getResponse() {
		return response;
	}
}
