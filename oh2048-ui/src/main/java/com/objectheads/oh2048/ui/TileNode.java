package com.objectheads.oh2048.ui;

import static com.objectheads.oh2048.Constants.PADDING;
import static com.objectheads.oh2048.Constants.SCREEN_HEIGHT;
import static com.objectheads.oh2048.Constants.SCREEN_WIDTH;

import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TileNode extends StackPane {

	public TileNode(Tile tile, GridPosition position)
	{
		setId(tile.getId().toString());
		final int rectangleWidth = (SCREEN_WIDTH - 5 * PADDING) / 4;
		final int rectangleHeight = (SCREEN_HEIGHT - 5 * PADDING) / 4;

		final Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
		int value = tile.getValue();
		rectangle.setFill(getBackground(value));
		rectangle.setArcWidth(10);
		rectangle.setArcHeight(10);
		setLayoutX(position.getColumn());
		setLayoutY(position.getRow());

		getChildren().add(rectangle);

		final Text text = new Text(Integer.toString(value));

		int fontSize = 55;
		if (value > 64) {
			fontSize = 45;
		}
		if (value > 512) {
			fontSize = 35;
		}

		text.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
		if (value > 4) {
			text.setFill(Color.web("#f9f6f2"));
		} else {
			text.setFill(Color.web("#776e65"));
		}
		getChildren().add(text);

	}

	public Color getBackground(final int value)
	{
		switch (value) {
		case 2:
			return Color.web("#eee4da");
		case 4:
			return Color.web("#ede0c8");
		case 8:
			return Color.web("#f2b179");
		case 16:
			return Color.web("#f59563");
		case 32:
			return Color.web("#f67c5f");
		case 64:
			return Color.web("#f65e3b");
		case 128:
			return Color.web("#edcf72");
		case 256:
			return Color.web("#edcc61");
		case 512:
			return Color.web("#edc850");
		case 1024:
			return Color.web("#edc53f");
		case 2048:
			return Color.web("#edc22e");
		}
		return Color.web("#cdc1b4");
	}

}
