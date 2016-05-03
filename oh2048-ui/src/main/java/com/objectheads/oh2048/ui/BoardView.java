package com.objectheads.oh2048.ui;

import static com.objectheads.oh2048.Constants.PADDING;
import static com.objectheads.oh2048.Constants.SCREEN_HEIGHT;
import static com.objectheads.oh2048.Constants.SCREEN_WIDTH;
import static com.objectheads.oh2048.Constants.TRANSITION_SPEED;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.objectheads.oh2048.Constants;
import com.objectheads.oh2048.grid.GridPosition;
import com.objectheads.oh2048.grid.Tile;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardView extends AnchorPane {

	private final int rectangleWidth;
	private final int rectangleHeight;
	private final Map<UUID, Node> nodeMap;
	private final CoordinateConverter coordinateConverter;

	public BoardView()
	{
		this.nodeMap = new ConcurrentHashMap<>();

		rectangleWidth = (SCREEN_WIDTH - 5 * PADDING) / 4;
		rectangleHeight = (SCREEN_HEIGHT - 5 * PADDING) / 4;
		this.coordinateConverter = new CoordinateConverter(rectangleWidth, rectangleHeight);

		setMinWidth(SCREEN_WIDTH);
		setMinHeight(SCREEN_HEIGHT);

		initialize();
	}

	public void initialize()
	{
		setupGrid();
	}

	private void setupBase()
	{
		final Rectangle base = new Rectangle();
		base.setWidth(Constants.SCREEN_WIDTH);
		base.setHeight(Constants.SCREEN_HEIGHT);
		base.setFill(Color.web("#bbada0"));
		getChildren().add(base);
	}

	private void setupGrid()
	{
		setupBase();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				final GridPosition position = coordinateConverter.getWordPosition(i, j);
				final Node baseTile = createBaseTile(position);
				getChildren().add(baseTile);
			}
		}
	}

	private Rectangle createBaseTile(final GridPosition position)
	{
		final Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
		rectangle.setX(position.getColumn());
		rectangle.setY(position.getRow());
		rectangle.setFill(Color.web("#cdc1b4"));
		rectangle.setArcWidth(10);
		rectangle.setArcHeight(10);
		return rectangle;
	}

	private void showTileNode(final UUID tileId, final Node node)
	{
		nodeMap.put(tileId, node);
		getChildren().add(node);
	}

	public void createTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		final UUID tileId = tile.getId();

		final GridPosition position = coordinateConverter.getWordPosition(destinationPosition);
		final Node node = new TileNode(tile, position);

		TransitionBuilder.createParallel().addScale(0.5f, 0.5f, 1f, 1f, 1, false, node).build().play();
		showTileNode(tileId, node);
	}

	public void deleteTileNode(final Tile tile)
	{
		final UUID tileId = tile.getId();
		final Node node = nodeMap.get(tileId);
		nodeMap.remove(tileId);
		TransitionBuilder.createParallel().addScale(1f, 1f, 1.2f, 1.2f, 1, false, node, e -> getChildren().remove(node))
				.build().play();

	}

	public void moveTileNode(final Tile tile, final GridPosition destinationGridPosition)
	{
		final UUID tileId = tile.getId();
		final Node node = nodeMap.get(tileId);

		final GridPosition destinationPosition = coordinateConverter.getWordPosition(destinationGridPosition);
		final double toX = destinationPosition.getColumn() - node.getLayoutX();
		final double toY = destinationPosition.getRow() - node.getLayoutY();
		TransitionBuilder.createParallel().setDuration(TRANSITION_SPEED).addTranslate(toX, toY, node).build().play();

	}

	public void createDescendantTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		final UUID tileId = tile.getId();
		final GridPosition position = coordinateConverter.getWordPosition(destinationPosition);
		final Node node = new TileNode(tile, position);

		TransitionBuilder.createSequential().setDuration(TRANSITION_SPEED).addScale(1f, 1f, 1.2f, 1.2f, 2, true, node)
				.build().play();

		showTileNode(tileId, node);
	}

	public void reset()
	{
		for (final Entry<UUID, Node> entry : nodeMap.entrySet()) {
			final Node node = entry.getValue();

			final TransitionBuilder transitionBuilder = TransitionBuilder.createParallel()
					.setDuration(TRANSITION_SPEED);
			transitionBuilder.addTranslate(0, 200, node, e -> getChildren().remove(node));
			transitionBuilder.addFade(1, 0, node);
			transitionBuilder.build().play();
		}
		nodeMap.clear();
	}

}
