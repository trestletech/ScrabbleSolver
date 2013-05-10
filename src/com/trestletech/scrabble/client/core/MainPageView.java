package com.trestletech.scrabble.client.core;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.trestletech.scrabble.shared.BoardSettings;
import com.trestletech.scrabble.shared.ScoredWord;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private static String html = "<h1>Scrabble Solver</h1>";
	private final HTMLPanel panel = new HTMLPanel(html);
	private final FlowPanel boardPanel = new FlowPanel();
	private final HTMLPanel resultsPanel = new HTMLPanel("<h2>Solution</h2>");
	private final Label errorLabel;
	private final Button solveButton;
	private final Button randomButton;
	private final Button clearButton;
	private final Grid resultsTbl;
		
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
				
				b.getElement().addClassName("boggle-tile");
				
				boggleBtns[row][col] = b;
			}
		}
		
		resultsTbl = new Grid(1, 2);
		
		
		// We can add style names to widgets
		solveButton.addStyleName("sendButton");

		boardPanel.add(boggleTable);
		
		resultsPanel.add(solveButton);
		resultsPanel.add(resultsTbl);
				
		VerticalPanel leftPanel = new VerticalPanel();
		
		leftPanel.add(boardPanel);
		leftPanel.add(randomButton);
		leftPanel.add(clearButton);		
		leftPanel.add(errorLabel);
		
		HorizontalPanel horiz = new HorizontalPanel();
		horiz.add(leftPanel);
		horiz.add(resultsPanel);
		
		panel.add(horiz);		
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
	public void assignChar(int row, int col, String ch) {
		boggleBtns[row][col].setText(ch.toString().toUpperCase());
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

	@Override
	public void setResults(ScoredWord[] results) {		
		//wipe the table
		resultsTbl.clear(true);
		resultsTbl.resizeRows(0);
		
		//if no results, just leave the table empty.
		if (results == null){
			return;
		}
		
		//if valid response, but no results
		if (results.length == 0){
			resultsTbl.resizeRows(1);
			resultsTbl.setText(0, 0, "No words in this arrangement.");
			return;
		}
		
		//initialize with proper number of rows
		resultsTbl.resizeRows(results.length + 1);
		
		resultsTbl.setText(0, 0, "Word");
		resultsTbl.setText(0, 1, "Pts");
				
		for (int i = 1; i <= results.length; i++){
			resultsTbl.setText(i, 0, results[i-1].getWord());
			resultsTbl.setText(i, 1, Integer.toString(results[i-1].getScore()));			
		}
		
		
	}
}
