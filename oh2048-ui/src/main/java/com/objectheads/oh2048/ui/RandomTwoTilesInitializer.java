package com.objectheads.oh2048.ui;

import com.objectheads.oh2048.grid.Grid;

public class RandomTwoTilesInitializer implements GameInitializer {

	@Override
	public void initializeBoard(final Grid grid)
	{
		grid.getGridUndo().disableNext(2);
		grid.addRandom();
		grid.addRandom();
	}

}
