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
import com.objectheads.oh2048.grid.event.PointsIncreasedEvent;
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
		gridEvents.setOnPointsIncreased(e -> handlePointsIncreasedEvent(e));
		gridEvents.setOnTargetReached(e -> handleTargetReachedEvent(e));
	}

	public void initialize()
	{
		grid.initialize();
		view.initialize();
		gameInitializer.initializeBoard(grid);
		view.scoreProperty().bindBidirectional(score);
	}

	public void handleNoMoreSteps(final NoMoreStepsEvent event)
	{
		view.gameOver();
		initialize();
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

	private void handlePointsIncreasedEvent(final PointsIncreasedEvent event)
	{
		score.set(Integer.toString(event.getNewPoints()));
	}

	private void handleTargetReachedEvent(final TargetReachedEvent e)
	{
		final boolean newGame = view.gameWin();
		if (newGame) {
			initialize();
		}
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
