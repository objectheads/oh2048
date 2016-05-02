package com.objectheads.oh2048.grid;

public class GridPosition {

	private int column;
	private int row;

	public GridPosition(int row, int column)
	{
		this.row = row;
		this.column = column;
	}

	public int getColumn()
	{
		return column;
	}

	public void setColumn(int column)
	{
		this.column = column;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	@Override
	public String toString()
	{
		return "Position [column=" + column + ", row=" + row + "]";
	}

}
