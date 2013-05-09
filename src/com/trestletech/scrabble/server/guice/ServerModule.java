package com.trestletech.scrabble.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.trestletech.scrabble.shared.SendBoardToServer;
import com.trestletech.scrabble.server.SendTextToServerActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(SendBoardToServer.class, SendTextToServerActionHandler.class);
	}
}
