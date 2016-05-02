package com.objectheads.oh2048.grid.impl;

import static com.objectheads.oh2048.grid.MoveDirection.DOWN;
import static com.objectheads.oh2048.grid.MoveDirection.LEFT;
import static com.objectheads.oh2048.grid.MoveDirection.RIGHT;
import static com.objectheads.oh2048.grid.MoveDirection.UP;

import com.objectheads.oh2048.grid.GridMovement;
import com.objectheads.oh2048.grid.MoveDirection;

public class MovementImpl implements GridMovement {

	private final GridImpl grid;

	public MovementImpl(final GridImpl grid)
	{
		this.grid = grid;
	}

	@Override
	public void moveLeft()
	{
		move(LEFT);
	}

	@Override
	public void moveRight()
	{
		move(RIGHT);
	}

	@Override
	public void moveUp()
	{
		move(UP);
	}

	@Override
	public void moveDown()
	{
		move(DOWN);
	}

	@Override
	public void move(final MoveDirection direction)
	{
		grid.move(direction);
	}

}
