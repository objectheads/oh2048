package com.objectheads.oh2048.grid.impl;

import com.objectheads.oh2048.grid.GridUndo;
import com.objectheads.oh2048.grid.event.Event;

public interface UndoService extends GridUndo {

	void push(Event event);

	void reset();

}
