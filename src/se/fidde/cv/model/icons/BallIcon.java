package se.fidde.cv.model.icons;

import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import se.fidde.cv.model.util.Tools;
import se.fidde.cv.view.screens.CVMain;

public class BallIcon extends Circle {

	private final int CLICKS_BEFORE_NEW_BALL = 3;
	private final Duration DURATION = Duration.seconds(5);
	private final double RED_TIME = 3.0;
	private final double YELLOW_TIME = 2.0;
	private final Color GREEN = Color.GREEN;
	private final Color YELLOW = Color.YELLOW;
	private final Color RED = Color.RED;
	private final int MIN_RANGE = 50;
	private final int MAX_RANGE = 500;
	private final int MIN_RADIUS = 30;
	private final int MAX_RADIUS = 50;
	private PathTransition animation;
	private Pane myParent;
	private int timesClicked = 0;
	private int x;
	private int y;
	private int lineToX;
	private int lineToY;

	public BallIcon() {
		x = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		y = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		setRadius(Tools.getRandomIntInRange(MIN_RADIUS, MAX_RADIUS));
		setFill(GREEN);
		animation = new PathTransition(DURATION, null, this);
		setChangeListener();

		Path path = new Path();
		lineToX = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		lineToY = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);

		path.getElements().addAll(new MoveTo(x, y),
				new LineTo(lineToX, lineToY));
		animation.setPath(path);
		animation.play();

		setOnMouseClicked();
		setOnFinished();

	}

	private void setChangeListener() {
		animation.currentTimeProperty().addListener(
				new ChangeListener<Object>() {

					@Override
					public void changed(ObservableValue<?> observable,
							Object oldValue, Object newValue) {
						Duration time = (Duration) newValue;

						if (time.toSeconds() > RED_TIME)
							setFill(RED);

						else if (time.toSeconds() > YELLOW_TIME)
							setFill(YELLOW);
					}
				});
	}

	private void setOnFinished() {
		animation.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stopAllAnimations();
				myParent.setVisible(false);
				myParent.getChildren().clear();
				myParent.setDisable(true);
				CVMain.setGameIsRunning(false);

			}
		});
	}

	private void setOnMouseClicked() {
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				CVMain.incrementsClickCounter();
				timesClicked++;
				setFill(GREEN);
				setRadius(Tools.getRandomIntInRange(MIN_RADIUS, MAX_RADIUS));
				Path newPath = createNewPath();

				animation.stop();
				animation.setPath(newPath);
				animation.play();

				if (timesClicked >= CLICKS_BEFORE_NEW_BALL) {
					BallIcon newBall = new BallIcon();
					newBall.setMyParent(myParent);
					myParent.getChildren().add(newBall);
					timesClicked = 0;
				}
			}

		});
	}

	private Path createNewPath() {
		int x = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		int y = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		int lineToX = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		int lineToY = Tools.getRandomIntInRange(MIN_RANGE, MAX_RANGE);
		Path newPath = new Path();
		newPath.getElements().removeAll();
		newPath.getElements().addAll(new MoveTo(x, y),
				new LineTo(lineToX, lineToY));
		return newPath;
	}

	public void setMyParent(Pane pane) {
		myParent = pane;
	}

	public PathTransition getAnimation() {
		return animation;
	}

	private void stopAllAnimations() {
		for (Node node : myParent.getChildren()) {
			BallIcon icon = (BallIcon) node;
			icon.getAnimation().stop();
		}
	}

}
