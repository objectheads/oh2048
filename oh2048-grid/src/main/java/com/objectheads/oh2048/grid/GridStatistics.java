package com.objectheads.oh2048.grid;

import java.util.List;
import java.util.Set;

public interface GridStatistics {

	int getMaxTileValue();

	int getScore();

	int getMovementsCounter();

	int getEmptyCellCounter();

	Set<MoveDirection> getAvailableDirections();

	boolean hasMoreSteps();

	List<GridPosition> getFreeCellIndecies();

}
