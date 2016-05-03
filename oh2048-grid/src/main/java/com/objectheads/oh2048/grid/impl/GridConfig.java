package com.objectheads.oh2048.grid.impl;

public class GridConfig {

	private final int gridSize;
	private final int targetScore;
	private final boolean disableUndo;

	public GridConfig(final int gridSize, final int targetScore, final boolean disableUndo)
	{
		this.gridSize = gridSize;
		this.targetScore = targetScore;
		this.disableUndo = disableUndo;
	}

	public int getGridSize()
	{
		return gridSize;
	}

	public int getTargetScore()
	{
		return targetScore;
	}

	public boolean isDisableUndo()
	{
		return disableUndo;
	}

}
