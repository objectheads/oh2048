package com.objectheads.oh2048.grid;

public interface Grid {

	void initialize();

	void add(GridPosition gridPosition, Tile newTile);

	void remove(GridPosition gridPosition);

	void addRandom();

	int getBoardDimension();

	GridEvent getGridEvent();

	GridMovement getGridMovement();

	GridUndo getGridUndo();

	GridStatistics getGridStatistics();
}