package com.trestletech.scrabble.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.trestletech.scrabble.client.core.MainPagePresenter;
import com.trestletech.scrabble.client.core.MainPageView;
import com.trestletech.scrabble.client.place.ClientPlaceManager;
import com.trestletech.scrabble.client.place.DefaultPlace;
import com.trestletech.scrabble.client.place.NameTokens;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);

	}
}
