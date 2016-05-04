package com.objectheads.oh2048.ui;

import java.io.IOException;
import java.util.Optional;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameView extends AnchorPane {

	private final BoardView boardView;
	private final Stage stage;
	private Scene scene;
	private Label scoreLabel;

	public GameView(final Stage stage)
	{
		this.stage = stage;
		this.boardView = new BoardView();
		buildView();
		lookups();
	}

	public void buildView()
	{
		try {
			final FXMLLoader loader = new FXMLLoader();
			final Parent parent = (Parent)loader.load(getClass().getResourceAsStream("/game-main.fxml"));
			scene = new Scene(parent);
			stage.setTitle("2048");
			stage.setScene(scene);
			stage.show();
			final Pane gameBoard = (Pane)scene.lookup("#gamePane");
			gameBoard.getChildren().add(boardView);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void lookups()
	{
		scoreLabel = (Label)scene.lookup("#scoreLabel");
	}

	public StringProperty scoreProperty()
	{
		return scoreLabel.textProperty();
	}

	public void initialize()
	{
		boardView.initialize();
	}

	public boolean gameOver()
	{
		final Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("2048");
		alert.setHeaderText("Game over!");
		alert.setContentText("Start a new game or undo last move?");

		final ButtonType buttonTypeUndo = new ButtonType("Undo");
		final ButtonType buttonTypeNewGame = new ButtonType("New Game");

		alert.getButtonTypes().setAll(buttonTypeUndo, buttonTypeNewGame);
		
		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeNewGame) {
			return true;
		}
		return false;
	}
	
	public boolean gameWin()
	{
		final Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("2048");
		alert.setHeaderText("Congratulation! You win the game!");
		alert.setContentText("Would you like to continue or start a new game?");

		final ButtonType buttonTypeContinue = new ButtonType("Continue");
		final ButtonType buttonTypeNewGame = new ButtonType("New Game");

		alert.getButtonTypes().setAll(buttonTypeContinue, buttonTypeNewGame);

		final Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeNewGame) {
			return true;
		}
		return false;
	}

	public void createTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		boardView.createTileNode(tile, destinationPosition);
	}

	public void deleteTileNode(final Tile tile)
	{
		boardView.deleteTileNode(tile);
	}

	public void moveTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		boardView.moveTileNode(tile, destinationPosition);
	}

	public void createDescendantTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		boardView.createDescendantTileNode(tile, destinationPosition);
	}


	public void reset()
	{
		boardView.reset();
	}

}
