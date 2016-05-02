package com.objectheads.oh2048.grid.event;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class NewTileCreateEvent implements Event {

	private final Tile tile;
	private final GridPosition destinationPosition;

	public NewTileCreateEvent(final GridPosition destinationPosition, final Tile tile)
	{
		this.destinationPosition = destinationPosition;
		this.tile = tile;
	}

	public GridPosition getDestinationPosition()
	{
		return destinationPosition;
	}

	public Tile getTile()
	{
		return tile;
	}

	@Override
	public String toString()
	{
		return "NewTileCreateEvent [tile=" + tile + ", destinationPosition=" + destinationPosition + "]";
	}

}
