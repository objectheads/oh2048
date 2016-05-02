package com.objectheads.oh2048.grid;

public interface GridUndo {

	void undo();

	boolean isEnabled();

	void setEnabled(boolean enabled);

	void enable();

	void disable();

	void disableNext(int numberOfActions);

}