package com.trestletech.scrabble.server.guice;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.trestletech.scrabble.server.Dictionary;
import com.trestletech.scrabble.server.SendBoardToServerActionHandler;
import com.trestletech.scrabble.server.TrieDictionary;
import com.trestletech.scrabble.shared.SendBoardToServer;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(SendBoardToServer.class, SendBoardToServerActionHandler.class);
		bind(Dictionary.class).to(TrieDictionary.class).in(Singleton.class);
	}
}
