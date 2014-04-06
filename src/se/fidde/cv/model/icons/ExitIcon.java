package se.fidde.cv.model.icons;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ExitIcon extends BounceIcon {

	public ExitIcon() {
		super("exitIcon.png");
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Pane ballHunt = (Pane) getScene().lookup("#ballHuntBoard");
				for (Node node : ballHunt.getChildren()) {
					BallIcon icon = (BallIcon) node;
					icon.getAnimation().stop();
				}

				System.exit(0);
			}
		});
	}

}
