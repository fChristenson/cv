package se.fidde.cv.view.screens;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import se.fidde.cv.model.icons.BallIcon;
import se.fidde.cv.model.icons.BounceIcon;
import se.fidde.cv.model.icons.ExitIcon;

public class CVMain extends Application {

	private static boolean gameIsRunning = false;
	private static int totalClicks = 0;
	private static Label clickCounter;
	private BounceIcon game = new BounceIcon("gameIcon.png");
	private ExitIcon exit = new ExitIcon();
	private BounceIcon web = new BounceIcon("globeIcon.png");
	private BounceIcon video = new BounceIcon("playIcon.png");
	private BounceIcon cv = new BounceIcon("lightningIcon.png");
	private Media clip = new Media(getClass().getResource("examProject.mp4")
			.toString());
	private MediaPlayer player = new MediaPlayer(clip);
	private MediaView view = new MediaView(player);
	private Pane videoWindow;
	private boolean cvIsShowing = false;
	private boolean videoIsShowing = false;

	@Override
	public void start(Stage primaryStage) {
		final StackPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

			HBox icons = (HBox) root.lookup("#iconHBox");
			icons.getChildren().addAll(game, web, video, cv);
			icons.getChildren().add(icons.getChildren().size(), exit);
			Scene scene = new Scene(root);
			scene.fillProperty().set(Color.BLACK);
			player.setCycleCount(1);
			view.setVisible(false);
			view.fitWidthProperty().set(800);
			videoWindow = (Pane) root.lookup("#video");
			videoWindow.getChildren().add(view);
			clickCounter = (Label) root.lookup("#clickCounter");

			initializeGame(root);
			initializeWeb();
			initializeCv(root);
			initializeVideo();

			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			System.exit(0);

		}

	}

	private void initializeVideo() {
		video.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {

				player.setOnEndOfMedia(new Runnable() {

					@Override
					public void run() {
						videoIsShowing = false;
					}
				});

				if (videoIsShowing) {
					player.stop();
					view.setVisible(false);
					videoWindow.setVisible(false);
					videoWindow.setDisable(true);
					videoIsShowing = false;

				} else {
					view.setVisible(true);
					videoWindow.setVisible(true);
					videoWindow.setDisable(false);
					player.play();
					videoIsShowing = true;

				}

			}
		});
	}

	private void initializeCv(final StackPane root) {
		cv.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				ScrollPane cvScroller = (ScrollPane) root.lookup("#cvScroller");

				if (cvIsShowing) {
					new Timeline(new KeyFrame(new Duration(500), new KeyValue(
							cvScroller.opacityProperty(), 0.0))).play();
					cvIsShowing = false;
					cvScroller.setDisable(true);
					cvScroller.setVisible(false);

				} else {

					cvScroller.setOpacity(0);
					cvScroller.setVisible(true);
					cvScroller.setDisable(false);
					new Timeline(new KeyFrame(new Duration(500), new KeyValue(
							cvScroller.opacityProperty(), 0.80))).play();

					cvIsShowing = true;
				}
			}
		});
	}

	private void initializeWeb() {
		web.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				try {

					if (!Desktop.isDesktopSupported())
						return;

					Desktop.getDesktop()
							.browse(new URI("http://www.fidde.net"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void initializeGame(final StackPane root) {
		game.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Pane ballHunt = (Pane) root.lookup("#ballHuntBoard");

				if (!gameIsRunning && !videoIsShowing && !cvIsShowing) {
					clickCounter.setText("0");
					totalClicks = 0;
					ballHunt.setDisable(false);
					ballHunt.setVisible(true);
					BallIcon ball = new BallIcon();
					ball.setMyParent(ballHunt);
					ballHunt.getChildren().addAll(ball);
					gameIsRunning = true;

				} else {
					ballHunt.setVisible(false);
					ballHunt.setDisable(true);
					ballHunt.getChildren().clear();
					gameIsRunning = false;
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void setGameIsRunning(boolean gameIsRunning) {
		CVMain.gameIsRunning = gameIsRunning;
	}

	public static void incrementsClickCounter() {
		totalClicks++;
		clickCounter.setText(String.valueOf(totalClicks));
	}

}
