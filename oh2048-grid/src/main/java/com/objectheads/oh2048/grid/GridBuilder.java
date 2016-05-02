package com.objectheads.oh2048.grid;

import com.objectheads.oh2048.grid.impl.GridImpl;

public class GridBuilder {

	private int M = 4;
	private boolean built = false;
	private int targetScore = 2048;
	private boolean disableUndo = false;

	public static GridBuilder create()
	{
		return new GridBuilder();
	}

	public Grid build()
	{
		if (built) {
			throw new IllegalStateException("Already built");
		}
		built = true;

		return new GridImpl(M, disableUndo, targetScore);
	}

	public GridBuilder setDimension(int M)
	{
		this.M = M;
		return this;
	}

	public GridBuilder disableUndo()
	{
		this.disableUndo = true;
		return this;
	}

	public GridBuilder setTargetScore(int targetScore)
	{
		this.targetScore = targetScore;
		return this;
	}

}
