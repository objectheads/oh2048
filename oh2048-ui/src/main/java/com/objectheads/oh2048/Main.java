package com.objectheads.oh2048;

import com.objectheads.oh2048.ui.Game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(final Stage stage) throws Exception
	{
		new Game(stage);
	}

	public static void main(final String[] args)
	{
		launch(args);
	}

}
