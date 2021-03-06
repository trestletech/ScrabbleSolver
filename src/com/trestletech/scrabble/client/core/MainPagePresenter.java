package com.trestletech.scrabble.client.core;

import java.util.Random;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.trestletech.scrabble.client.place.NameTokens;
import com.trestletech.scrabble.shared.BoardSettings;
import com.trestletech.scrabble.shared.BoardVerifier;
import com.trestletech.scrabble.shared.ScoredWord;
import com.trestletech.scrabble.shared.SendBoardToServer;
import com.trestletech.scrabble.shared.SendBoardToServerResult;
import com.trestletech.scrabble.shared.WordScorer;

public class MainPagePresenter extends
		Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

	public interface MyView extends View {
		HasClickHandlers getSolveClickHandlers();
		HasClickHandlers getClearClickHandlers();
		HasClickHandlers getRandomClickHandlers();
		HasClickHandlers[][] getButtons();
		
		
		void setResults(ScoredWord[] results);
		
		
		HasText[][] getValues();
		
		String getChar(int row, int col);
		
		void assignChar(int row, int col, String ch);
		
		void clear();

		void setError(String errorText);
	}

	@ProxyStandard
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPagePresenter> {
	}

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;

	@Inject
	public MainPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final PlaceManager placeManager, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getView().getSolveClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						sendBoardToServer();
					}
				}));
		
		registerHandler(getView().getClearClickHandlers().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				getView().clear();
			}			
		}));
		
		final Random r = new Random();		
		registerHandler(getView().getRandomClickHandlers().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){			
					for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){
						//randomly assign characters in a uniform distribution
						String c = Character.toString((char)(r.nextInt(26) + 'A'));
						if (c.equals("Q")){
							c = "QU";							
						}
						getView().assignChar(row, col, c);
					}
				}				
			}			
		}));
		
		HasClickHandlers[][] buttons = getView().getButtons();
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){			
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){				
				final int rowNum = row;
				final int colNum = col;
				registerHandler(buttons[row][col].addClickHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						String ch = Window.prompt("Enter the character for this tile.", getView().getChar(rowNum, colNum));
						if (!BoardVerifier.isValidEntry(ch)){
							getView().setError("You must enter exactly one alphabetic character for each tile (or 'qu').");
						} else{
							//valid entry, assign
							getView().assignChar(rowNum, colNum, ch);
						}
					}					
				}));
			}
		}
		
		
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().clear();
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void sendBoardToServer() {
		getView().setResults(null);
		
		// First, we validate the input.
		getView().setError("");
		HasText[][] valuesHV = getView().getValues();
		String[][] values = new String[BoardSettings.BOARD_SIZE][];
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){	
			values[row] = new String[BoardSettings.BOARD_SIZE];
			
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){				
				String val = valuesHV[row][col].getText();
				
				//too long?
				if (!BoardVerifier.isValidEntry(val)){
					getView().setError("Each cube must have only one character in it (or be 'QU').");
					return;
				}
				values[row][col] = val;
			}
		}
		
		//check that the whole board is valid
		if (!BoardVerifier.isValidBoard(values)) {
			getView().setError("Please ensure that each cube has exactly one letter assigned to it.");
			return;
		}
	
		
		//if valid, send to server
		dispatcher.execute(new SendBoardToServer(values),
				new AsyncCallback<SendBoardToServerResult>() {
					@Override
					public void onFailure(Throwable caught) {
						getView().setError("Error communicating with server. Check that you're online or try again later.");
					}
	
					@Override
					public void onSuccess(SendBoardToServerResult result) {
						//compute scores for all words
						String[] words = result.getResponse();
						
						ScoredWord[] scores = WordScorer.scoreWords(words);
						
						getView().setResults(scores);
						
					}
				});
	}
}
