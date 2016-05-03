package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.Tile;

public class GridToString {

	public static String getGridAsString(final GridImpl grid)
	{
		final int M = grid.getGridSize();
		final Tile[][] matrix = grid.getMatrix();
		final StringBuilder sb = new StringBuilder();
		final int maxTileValueLength = Integer.toString(grid.getGridStatistics().getMaxTileValue()).length();

		for (int row = 0; row < M; row++) {
			for (int column = 0; column < M; column++) {
				final Tile tile = matrix[row][column];
				final String value = tile == null ? String.format("%" + (maxTileValueLength + 1) + "s", "-")
						: String.format("%" + (maxTileValueLength + 1) + "d", tile.getValue());
				sb.append(value);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
