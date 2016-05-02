package com.objectheads.oh2048.ui;

import static com.objectheads.oh2048.Constants.PADDING;

import com.objectheads.oh2048.grid.GridPosition;

public class CoordinateConverter {

	private final  int rectangleWidth;
	private final int rectangleHeight;

	public CoordinateConverter(final int rectangleWidth,final int rectangleHeight )
	{
		this.rectangleWidth = rectangleWidth;
		this.rectangleHeight = rectangleHeight;
	}

	public  GridPosition getWordPosition(final GridPosition gridPosition)
	{
		return getWordPosition(gridPosition.getRow(), gridPosition.getColumn());
	}

	public  GridPosition getWordPosition(final int row, final int column)
	{
		final int xPos = (row + 1) * PADDING + row * rectangleWidth;
		final int yPos = (column + 1) * PADDING + column * rectangleHeight;

		return new GridPosition(xPos, yPos);
	}
}
