package com.objectheads.oh2048.ui;

import javafx.stage.Stage;

public class Game {

	private GameView view;
	private GameModel model;
	private GameController controller;

	public Game(final Stage stage)
	{
		view = new GameView(stage);
		// model = new GameModel(view);
		// model = new GameModel(view, new FillBoardInitializer());
		model = new GameModel(view, new TwoTilesInitializer());
		controller = new GameController(model);
		stage.getScene().setOnKeyPressed(p -> controller.onKeyPressed(p));
	}

}
