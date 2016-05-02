package com.objectheads.oh2048.grid;

import java.util.UUID;

public class Tile {

	private final int value;
	private final UUID id;

	public Tile(final int value)
	{
		this.value = value;
		this.id = UUID.randomUUID();
	}

	public int getValue()
	{
		return value;
	}

	

	public UUID getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "Tile [value=" + value + ", id=" + id + "]";
	}

}
