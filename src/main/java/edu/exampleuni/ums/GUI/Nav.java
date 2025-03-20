package edu.exampleuni.ums.GUI;

import javafx.animation.Animation.*;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.util.*;
import javafx.scene.input.*;


public class Nav extends SkinBase<TitledPane>  {

	private static final Duration TRANSITION_DURATION = new Duration(350.0);

	private final StackPane bar;
	private final StackPane contentContainer;
	private final Region arrow;
	private Node content;
	private Timeline timeline;
	private double transitionStartValue = 0;
	private final Rectangle clipRect = new Rectangle();
	private DoubleProperty transition;
	private double getTransition() { return transition == null ? 0.0 : transition.get(); }


	public Nav(final TitledPane control) {
		super(control);

		arrow = new Region();
		arrow.getStyleClass().setAll("navArrow");

		arrow.rotateProperty().bind(transitionProperty().add(-1.0).multiply(-90.));

		bar = new StackPane(arrow);
		bar.getStyleClass().setAll("navButton");
		bar.setAlignment(Pos.CENTER);

		bar.setOnMouseReleased(e -> {
			if( e.getButton() != MouseButton.PRIMARY ) return;
			ContextMenu contextMenu = getSkinnable().getContextMenu() ;
			if (contextMenu != null) {
				contextMenu.hide();
			}
			control.setExpanded(!control.isExpanded());
		});

		bar.getChildren().clear();
		bar.getChildren().add(arrow);
		bar.setCursor(Cursor.HAND);

		content = getSkinnable().getContent();
		contentContainer = new StackPane();
		if (content != null) contentContainer.getChildren().setAll(content);

		contentContainer.setClip(clipRect);
		updateClip();

		if (control.isExpanded()) {
			transitionProperty().set(1.0f);
			setExpanded(control.isExpanded());
		} else {
			transitionProperty().set(0.0f);
			if (content != null) content.setVisible(false);
		}

		getChildren().setAll(contentContainer, bar);

		registerChangeListener(control.contentProperty(), e -> {
			content = getSkinnable().getContent();
			if (content == null) {
				contentContainer.getChildren().clear();
			} else {
				contentContainer.getChildren().setAll(content);
			}
		});
		registerChangeListener(control.expandedProperty(), e -> setExpanded(getSkinnable().isExpanded()));
		registerChangeListener(control.collapsibleProperty(), e -> {
			bar.getChildren().clear();
			bar.getChildren().add(arrow);
			bar.setCursor(Cursor.HAND);
		});
		registerChangeListener(control.widthProperty(), e -> updateClip());
		registerChangeListener(control.heightProperty(), e -> updateClip());
	}

	private DoubleProperty transitionProperty() {
		if (transition == null) {
			transition = new SimpleDoubleProperty(this, "transition", 0.0) {
				@Override protected void invalidated() {
					contentContainer.requestLayout();
				}
			};
		}
		return transition;
	}

	/** {@inheritDoc} */
	@Override protected void layoutChildren(double x, double y,
											final double w, final double h) {

		double barWidth = snapSizeX(bar.prefWidth(-1));
		double contentWidth = snapSizeX((w - barWidth) * getTransition());

		// content
		contentContainer.resize(contentWidth, h);
		clipRect.setWidth(contentWidth);
		positionInArea(contentContainer, x, y,
				contentWidth, h, /*baseline ignored*/0, HPos.CENTER, VPos.CENTER);
		// bar
		x += contentWidth;

		bar.resize(barWidth, h);
		positionInArea(bar, x, y,
				barWidth, h, 0, HPos.CENTER, VPos.TOP);
		bar.requestLayout();
	}

	/** {@inheritDoc} */
	@Override protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double headerWidth = snapSizeX(bar.prefWidth(height));
		double contentWidth = contentContainer.prefWidth(height) * getTransition();
		return headerWidth + snapSizeX(contentWidth) + leftInset + rightInset;
	}

	/** {@inheritDoc} */
	@Override protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double titleHeight = snapSizeY(bar.prefHeight(width));
		double contentHeight = snapSizeY(contentContainer.prefHeight(width));
		return Math.max(titleHeight, contentHeight) + topInset + bottomInset;
	}

	/** {@inheritDoc} */
	@Override protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		double headerWidth = snapSizeX(bar.prefWidth(height));
		double contentWidth = contentContainer.prefWidth(height) * getTransition();
		return headerWidth + snapSizeX(contentWidth) + leftInset + rightInset;
	}

	/** {@inheritDoc} */
	@Override protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		double titleHeight = snapSizeY(bar.prefHeight(width));
		double contentHeight = snapSizeY(contentContainer.prefHeight(width));
		return Math.max(titleHeight, contentHeight) + topInset + bottomInset;
	}

	@Override protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		return (this.getSkinnable()).prefWidth(height);
	}
	/** {@inheritDoc} */
	@Override protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		return Double.MAX_VALUE;
	}



	private void updateClip() {
		clipRect.setWidth(contentContainer.getWidth());
		clipRect.setHeight(getSkinnable().getHeight());
	}
	private void setExpanded(boolean expanded) {
		// we need to perform the transition between expanded / hidden
		if (getSkinnable().isAnimated()) {
			transitionStartValue = getTransition();
			doAnimationTransition();
		} else {
			transitionProperty().set(expanded ? 1.0f : 0.0f);
			if (content != null) content.setVisible(expanded);
			getSkinnable().requestLayout();
		}
	}

	private void doAnimationTransition() {
		if (content == null) return;

		Duration duration;
		if (timeline != null && (timeline.getStatus() != Status.STOPPED)) {
			duration = timeline.getCurrentTime();
			timeline.stop();
		} else {
			duration = TRANSITION_DURATION;
		}

		timeline = new Timeline();
		timeline.setCycleCount(1);

		KeyFrame k1, k2;

		if (getSkinnable().isExpanded()) {
			k1 = new KeyFrame(
					Duration.ZERO,
					event -> content.setVisible(true),
					new KeyValue(transitionProperty(), transitionStartValue)
			);

			k2 = new KeyFrame(
					duration,
					event -> {},
					new KeyValue(transitionProperty(), 1, Interpolator.LINEAR)

			);
		} else {
			k1 = new KeyFrame(
					Duration.ZERO,
					event -> {},
					new KeyValue(transitionProperty(), transitionStartValue)
			);

			k2 = new KeyFrame(
					duration,
					event -> content.setVisible(false),
					new KeyValue(transitionProperty(), 0, Interpolator.LINEAR)
			);
		}

		timeline.getKeyFrames().setAll(k1, k2);
		timeline.play();
	}
}