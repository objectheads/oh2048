package com.objectheads.oh2048.grid;

import com.objectheads.oh2048.grid.event.Event;

public interface EventHandler<T extends Event>  {
	
    void handle(T event);
    
}
