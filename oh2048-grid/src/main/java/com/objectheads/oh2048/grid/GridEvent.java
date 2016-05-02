package com.objectheads.oh2048.grid;

import com.objectheads.oh2048.grid.event.DeleteTileEvent;
import com.objectheads.oh2048.grid.event.MoveEvent;
import com.objectheads.oh2048.grid.event.MoveTileEvent;
import com.objectheads.oh2048.grid.event.NewDescendantTileCreateEvent;
import com.objectheads.oh2048.grid.event.NewTileCreateEvent;
import com.objectheads.oh2048.grid.event.NoMoreStepsEvent;
import com.objectheads.oh2048.grid.event.PointsIncreasedEvent;
import com.objectheads.oh2048.grid.event.TargetReachedEvent;

public interface GridEvent {

	void setOnNewTileCreate(EventHandler<? super NewTileCreateEvent> handler);

	void setOnDeleteTile(EventHandler<? super DeleteTileEvent> handler);

	void setOnNewDescendantTileCreate(EventHandler<? super NewDescendantTileCreateEvent> handler);

	void setOnMoveTile(EventHandler<? super MoveTileEvent> handler);

	void setOnNoMoreSteps(EventHandler<? super NoMoreStepsEvent> handler);

	void setOnMove(EventHandler<? super MoveEvent> handler);

	void setOnPointsIncreased(EventHandler<? super PointsIncreasedEvent> handler);
	
	void setOnTargetReached(EventHandler<? super TargetReachedEvent> handler);

}