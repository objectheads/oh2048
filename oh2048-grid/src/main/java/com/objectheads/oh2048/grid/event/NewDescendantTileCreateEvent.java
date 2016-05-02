package com.objectheads.oh2048.grid.event;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class NewDescendantTileCreateEvent implements Event {

	private final Tile tile;
	private final GridPosition destinationPosition;

	public NewDescendantTileCreateEvent(final Tile tile, final GridPosition destinationPosition)
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
		return "JoinTileEvent [tile=" + tile + ", destinationPosition=" + destinationPosition + "]";
	}

}
