package com.objectheads.oh2048.grid.impl;

import java.util.List;
import java.util.Random;

import com.objectheads.oh2048.grid.Grid;
import com.objectheads.oh2048.grid.GridBuilder;
import com.objectheads.oh2048.grid.GridEvent;
import com.objectheads.oh2048.grid.GridMovement;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.GridStatistics;
import com.objectheads.oh2048.grid.GridUndo;
import com.objectheads.oh2048.grid.MoveDirection;
import com.objectheads.oh2048.grid.Tile;
import com.objectheads.oh2048.grid.event.Event;

public class GridImpl implements Grid {

	private final GridConfig config;
	private final UndoService undo;
	private final GridMovement movement;
	private final VirtualMatrix virtualMatrix;
	private final GridStatisticsImpl statistics;
	private final EventDispatcher eventDispatcher;

	private final Random random;

	public GridImpl(final int gridSize, final boolean disableUndo, final int targetScore)
	{
		this.config = new GridConfig(gridSize, targetScore, disableUndo);
		this.movement = new MovementImpl(this);
		this.statistics = new GridStatisticsImpl(this);
		this.undo = config.isDisableUndo() ? new NoUndoImpl() : new UndoImpl(this, statistics);
		this.eventDispatcher = new EventDispatcher(undo);
		this.virtualMatrix = new VirtualMatrix(this, statistics);
		random = new Random(System.currentTimeMillis());
		reset();
	}

	@Override
	public int getGridSize()
	{
		return config.getGridSize();
	}

	@Override
	public void reset()
	{
		statistics.reset();
		undo.reset();
		virtualMatrix.reset();
		final EventBuilder eventBuilder = EventBuilder.create().addResetEvent();
		eventDispatcher.fire(eventBuilder.build());
	}

	@Override
	public void addRandom()
	{
		final EventBuilder eventBuilder = EventBuilder.create();
		addRandom(eventBuilder);
		eventDispatcher.fire(eventBuilder.build());
	}

	protected EventDispatcher geEventDispatcher()
	{
		return eventDispatcher;
	}

	private void addRandom(final EventBuilder eventBuilder)
	{
		final int randomTileValue = random.nextDouble() < 0.25d ? 4 : 2;
		final List<GridPosition> freeCells = statistics.getFreeCellIndecies();

		if (freeCells.isEmpty()) {
			return;
		}

		final int randomFreeCellIndex = random.nextInt(freeCells.size());
		final GridPosition targetPosition = freeCells.get(randomFreeCellIndex);
		final Tile newTile = new Tile(randomTileValue);
		virtualMatrix.virtualSet(targetPosition, newTile, eventBuilder);
	}

	@Override
	public void remove(final GridPosition gridPosition)
	{
		final EventBuilder eventBuilder = EventBuilder.create();
		remove(gridPosition, eventBuilder);
		eventDispatcher.fire(eventBuilder.build());
	}

	public void remove(final GridPosition gridPosition, final EventBuilder eventBuilder)
	{
		final VirtualPosition virtualPosition = new VirtualPosition(gridPosition, MoveDirection.LEFT);
		virtualMatrix.virtualRemove(virtualPosition, eventBuilder);
	}

	protected void move(final Tile newTile, final GridPosition sourcePosition, final GridPosition destinationPosition)
	{
		final EventBuilder eventBuilder = EventBuilder.create();
		move(newTile, sourcePosition, destinationPosition, eventBuilder);
		eventDispatcher.fire(eventBuilder.build());
	}

	public void move(final Tile newTile, final GridPosition sourcePosition, final GridPosition destinationPosition,
			final EventBuilder eventBuilder)
	{
		final VirtualPosition virtualSourcePosition = new VirtualPosition(sourcePosition, MoveDirection.LEFT);
		final VirtualPosition virtualDestinationPosition = new VirtualPosition(destinationPosition, MoveDirection.LEFT);
		virtualMatrix.virtualMove(virtualSourcePosition, virtualDestinationPosition, eventBuilder);
	}

	@Override
	public void add(final GridPosition gridPosition, final Tile newTile)
	{
		final EventBuilder eventBuilder = EventBuilder.create();
		add(gridPosition, newTile, eventBuilder);
		eventDispatcher.fire(eventBuilder.build());
	}

	@Override
	public void add(final GridPosition gridPosition, final int value)
	{
		add(gridPosition, new Tile(value));
	}

	@Override
	public void add(final int row, final int column, final int value)
	{
		add(new GridPosition(row, column), new Tile(value));
	}

	public void add(final GridPosition gridPosition, final Tile newTile, final EventBuilder eventBuilder)
	{
		final VirtualPosition virtualPosition = new VirtualPosition(gridPosition, MoveDirection.LEFT);
		virtualMatrix.virtualSet(virtualPosition, newTile, eventBuilder);
	}

	protected void move(final MoveDirection direction)
	{
		final EventBuilder eventBuilder = EventBuilder.create();
		doMovements(direction, eventBuilder);
		checkAndFireMovement(eventBuilder);
		addNextTile(eventBuilder);
		checkAndFireNoMoreSteps(eventBuilder);
		checkAndFireTargetReached(eventBuilder);
		final Event event = eventBuilder.build();
		eventDispatcher.fire(event);
	}

	private void checkAndFireTargetReached(final EventBuilder eventBuilder)
	{
		if (statistics.getMaxTileValue() >= config.getTargetScore() && !statistics.isTargetReached()) {
			statistics.setTargetReached(true);
			eventBuilder.addTargetReachedEvent(statistics.getMaxTileValue(), statistics.getMaxTileValue());
		}
	}

	@Override
	public GridUndo getGridUndo()
	{
		return undo;
	}

	@Override
	public GridMovement getGridMovement()
	{
		return movement;
	}

	private void addNextTile(final EventBuilder eventBuilder)
	{
		if (eventBuilder.getMoveCounter() > 0) {
			addRandom(eventBuilder);
		}
	}

	private void doMovements(final MoveDirection direction, final EventBuilder eventBuilder)
	{
		for (int row = 0; row < config.getGridSize(); row++) {
			processRow(row, direction, eventBuilder);
		}
	}

	private void checkAndFireMovement(final EventBuilder eventBuilder)
	{
		if (eventBuilder.getMoveCounter() > 0) {
			eventBuilder.addMoveEvent(eventBuilder.getMoveCounter());
		}
	}

	private void checkAndFireNoMoreSteps(final EventBuilder eventBuilder)
	{
		if (!statistics.hasMoreSteps()) {
			eventBuilder.addNoMoreStepsEvent();
		}
	}

	private void processRow(final int row, final MoveDirection direction, final EventBuilder eventBuilder)
	{
		for (int i = 0; i < config.getGridSize(); i++) {
			final VirtualPosition virtualPosition = new VirtualPosition(row, i, direction);

			Tile currentTile = virtualMatrix.virtualGet(virtualPosition);
			if (currentTile == null) {
				moveTileToFreeCell(virtualPosition, eventBuilder);
			}

			currentTile = virtualMatrix.virtualGet(virtualPosition);
			if (currentTile != null) {
				moveOrJoinTiles(virtualPosition, currentTile, eventBuilder);
			}
		}
	}

	private void moveTileToFreeCell(final VirtualPosition targetVirtualPosition, final EventBuilder eventBuilder)
	{
		final int targetVirtualRow = targetVirtualPosition.getRow();
		final MoveDirection direction = targetVirtualPosition.getDirection();
		for (int j = targetVirtualPosition.getColumn() + 1; j < config.getGridSize(); j++) {
			final VirtualPosition sourceVirtualPosition = new VirtualPosition(targetVirtualRow, j, direction);
			final Tile sourceTile = virtualMatrix.virtualGet(sourceVirtualPosition);
			if (sourceTile != null) {
				virtualMatrix.virtualMove(sourceVirtualPosition, targetVirtualPosition, eventBuilder);
				break;
			}
		}
	}

	private void moveOrJoinTiles(final VirtualPosition virtualPosition, final Tile targetTile,
			final EventBuilder eventBuilder)
	{
		final int currentRow = virtualPosition.getRow();
		final int currentColumn = virtualPosition.getColumn();
		final MoveDirection direction = virtualPosition.getDirection();

		for (int j = currentColumn + 1; j < config.getGridSize(); j++) {
			final VirtualPosition sourceVirtualPosition = new VirtualPosition(currentRow, j, direction);
			final Tile sourceTile = virtualMatrix.virtualGet(sourceVirtualPosition);

			if (sourceTile != null && targetTile.getValue() != sourceTile.getValue()) {
				break;
			}

			if (sourceTile != null && targetTile.getValue() == sourceTile.getValue()) {
				final int newTileValue = targetTile.getValue() * 2;
				final Tile childTile = new Tile(newTileValue);
				final VirtualPosition targetVirtualPosition = new VirtualPosition(currentRow, currentColumn, direction);
				virtualMatrix.virtualJoin(sourceVirtualPosition, targetVirtualPosition, childTile, eventBuilder);
				break;
			}
		}

	}

	@Override
	public GridEvent getGridEvent()
	{
		return eventDispatcher;
	}

	@Override
	public GridStatistics getGridStatistics()
	{
		return statistics;
	}

	@Override
	public String toString()
	{
		return GridToString.getGridAsString(this);
	}

	protected Tile[][] getMatrix()
	{
		return virtualMatrix.getMatrix();
	}

	public static void main(final String[] args)
	{
		final Grid grid = GridBuilder.create().setDimension(4).build();
		grid.add(new GridPosition(0, 0), new Tile(8192));
		grid.add(new GridPosition(1, 0), new Tile(8192));
		System.out.println(grid);
		grid.getGridMovement().moveDown();
		System.out.println(grid);

	}

}
