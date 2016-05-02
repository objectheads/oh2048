package com.objectheads.oh2048.ui;

import com.objectheads.oh2048.grid.Grid;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class TwoTilesInitializer implements GameInitializer {

	@Override
	public void initializeBoard(final Grid grid)
	{
		grid.getGridUndo().disableNext(2);
		grid.add(new GridPosition(0, 0), new Tile(2));
		grid.add(new GridPosition(0, 1), new Tile(2));
	}

}
