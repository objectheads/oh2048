package com.objectheads.oh2048.grid;

public interface GridMovement {

	void moveLeft();

	void moveRight();

	void moveUp();

	void moveDown();
	
	void move(final MoveDirection direction);

}
