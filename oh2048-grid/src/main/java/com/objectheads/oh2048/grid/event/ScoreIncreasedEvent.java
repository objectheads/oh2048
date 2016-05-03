package com.objectheads.oh2048.grid.event;

public class ScoreIncreasedEvent implements Event {

	private final int newScore;
	private final int pointsAdded;

	public ScoreIncreasedEvent(final int newScore, final int pointsAdded)
	{
		this.newScore = newScore;
		this.pointsAdded = pointsAdded;
	}

	public int getNewScore()
	{
		return newScore;
	}

	public int getPointsAdded()
	{
		return pointsAdded;
	}

	@Override
	public String toString()
	{
		return "ScoreIncreasedEvent [newScore=" + newScore + ", pointsAdded=" + pointsAdded + "]";
	}

}
