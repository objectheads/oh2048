package com.objectheads.oh2048.ui;

import com.objectheads.oh2048.grid.Grid;
import com.objectheads.oh2048.grid.GridMovement;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController {

	private final GameModel model;

	public GameController(final GameModel model)
	{
		this.model = model;
	}

	public void onKeyPressed(final KeyEvent event)
	{
		final KeyCode code = event.getCode();
		move(code);
	}

	private void move(final KeyCode keyCode)
	{
		final Grid grid = model.getGrid();
		final GridMovement movement = grid.getGridMovement();
		switch (keyCode) {
		case UP:
			movement.moveUp();
			break;
		case DOWN:
			movement.moveDown();
			break;
		case LEFT:
			movement.moveLeft();
			break;
		case RIGHT:
			movement.moveRight();
			break;
		case Z:
			model.undo();
			break;
		case SPACE:
			model.initialize();
			break;
		default:
			break;
		}
	}

}
