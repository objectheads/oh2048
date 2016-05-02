package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.event.Event;

public class NoUndoImpl implements UndoService {

	@Override
	public void undo()
	{
		// NOP
	}

	@Override
	public boolean isEnabled()
	{
		return false;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		// NOP
	}

	@Override
	public void enable()
	{
		// NOP
	}

	@Override
	public void disable()
	{
		// NOP
	}

	@Override
	public void disableNext(int numberOfActions)
	{
		// NOP
	}

	@Override
	public void push(Event event)
	{
		// NOP
	}

}
