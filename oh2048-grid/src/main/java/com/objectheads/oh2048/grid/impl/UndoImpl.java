package com.objectheads.oh2048.grid.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;
import com.objectheads.oh2048.grid.event.CompositeEvent;
import com.objectheads.oh2048.grid.event.DeleteTileEvent;
import com.objectheads.oh2048.grid.event.Event;
import com.objectheads.oh2048.grid.event.MoveTileEvent;
import com.objectheads.oh2048.grid.event.NewTileCreateEvent;
import com.objectheads.oh2048.grid.event.ScoreIncreasedEvent;

public class UndoImpl implements UndoService {

	private final GridImpl grid;
	private final Stack<Event> events = new Stack<>();

	private int numberOfActions;
	private GridStatisticsImpl statistics;

	private boolean enabled = true;
	private boolean disabledNext = false;
	private boolean undoOperation = false;

	public UndoImpl(final GridImpl grid, GridStatisticsImpl statistics)
	{
		this.grid = grid;
		this.statistics = statistics;
	}

	@Override
	public void push(final Event event)
	{
		if (checkPushEnabled()) {
			if (event instanceof CompositeEvent && ((CompositeEvent)event).get().size() == 0) {
				return;
			}
			events.push(event);
		}
	}

	private boolean checkPushEnabled()
	{

		final boolean isPushEnabled = !disabledNext && !undoOperation && enabled;

		if (disabledNext) {
			numberOfActions--;
			if (numberOfActions == 0) {
				disabledNext = false;
			}
		}

		return isPushEnabled;
	}

	@Override
	public void undo()
	{
		try {
			undoOperation = true;
			final List<Event> lastEvents = getLastEvents();
			doUndo(lastEvents);
		} finally {
			undoOperation = false;
		}
	}

	private List<Event> getLastEvents()
	{
		if (!events.isEmpty()) {
			final Event event = events.pop();
			final List<Event> result = (event instanceof CompositeEvent) ? ((CompositeEvent)event).get()
					: Arrays.asList(event);
			return result.size() > 0 ? result : Collections.emptyList();
		} else {
			return Collections.emptyList();
		}
	}

	private void doUndo(final List<Event> lastEvents)
	{
		Collections.reverse(lastEvents);
		for (final Event event : lastEvents) {
			undoEvent(event);
		}
	}

	private void undoEvent(final Event event)
	{
		if (event instanceof NewTileCreateEvent) {
			final NewTileCreateEvent newTileCreateEvent = (NewTileCreateEvent)event;
			final GridPosition position = newTileCreateEvent.getDestinationPosition();
			grid.remove(position);
			return;
		}

		if (event instanceof DeleteTileEvent) {
			final DeleteTileEvent deleteTileEvent = (DeleteTileEvent)event;
			final GridPosition position = deleteTileEvent.getGridPosition();
			final Tile tile = deleteTileEvent.getTile();
			grid.add(position, tile);
			return;
		}

		if (event instanceof MoveTileEvent) {
			final MoveTileEvent moveTileEvent = (MoveTileEvent)event;
			final Tile tile = moveTileEvent.getTile();
			grid.move(tile, moveTileEvent.getDestinationPosition(), moveTileEvent.getSourcePosition());
			return;
		}

		if (event instanceof ScoreIncreasedEvent) {
			final ScoreIncreasedEvent pointsIncreasedEvent = (ScoreIncreasedEvent)event;
			final EventBuilder eventBuilder = EventBuilder.create();
			final int score = pointsIncreasedEvent.getNewScore() - pointsIncreasedEvent.getPointsAdded();
			final int pointsAdded = -1 * pointsIncreasedEvent.getPointsAdded();
			eventBuilder.addScoreIncreasedEvent(score, pointsAdded);
			grid.geEventDispatcher().fire(eventBuilder.build());
			statistics.setScore(score);
			return;
		}

	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public void enable()
	{
		this.enabled = true;
	}

	@Override
	public void disable()
	{
		this.enabled = false;
	}

	@Override
	public void disableNext(final int numberOfActions)
	{
		checkArgument(numberOfActions > 0, "Number of actions must be greater than zero!");
		this.numberOfActions = numberOfActions;
		this.disabledNext = true;
	}

	@Override
	public void reset()
	{
		events.clear();
		enabled = true;
		disabledNext = false;
		undoOperation = false;
	}

}
