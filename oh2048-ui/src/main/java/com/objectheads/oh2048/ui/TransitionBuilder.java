package com.objectheads.oh2048.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionBuilder {

	private static final int DEFAULT_SPEED = 200;

	private final boolean parallel;
	private final List<Transition> transitions = new ArrayList<>();

	private int duration = DEFAULT_SPEED;

	private TransitionBuilder(final boolean parallel)
	{
		this.parallel = parallel;
	}

	public static TransitionBuilder createParallel()
	{
		return new TransitionBuilder(true);
	}

	public static TransitionBuilder createSequential()
	{
		return new TransitionBuilder(false);
	}

	public TransitionBuilder setDuration(final int duration)
	{
		this.duration = duration;
		return this;
	}

	public Transition build()
	{

		if (transitions.isEmpty()) {
			throw new IllegalStateException("At least one transition must be added.");
		}

		if (transitions.size() == 1) {
			return transitions.get(0);
		}

		if (parallel) {
			return buildParallel();
		} else {
			return buildSequential();
		}

	}

	private Transition buildParallel()
	{
		final ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(transitions);
		return parallelTransition;
	}

	private Transition buildSequential()
	{
		final SequentialTransition sequentialTransition = new SequentialTransition();
		sequentialTransition.getChildren().addAll(transitions);
		return sequentialTransition;
	}

	public TransitionBuilder addScale(final double fromX, final double fromY, final double toX, final double toY,
			final int cycleCount, final boolean autoReverse, final Node node)
	{
		return addScale(fromX, fromY, toX, toY, cycleCount, autoReverse, node, null);
	}

	public TransitionBuilder addScale(final double fromX, final double fromY, final double toX, final double toY,
			final int cycleCount, final boolean autoReverse, final Node node,
			final EventHandler<ActionEvent> evetHandler)
	{
		final ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration / 2), node);
		scaleTransition.setFromX(1f);
		scaleTransition.setFromY(1f);
		scaleTransition.setToX(1.2f);
		scaleTransition.setToY(1.2f);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(2);
		if (evetHandler != null) {
			scaleTransition.setOnFinished(evetHandler);
		}
		transitions.add(scaleTransition);
		return this;
	}

	public TransitionBuilder addFade(final double fromValue, final double toValue, final Node node)
	{
		final FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
		fadeTransition.setFromValue(fromValue);
		fadeTransition.setToValue(toValue);
		transitions.add(fadeTransition);
		return this;
	}

	public TransitionBuilder addTranslate(final double toX, final double toY, final Node node,
			final EventHandler<ActionEvent> eventHandler)
	{
		final TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), node);
		translateTransition.setToX(toX);
		translateTransition.setToY(toY);
		translateTransition.setInterpolator(Interpolator.EASE_OUT);
		if (eventHandler != null) {
			translateTransition.setOnFinished(eventHandler);
		}
		transitions.add(translateTransition);
		return this;
	}

	public TransitionBuilder addTranslate(final double toX, final double toY, final Node node)
	{
		return addTranslate(toX, toY, node, null);
	}

}
