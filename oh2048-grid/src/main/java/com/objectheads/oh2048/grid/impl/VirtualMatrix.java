package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.MoveDirection;
import com.objectheads.oh2048.grid.Tile;

public class VirtualMatrix {

	private final int M;
	private final GridStatisticsImpl statistics;

	private Tile[][] matrix;

	public VirtualMatrix(final GridImpl grid, final GridStatisticsImpl statistics)
	{
		this.M = grid.getGridSize();
		matrix = new Tile[M][M];
		this.statistics = statistics;
	}

	private GridPosition convertToRealPosition(final VirtualPosition virtualPosition)
	{

		final int virtualRow = virtualPosition.getRow();
		final int virtualColumn = virtualPosition.getColumn();

		switch (virtualPosition.getDirection()) {
		case LEFT:
			return new GridPosition(virtualRow, virtualColumn);
		case DOWN:
			return new GridPosition(M - 1 - virtualColumn, virtualRow);
		case RIGHT:
			return new GridPosition(M - 1 - virtualRow, M - 1 - virtualColumn);
		case UP:
			return new GridPosition(virtualColumn, M - 1 - virtualRow);
		}
		throw new IllegalStateException();
	}

	public Tile virtualGet(final VirtualPosition virtualPosition)
	{
		final GridPosition matrixPosision = convertToRealPosition(virtualPosition);
		final int virtualRow = matrixPosision.getRow();
		final int virtualColumn = matrixPosision.getColumn();
		return matrix[virtualRow][virtualColumn];
	}

	public void virtualSet(final GridPosition position, final Tile tile, final EventBuilder eventBuilder)
	{
		final VirtualPosition virtualPosition = new VirtualPosition(position, MoveDirection.LEFT);
		virtualSet(virtualPosition, tile, eventBuilder);
	}

	private void virtualSet(final VirtualPosition virtualPosition, final Tile tile, final EventBuilder eventBuilder)
	{
		if (tile == null) {
			throw new IllegalArgumentException("tile parameter mustn't be null");
		}

		final GridPosition destPos = convertToRealPosition(virtualPosition);

		if (matrix[destPos.getRow()][destPos.getColumn()] != null) {
			throw new IllegalStateException(String.format("Cell is not empty [position=%s]", destPos));
		}

		final int virtualRow = destPos.getRow();
		final int virtualColumn = destPos.getColumn();
		matrix[virtualRow][virtualColumn] = tile;
		statistics.decreaseEmptyCellCounter();
		statistics.newTileCrteated(tile.getValue());

		eventBuilder.addNewTileCreateEvent(destPos, tile);

	}

	public void virtualMove(final VirtualPosition sourceVirtualPosition, final VirtualPosition targetVirtualPosition,
			final EventBuilder eventBuilder)
	{
		final GridPosition srcPos = convertToRealPosition(sourceVirtualPosition);
		final GridPosition destPos = convertToRealPosition(targetVirtualPosition);
		final Tile sourceTile = matrix[srcPos.getRow()][srcPos.getColumn()];

		checkVirtualMoveParameters(srcPos, destPos);

		matrix[destPos.getRow()][destPos.getColumn()] = sourceTile;
		matrix[srcPos.getRow()][srcPos.getColumn()] = null;

		eventBuilder.addMoveTileEvent(sourceTile, srcPos, destPos);
	}

	private void checkVirtualMoveParameters(final GridPosition sourcePosision, final GridPosition destinationPosision)
	{
		if (matrix[sourcePosision.getRow()][sourcePosision.getColumn()] == null) {
			final String message = String.format("Source grid cell must be not null! [sourcePosition=%s]",
					sourcePosision);
			throw new IllegalStateException(message);
		}

		if (matrix[destinationPosision.getRow()][destinationPosision.getColumn()] != null) {
			final String message = String.format("Source grid cell must be null! [destinationPosision=%s]",
					destinationPosision);
			throw new IllegalStateException(message);
		}
	}

	public void virtualJoin(final VirtualPosition sourceVirtualPosition, final VirtualPosition targetVirtualPosition,
			final Tile childTile, final EventBuilder eventBuilder)
	{
		final GridPosition sourceMatrixPosition = convertToRealPosition(sourceVirtualPosition);
		final GridPosition targetMatrixPosition = convertToRealPosition(targetVirtualPosition);
		final Tile sourceTile = matrix[sourceMatrixPosition.getRow()][sourceMatrixPosition.getColumn()];
		final Tile targetTile = matrix[targetMatrixPosition.getRow()][targetMatrixPosition.getColumn()];

		statistics.increaseEmptyCellCounter();
		checkVirtualJoinParameters(sourceMatrixPosition, targetMatrixPosition);

		matrix[targetMatrixPosition.getRow()][targetMatrixPosition.getColumn()] = childTile;
		matrix[sourceMatrixPosition.getRow()][sourceMatrixPosition.getColumn()] = null;

		eventBuilder.addDeleteTileEvent(targetTile, targetMatrixPosition);
		eventBuilder.addMoveTileEvent(sourceTile, sourceMatrixPosition, targetMatrixPosition);
		eventBuilder.addDeleteTileEvent(sourceTile, targetMatrixPosition);
		eventBuilder.addNewTileCreateEvent(targetMatrixPosition, childTile);

		final int newTileValue = childTile.getValue();
		statistics.incrementScore(newTileValue, eventBuilder);
	}

	private void checkVirtualJoinParameters(final GridPosition sourceMatrixPosision,
			final GridPosition targetMatrixPosision)
	{
		if (matrix[sourceMatrixPosision.getRow()][sourceMatrixPosision.getColumn()] == null) {
			final String message = String.format("Source grid cell must be not null! [sourceMatrixPosision=%s]",
					sourceMatrixPosision);
			throw new IllegalStateException(message);
		}

		if (matrix[targetMatrixPosision.getRow()][targetMatrixPosision.getColumn()] == null) {
			final String message = String.format("Target grid cell must be not null! [targetMatrixPosision=%s]",
					targetMatrixPosision);
			throw new IllegalStateException(message);
		}
	}

	public void virtualRemove(final VirtualPosition virtualPosition, final EventBuilder eventBuilder)
	{
		final GridPosition virtualPosision = convertToRealPosition(virtualPosition);
		final int virtualRow = virtualPosision.getRow();
		final int virtualColumn = virtualPosision.getColumn();
		final Tile tile = matrix[virtualRow][virtualColumn];
		matrix[virtualRow][virtualColumn] = null;
		statistics.increaseEmptyCellCounter();
		eventBuilder.addDeleteTileEvent(tile, virtualPosision);
	}

	protected Tile[][] getMatrix()
	{
		return matrix;
	}

	public void reset()
	{
		matrix = new Tile[M][M];
	}
}
