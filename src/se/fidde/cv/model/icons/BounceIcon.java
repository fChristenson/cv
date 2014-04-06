package se.fidde.cv.model.icons;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BounceIcon extends ImageView {
	private Timeline bounce = new Timeline();
	private boolean mouseIn = false;

	public BounceIcon(String name) {
		super("/se/fidde/cv/view/assets/images/" + name);

		setEffect(new Reflection(0.0, 0.4, 0.8, 0.4));
		setFitWidth(50);
		setFitHeight(50);

		setOnMouseEntered();
		setOnExited();
		setOnFinished();
		setKeyFrames();

	}

	private void setKeyFrames() {
		bounce.getKeyFrames().addAll(createKeyFrame(0, 0, 1.2, 1.0),
				createKeyFrame(0, 0, 1.0, 1.2),
				createKeyFrame(300, -20, 1.0, 1.0),
				createKeyFrame(0, 0, 1.0, 1.2),
				createKeyFrame(600, 0, 1.0, 1.0));
		bounce.setCycleCount(1);
	}

	private void setOnMouseEntered() {
		setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				bounce.play();
				mouseIn = true;
			}
		});
	}

	private void setOnFinished() {
		bounce.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (mouseIn)
					bounce.play();

				else {
					bounce.stop();
				}

			}
		});
	}

	private void setOnExited() {
		setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				mouseIn = false;
			}
		});
	}

	private KeyFrame createKeyFrame(double duration, double y, double sx,
			double sy) {
		return new KeyFrame(new Duration(duration), new KeyValue(
				translateYProperty(), y), new KeyValue(scaleXProperty(), sx),
				new KeyValue(scaleYProperty(), sy));
	}
}
