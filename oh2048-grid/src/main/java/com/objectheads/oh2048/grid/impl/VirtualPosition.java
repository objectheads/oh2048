package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.MoveDirection;

public class VirtualPosition extends GridPosition {

	private final MoveDirection direction;

	public VirtualPosition(final GridPosition position, final MoveDirection direction)
	{
		this(position.getRow(), position.getColumn(), direction);
	}

	public VirtualPosition(final int row, final int column, final MoveDirection direction)
	{
		super(row, column);
		this.direction = direction;
	}

	public MoveDirection getDirection()
	{
		return direction;
	}

	@Override
	public String toString()
	{
		return "VirtualPosition [direction=" + direction + ", getColumn()=" + getColumn() + ", getRow()=" + getRow()
				+ "]";
	}

}
