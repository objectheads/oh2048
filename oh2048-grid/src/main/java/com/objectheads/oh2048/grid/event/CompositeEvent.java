package com.objectheads.oh2048.grid.event;

import java.util.ArrayList;
import java.util.List;

public class CompositeEvent implements Event {

	private final List<Event> events = new ArrayList<>();

	public void add(final Event event)
	{
		events.add(event);
	}

	public List<Event> get()
	{
		return events;
	}

	@Override
	public String toString()
	{
		return "CompositeEvent [events=" + events + "]";
	}

}
