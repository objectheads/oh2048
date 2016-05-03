package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.EventHandler;
import com.objectheads.oh2048.grid.GridEvent;
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

public class EventDispatcher implements GridEvent {

	private final UndoService undo;

	private EventHandler<? super MoveEvent> moveEventHandler;
	private EventHandler<? super MoveTileEvent> moveTileEventHandler;
	private EventHandler<? super DeleteTileEvent> deleteTileEventHandler;
	private EventHandler<? super NoMoreStepsEvent> noMoreStepsEventHandler;
	private EventHandler<? super TargetReachedEvent> targetReachedEventHandler;
	private EventHandler<? super NewTileCreateEvent> newTileCreateEventHandler;
	private EventHandler<? super ScoreIncreasedEvent> scoreIncreasedEventHandler;
	private EventHandler<? super NewDescendantTileCreateEvent> newDescendantTileCreateHandler;

	public EventDispatcher(final UndoService undo)
	{
		this.undo = undo;
	}

	@Override
	public void setOnDeleteTile(final EventHandler<? super DeleteTileEvent> handler)
	{
		this.deleteTileEventHandler = handler;
	}

	@Override
	public void setOnMove(final EventHandler<? super MoveEvent> handler)
	{
		this.moveEventHandler = handler;
	}

	@Override
	public void setOnMoveTile(final EventHandler<? super MoveTileEvent> handler)
	{
		this.moveTileEventHandler = handler;
	}

	@Override
	public void setOnNewTileCreate(final EventHandler<? super NewTileCreateEvent> handler)
	{
		this.newTileCreateEventHandler = handler;
	}

	@Override
	public void setOnNoMoreSteps(final EventHandler<? super NoMoreStepsEvent> handler)
	{
		this.noMoreStepsEventHandler = handler;
	}

	@Override
	public void setOnNewDescendantTileCreate(final EventHandler<? super NewDescendantTileCreateEvent> handler)
	{
		this.newDescendantTileCreateHandler = handler;
	}

	@Override
	public void setOnScoreIncreased(final EventHandler<? super ScoreIncreasedEvent> handler)
	{
		this.scoreIncreasedEventHandler = handler;
	}

	@Override
	public void setOnTargetReached(final EventHandler<? super TargetReachedEvent> handler)
	{
		this.targetReachedEventHandler = handler;
	}

	public void fire(final Event event)
	{

		if (event instanceof CompositeEvent) {
			undo.push(event);
			processCompositeEvent((CompositeEvent)event);
			return;
		}

		if (event instanceof NewTileCreateEvent) {
			fireNewTileCreateEvent((NewTileCreateEvent)event);
			return;
		}

		if (event instanceof DeleteTileEvent) {
			fireDeleteTileEvent((DeleteTileEvent)event);
			return;
		}

		if (event instanceof NewDescendantTileCreateEvent) {
			fireNewDescendantTileCreateEvent((NewDescendantTileCreateEvent)event);
			return;
		}

		if (event instanceof MoveTileEvent) {
			fireMoveTileEvent((MoveTileEvent)event);
			return;
		}

		if (event instanceof NoMoreStepsEvent) {
			fireNoMoreStepsEvent((NoMoreStepsEvent)event);
			return;
		}

		if (event instanceof MoveEvent) {
			fireMoveEvent((MoveEvent)event);
			return;
		}

		if (event instanceof ScoreIncreasedEvent) {
			firePointsIncreasedEvent((ScoreIncreasedEvent)event);
			return;
		}

		if (event instanceof TargetReachedEvent) {
			fireTargetReachedEvent((TargetReachedEvent)event);
			return;
		}

	}

	private void processCompositeEvent(final CompositeEvent compositeEvent)
	{
		for (final Event event : compositeEvent.get()) {
			fire(event);
		}
	}

	private void fireDeleteTileEvent(final DeleteTileEvent event)
	{
		if (deleteTileEventHandler != null) {
			deleteTileEventHandler.handle(event);
		}
	}

	private void fireMoveTileEvent(final MoveTileEvent event)
	{
		if (moveTileEventHandler != null) {
			moveTileEventHandler.handle(event);
		}
	}

	private void fireMoveEvent(final MoveEvent event)
	{
		if (moveEventHandler != null) {
			moveEventHandler.handle(event);
		}
	}

	private void fireNewTileCreateEvent(final NewTileCreateEvent event)
	{
		if (newTileCreateEventHandler != null) {
			newTileCreateEventHandler.handle(event);
		}
	}

	private void fireNoMoreStepsEvent(final NoMoreStepsEvent event)
	{
		if (noMoreStepsEventHandler != null) {
			noMoreStepsEventHandler.handle(event);
		}
	}

	private void fireNewDescendantTileCreateEvent(final NewDescendantTileCreateEvent event)
	{
		if (newDescendantTileCreateHandler != null) {
			newDescendantTileCreateHandler.handle(event);
		}
	}

	private void firePointsIncreasedEvent(final ScoreIncreasedEvent event)
	{
		if (scoreIncreasedEventHandler != null) {
			scoreIncreasedEventHandler.handle(event);
		}
	}

	private void fireTargetReachedEvent(final TargetReachedEvent event)
	{
		if (targetReachedEventHandler != null) {
			targetReachedEventHandler.handle(event);
		}
	}

}
