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

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

		final ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_SPEED), node);
		st.setFromX(0.5f);
		st.setFromY(0.5f);
		st.setToX(1f);
		st.setToY(1f);
		st.setAutoReverse(false);
		st.setCycleCount(1);

		showTileNode(tileId, node);
		st.play();
	}

	public void deleteTileNode(final Tile tile)
	{
		final UUID tileId = tile.getId();
		final Node node = nodeMap.get(tileId);
		nodeMap.remove(tileId);

		final ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_SPEED), node);
		st.setFromX(1f);
		st.setFromY(1f);
		st.setToX(1.1f);
		st.setToY(1.1f);
		st.setAutoReverse(false);
		st.setCycleCount(1);
		st.setOnFinished(e -> getChildren().remove(node));
		st.play();
	}

	public void moveTileNode(final Tile tile, final GridPosition destinationGridPosition)
	{
		final UUID tileId = tile.getId();
		final Node node = nodeMap.get(tileId);

		final GridPosition destinationPosition = coordinateConverter.getWordPosition(destinationGridPosition);
		final TranslateTransition tt = new TranslateTransition(Duration.millis(TRANSITION_SPEED), node);
		tt.setToX(destinationPosition.getColumn() - node.getLayoutX());
		tt.setToY(destinationPosition.getRow() - node.getLayoutY());
		tt.play();
		tt.setInterpolator(Interpolator.EASE_OUT);
		tt.play();
	}

	public void createDescendantTileNode(final Tile tile, final GridPosition destinationPosition)
	{
		final UUID tileId = tile.getId();

		final GridPosition position = coordinateConverter.getWordPosition(destinationPosition);
		final Node node = new TileNode(tile, position);

		final ScaleTransition st = new ScaleTransition(Duration.millis(TRANSITION_SPEED / 2), node);
		st.setFromX(1f);
		st.setFromY(1f);
		st.setToX(1.2f);
		st.setToY(1.2f);
		st.setAutoReverse(true);
		st.setCycleCount(2);
		st.play();

		showTileNode(tileId, node);
	}

	public void reset()
	{
		for (final Entry<UUID, Node> entry : nodeMap.entrySet()) {
			final Node node = entry.getValue();

			final ParallelTransition pt = new ParallelTransition();

			final Duration duration = Duration.millis(TRANSITION_SPEED);
			final TranslateTransition tt = new TranslateTransition(duration, node);
			tt.setToY(200);
			tt.play();
			tt.setInterpolator(Interpolator.EASE_OUT);
			tt.setOnFinished(e -> getChildren().remove(node));

			final FadeTransition ft = new FadeTransition(duration, node);
			ft.setToValue(0);

			pt.getChildren().addAll(tt, ft);
			pt.play();

		}
		nodeMap.clear();
	}

}
