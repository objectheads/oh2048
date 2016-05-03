package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;
import com.objectheads.oh2048.grid.event.CompositeEvent;
import com.objectheads.oh2048.grid.event.DeleteTileEvent;
import com.objectheads.oh2048.grid.event.Event;
import com.objectheads.oh2048.grid.event.MoveEvent;
import com.objectheads.oh2048.grid.event.MoveTileEvent;
import com.objectheads.oh2048.grid.event.NewDescendantTileCreateEvent;
import com.objectheads.oh2048.grid.event.NewTileCreateEvent;
import com.objectheads.oh2048.grid.event.NoMoreStepsEvent;
import com.objectheads.oh2048.grid.event.ScoreIncreasedEvent;
import com.objectheads.oh2048.grid.event.TargetReachedEvent;

public class EventBuilder {

	private final CompositeEvent compositeEvent;

	private int moveCounter;
	private boolean built = false;

	private EventBuilder()
	{
		this.compositeEvent = new CompositeEvent();
	}

	public static EventBuilder create()
	{
		return new EventBuilder();
	}

	public Event build()
	{
		if (built) {
			throw new IllegalStateException("Already built");
		}
		built = true;

		return compositeEvent;
	}

	public int getMoveCounter()
	{
		return moveCounter;
	}

	private void add(final Event event)
	{
		compositeEvent.add(event);
	}

	public EventBuilder addMoveTileEvent(final Tile sourceTile, final GridPosition sourcePosition,
			final GridPosition destinationPosition)
	{
		final MoveTileEvent event = new MoveTileEvent(sourceTile, sourcePosition, destinationPosition);
		add(event);
		moveCounter++;
		return this;
	}

	public EventBuilder addJoinTilesEvent(final Tile tile, final GridPosition destinationPosition)
	{
		final NewDescendantTileCreateEvent event = new NewDescendantTileCreateEvent(tile, destinationPosition);
		add(event);
		moveCounter++;
		return this;
	}

	public EventBuilder addDeleteTileEvent(final Tile tile, final GridPosition gridPosition)
	{
		final DeleteTileEvent event = new DeleteTileEvent(tile, gridPosition);
		add(event);
		return this;
	}

	public EventBuilder addScoreIncreasedEvent(final int newScore, final int pointsAdded)
	{
		final ScoreIncreasedEvent event = new ScoreIncreasedEvent(newScore, pointsAdded);
		add(event);
		return this;
	}

	public EventBuilder addTargetReachedEvent(final int oldValue, final int newValue)
	{
		final TargetReachedEvent event = new TargetReachedEvent(oldValue, newValue);
		add(event);
		return this;
	}

	public EventBuilder addMoveEvent(final int moveCounter)
	{
		final MoveEvent event = new MoveEvent(moveCounter);
		add(event);
		return this;
	}

	public EventBuilder addNoMoreStepsEvent()
	{
		final NoMoreStepsEvent event = new NoMoreStepsEvent();
		add(event);
		return this;
	}

	public EventBuilder addNewTileCreateEvent(final GridPosition targetPosition, final Tile newTile)
	{
		final NewTileCreateEvent event = new NewTileCreateEvent(targetPosition, newTile);
		add(event);
		return this;
	}

}
