package com.objectheads.oh2048.ui;

import com.objectheads.oh2048.grid.Grid;
import com.objectheads.oh2048.grid.GridBuilder;
import com.objectheads.oh2048.grid.GridEvent;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.GridUndo;
import com.objectheads.oh2048.grid.Tile;
import com.objectheads.oh2048.grid.event.DeleteTileEvent;
import com.objectheads.oh2048.grid.event.MoveEvent;
import com.objectheads.oh2048.grid.event.MoveTileEvent;
import com.objectheads.oh2048.grid.event.NewDescendantTileCreateEvent;
import com.objectheads.oh2048.grid.event.NewTileCreateEvent;
import com.objectheads.oh2048.grid.event.NoMoreStepsEvent;
import com.objectheads.oh2048.grid.event.ResetEvent;
import com.objectheads.oh2048.grid.event.ScoreIncreasedEvent;
import com.objectheads.oh2048.grid.event.TargetReachedEvent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameModel {

	private final GameView view;
	private final GameInitializer gameInitializer;
	private final StringProperty score = new SimpleStringProperty("0");

	private Grid grid;
	private GridUndo undo;

	public GameModel(final GameView view)
	{
		this.view = view;
		this.gameInitializer = new RandomTwoTilesInitializer();
		initializeGrid();
		initialize();
	}

	public GameModel(final GameView view, final GameInitializer gameInitializer)
	{
		this.view = view;
		this.gameInitializer = gameInitializer;
		initializeGrid();
		initialize();
	}

	private void initializeGrid()
	{
		grid = GridBuilder.create().build();

		this.undo = grid.getGridUndo();

		final GridEvent gridEvents = grid.getGridEvent();
		gridEvents.setOnMoveTile(e -> handleMoveEvent(e));
		gridEvents.setOnDeleteTile(e -> handleDeleteEvent(e));
		gridEvents.setOnNoMoreSteps(e -> handleNoMoreSteps(e));
		gridEvents.setOnNewDescendantTileCreate(e -> handleJoinTilesEvent(e));
		gridEvents.setOnNewTileCreate(e -> handleNewTileCreateEvent(e));
		gridEvents.setOnMove(e -> handleMoveEventEvent(e));
		gridEvents.setOnScoreIncreased(e -> handleScoreIncreasedEvent(e));
		gridEvents.setOnTargetReached(e -> handleTargetReachedEvent(e));
		gridEvents.setOnResetEvent(e -> handleResetEvent(e));
	}

	public void initialize()
	{
		view.initialize();
		gameInitializer.initializeBoard(grid);
		view.scoreProperty().bindBidirectional(score);
	}

	public void reset()
	{
		grid.reset();
		gameInitializer.initializeBoard(grid);
	}

	public void handleNoMoreSteps(final NoMoreStepsEvent event)
	{
		if (view.gameOver()) {
			grid.reset();
		} else {
			undo.undo();
		}
	}

	public void handleNewTileCreateEvent(final NewTileCreateEvent event)
	{
		final Tile tile = event.getTile();
		final GridPosition destinationPosition = event.getDestinationPosition();
		view.createTileNode(tile, destinationPosition);
	}

	public void handleDeleteEvent(final DeleteTileEvent event)
	{
		final Tile tile = event.getTile();
		view.deleteTileNode(tile);
	}

	public void handleMoveEvent(final MoveTileEvent event)
	{
		final Tile tile = event.getTile();
		view.moveTileNode(tile, event.getDestinationPosition());
	}

	public void handleJoinTilesEvent(final NewDescendantTileCreateEvent event)
	{
		final Tile tile = event.getTile();
		final GridPosition destinationPosition = event.getDestinationPosition();
		view.createDescendantTileNode(tile, destinationPosition);
	}

	private void handleMoveEventEvent(final MoveEvent event)
	{
	}

	private void handleScoreIncreasedEvent(final ScoreIncreasedEvent event)
	{
		score.set(Integer.toString(event.getNewScore()));
	}

	private void handleTargetReachedEvent(final TargetReachedEvent e)
	{
		final boolean newGame = view.gameWin();
		if (newGame) {
			initialize();
		}
	}

	private void handleResetEvent(ResetEvent e)
	{
		view.reset();
		score.set("0");
	}

	public Grid getGrid()
	{
		return grid;
	}

	public void undo()
	{
		undo.undo();
	}

}
