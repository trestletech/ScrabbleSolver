package com.trestletech.scrabble.server;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.trestletech.scrabble.shared.SendBoardToServer;
import com.trestletech.scrabble.shared.SendBoardToServerResult;
import com.google.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.trestletech.scrabble.shared.BoardVerifier;

public class SendBoardToServerActionHandler implements
		ActionHandler<SendBoardToServer, SendBoardToServerResult> {

	private final ServletContext servletContext;

	private final Provider<HttpServletRequest> requestProvider;
	private final BoggleSolver boggleSolver;
	
	
	@Inject
	public SendBoardToServerActionHandler(final ServletContext servletContext, 
			final Provider<HttpServletRequest> requestProvider, 
			final BoggleSolver boggleSolver) {

		this.servletContext = servletContext;
		this.requestProvider = requestProvider;
		this.boggleSolver = boggleSolver;
	}

	@Override
	public SendBoardToServerResult execute(SendBoardToServer action,
			ExecutionContext context) throws ActionException {
		String[][] input = action.getBoardToServer();
	
		// Verify that the input is valid.
		if (!BoardVerifier.isValidBoard(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new ActionException("You must have exactly one character in each cube (or 'qu').");
		}
			
		
		
		return (new SendBoardToServerResult(boggleSolver.solveBoard(input)));
	}

	@Override
	public void undo(SendBoardToServer action, SendBoardToServerResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<SendBoardToServer> getActionType() {
		return SendBoardToServer.class;
	}
}
