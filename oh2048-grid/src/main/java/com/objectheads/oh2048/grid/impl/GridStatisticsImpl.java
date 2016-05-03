package com.objectheads.oh2048.grid.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.GridStatistics;
import com.objectheads.oh2048.grid.MoveDirection;
import com.objectheads.oh2048.grid.Tile;

public class GridStatisticsImpl implements GridStatistics {

	private final GridImpl grid;

	private int score;
	private int maxTileValue;
	private int movementsCounter;
	private int emptyCellCounter;

	public GridStatisticsImpl(final GridImpl gridImpl)
	{
		this.grid = gridImpl;
		reset();
	}

	public void reset()
	{
		score = 0;
		maxTileValue = 0;
		movementsCounter = 0;
		final int boardDimension = grid.getBoardDimension();
		emptyCellCounter = boardDimension * boardDimension;
	}

	@Override
	public int getMaxTileValue()
	{
		return maxTileValue;
	}

	protected void newTileCrteated(final int newTileValue)
	{
		if (maxTileValue <= newTileValue) {
			maxTileValue = newTileValue;
		}
	}

	@Override
	public int getScore()
	{
		return score;
	}

	protected void setScore(int score)
	{
		this.score = score;
	}

	protected void incrementScore(final int value, final EventBuilder eventBuilder)
	{
		newTileCrteated(value);
		score = score + value;
		eventBuilder.addScoreIncreasedEvent(score, value);
	}

	@Override
	public int getMovementsCounter()
	{
		return movementsCounter;
	}

	protected void incrementMovementsCounter()
	{
		movementsCounter++;
	}

	@Override
	public int getEmptyCellCounter()
	{
		return emptyCellCounter;
	}

	protected void increaseEmptyCellCounter()
	{
		emptyCellCounter++;
	}

	protected void decreaseEmptyCellCounter()
	{
		emptyCellCounter--;
	}

	@Override
	public Set<MoveDirection> getAvailableDirections()
	{
		if (emptyCellCounter > 0) {
			return Sets.immutableEnumSet(MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.DOWN);
		}
		final Set<MoveDirection> directions = new HashSet<>();
		directions.addAll(getHorizontalDirections());
		directions.addAll(getVerticalDirections());

		return directions;
	}

	private Collection<? extends MoveDirection> getHorizontalDirections()
	{
		final Tile[][] matrix = grid.getMatrix();
		final int M = grid.getBoardDimension();
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M - 1; j++) {
				if (matrix[i][j].getValue() == matrix[i][j + 1].getValue()) {
					return Sets.immutableEnumSet(MoveDirection.LEFT, MoveDirection.RIGHT);
				}
			}
		}
		return Collections.emptySet();
	}

	private Collection<? extends MoveDirection> getVerticalDirections()
	{
		final Tile[][] matrix = grid.getMatrix();
		final int M = grid.getBoardDimension();
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M - 1; j++) {
				if (matrix[j][i].getValue() == matrix[j + 1][i].getValue()) {
					return Sets.immutableEnumSet(MoveDirection.UP, MoveDirection.DOWN);
				}
			}
		}
		return Collections.emptySet();
	}

	@Override
	public boolean hasMoreSteps()
	{
		final Set<MoveDirection> directions = getAvailableDirections();
		return !directions.isEmpty();
	}

	@Override
	public List<GridPosition> getFreeCellIndecies()
	{

		final Tile[][] matrix = grid.getMatrix();
		final int M = grid.getBoardDimension();
		final List<GridPosition> indicies = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				if (matrix[i][j] == null) {
					final GridPosition gridPosition = new GridPosition(i, j);
					indicies.add(gridPosition);
				}

			}
		}
		return indicies;
	}

}
