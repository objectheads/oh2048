package com.objectheads.oh2048.grid.event;

public class MoveEvent implements Event {

	private final int moveCounter;

	public MoveEvent(final int moveCounter)
	{
		this.moveCounter = moveCounter;
	}

	public int getMoveCounter()
	{
		return moveCounter;
	}

	@Override
	public String toString()
	{
		return "MoveEvent [moveCounter=" + moveCounter + "]";
	}

	
}
