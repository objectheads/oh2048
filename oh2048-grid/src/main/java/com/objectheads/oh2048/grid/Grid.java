package com.objectheads.oh2048.grid;

public interface Grid {

	void reset();

	void add(int row, int column, int value);
	
	void add(GridPosition gridPosition, int value);
	
	void add(GridPosition gridPosition, Tile newTile);

	void remove(GridPosition gridPosition);

	void addRandom();

	int getGridSize();

	GridEvent getGridEvent();

	GridMovement getGridMovement();

	GridUndo getGridUndo();

	GridStatistics getGridStatistics();
}