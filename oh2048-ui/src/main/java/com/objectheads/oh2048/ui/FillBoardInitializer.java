package com.objectheads.oh2048.ui;

import com.objectheads.oh2048.grid.Grid;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

public class FillBoardInitializer implements GameInitializer {

	@Override
	public void initializeBoard(final Grid grid)
	{
		int M = grid.getGridSize();
		int counter = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				if (i != 0 || j != 0) {
					grid.add(new GridPosition(i, j), new Tile(counter++));
				}
			}
		}

	}

}
