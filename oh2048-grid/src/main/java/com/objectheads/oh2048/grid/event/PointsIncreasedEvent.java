package com.objectheads.oh2048.grid.event;

public class PointsIncreasedEvent implements Event {

	private final int oldPoints;
	private final int newPoints;

	public PointsIncreasedEvent(final int oldPoints, final int newPoints)
	{
		this.oldPoints = oldPoints;
		this.newPoints = newPoints;
	}

	public int getOldPoints()
	{
		return oldPoints;
	}

	public int getNewPoints()
	{
		return newPoints;
	}

	@Override
	public String toString()
	{
		return "PointsIncreasedEvent [oldPoints=" + oldPoints + ", newPoints=" + newPoints + "]";
	}

}
