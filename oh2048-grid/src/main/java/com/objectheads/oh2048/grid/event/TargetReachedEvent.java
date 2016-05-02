package com.objectheads.oh2048.grid.event;

public class TargetReachedEvent implements Event {

	private final int oldValue;
	private final int newValue;

	public TargetReachedEvent(final int oldValue, final int newValue)
	{
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public int getOldValue()
	{
		return oldValue;
	}

	public int getNewValue()
	{
		return newValue;
	}

	@Override
	public String toString()
	{
		return "TargetReachedEvent [oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}

}
