package com.objectheads.oh2048.grid.event;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class MoveTileEvent implements Event {

	private final Tile tile;
	private final GridPosition sourcePosition;
	private final GridPosition destinationPosition;

	public MoveTileEvent(final Tile tile, final GridPosition sourcePosition, final GridPosition destinationPosition)
	{
		this.tile = tile;
		this.sourcePosition = sourcePosition;
		this.destinationPosition = destinationPosition;
	}

	public Tile getTile()
	{
		return tile;
	}

	public GridPosition getDestinationPosition()
	{
		return destinationPosition;
	}

	public GridPosition getSourcePosition()
	{
		return sourcePosition;
	}

	@Override
	public String toString()
	{
		return "MoveTileEvent [sourceTile=" + tile + ", sourcePosition=" + sourcePosition
				+ ", destinationPosition=" + destinationPosition + "]";
	}

}
