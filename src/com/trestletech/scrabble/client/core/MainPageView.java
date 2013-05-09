package com.trestletech.scrabble.client.core;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.trestletech.scrabble.shared.BoardSettings;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private static String html = "<h1>Scrabble Solver</h1>";
	private final HTMLPanel panel = new HTMLPanel(html);
	private final Label errorLabel;
	private final Button solveButton;
	private final Button randomButton;
	private final Button clearButton;
	
	private final Button[][] boggleBtns;
	
	
	@Inject
	public MainPageView() {

		solveButton = new Button("Solve");
		randomButton = new Button("Randomize");
		clearButton = new Button("Clear");
		
		errorLabel = new Label();

		boggleBtns = new Button[BoardSettings.BOARD_SIZE][];
		
		
		Grid boggleTable = new Grid(BoardSettings.BOARD_SIZE,BoardSettings.BOARD_SIZE);
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){
			boggleBtns[row] = new Button[BoardSettings.BOARD_SIZE];
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){
				Button b = new Button("");
				boggleTable.setWidget(row,  col, b);
				
				boggleBtns[row][col] = b;
			}
		}
		
		// We can add style names to widgets
		solveButton.addStyleName("sendButton");

		panel.add(boggleTable);
		panel.add(randomButton);
		panel.add(clearButton);
		panel.add(solveButton);
		panel.add(errorLabel);
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasClickHandlers getSolveClickHandlers() {
		return solveButton;
	}

	@Override
	public void setError(String errorText) {
		errorLabel.setText(errorText);
	}

	@Override
	public HasClickHandlers[][] getButtons() {
		return boggleBtns;
	}

	@Override
	public void clear() {
		for (int row = 0; row < BoardSettings.BOARD_SIZE; row++){			
			for (int col = 0; col < BoardSettings.BOARD_SIZE; col++){				
				boggleBtns[row][col].setText("");
			}
		}
	}

	@Override
	public HasText[][] getValues() {
		return boggleBtns;
	}

	@Override
	public void assignChar(int row, int col, Character ch) {
		boggleBtns[row][col].setText(ch.toString());
	}

	@Override
	public String getChar(int row, int col) {
		return boggleBtns[row][col].getText();
	}

	@Override
	public HasClickHandlers getClearClickHandlers() {
		return clearButton;
	}

	@Override
	public HasClickHandlers getRandomClickHandlers() {
		return randomButton;
	}
}
