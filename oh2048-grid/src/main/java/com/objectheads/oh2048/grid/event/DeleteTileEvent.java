package com.objectheads.oh2048.grid.event;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class DeleteTileEvent implements Event {

	private final Tile tile;
	private final GridPosition gridPosition;

	public DeleteTileEvent(final Tile tile, final GridPosition gridPosition)
	{
		this.tile = tile;
		this.gridPosition = gridPosition;
	}

	public Tile getTile()
	{
		return tile;
	}



	public GridPosition getGridPosition()
	{
		return gridPosition;
	}

	@Override
	public String toString()
	{
		return "DeleteEvent [tile=" + tile + "]";
	}


}
