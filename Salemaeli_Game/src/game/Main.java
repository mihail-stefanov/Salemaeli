package game;

import game.start.*;
import game.play.*;
import game.play.fallingObjects.*;
import game.end.*;

import java.io.File;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {

	// Loading the game objects
	private Canvas canvas = new Canvas(800, 620);
	private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

	private Level level = new Level();
	private Stats gameStats = new Stats(level.getInitialNumberOfLives());
	private ArrayList<String> inputKeys = new ArrayList<String>();

	private ArrayList<Brick> bricks = new ArrayList<>();
	private ArrayList<FallingObject> fallingObjects = new ArrayList<>();
	private Board board = new Board(350, 575);
	private Ball ball = new Ball(board.getPositionX() + board.getWidth() / 2, 
									board.getPositionY() - Ball.radius, 1,
									level.getballVelocity());
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws Exception {
		Media media = new Media(new File("src/sounds/PinguinDance.mp2").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		mediaPlayer.play();

		window.setTitle("Break&Collect");
		Group root = new Group();
		Scene mainScene = new Scene(root);
		window.setScene(mainScene);
		window.getIcons().add(new Image("images/wall32.PNG"));
		root.getChildren().add(canvas);
		window.show();

		showStartScreen(mainScene);
	}

	private void showStartScreen(Scene scene) {
		StartScreen startScreen = new StartScreen(graphicsContext, canvas);
		scene.setCursor(Cursor.HAND);

		scene.setOnMouseClicked(event -> {
			if (startScreen.getStartButtonTarget().contains(event.getX(), event.getY())) {
				beginGame(scene, Difficulty.EASY);
			}
			if (startScreen.getInstructionButtonTarget().contains(event.getX(), event.getY())) {
				showInstructions(scene);
			}
			if (startScreen.getLevelButtonTarget().contains(event.getX(), event.getY())) {
				showDifficultyChoices(scene);
			}
			if (startScreen.getExitButtonTarget().contains(event.getX(), event.getY())) {
				Platform.exit();
			}
		});
	}

	private void showInstructions(Scene scene) {
		Instructions.show(graphicsContext, canvas);
		scene.setOnMouseClicked(event -> showStartScreen(scene));
	}

	private void showDifficultyChoices(Scene scene) {
		DifficultyChoices difficultyChoices = new DifficultyChoices(graphicsContext, canvas);

		scene.setOnMouseClicked(event -> {
			if (difficultyChoices.getBabyButtonTarget().contains(event.getX(), event.getY())) {
				beginGame(scene, Difficulty.BABY);
			}
			else if (difficultyChoices.getEasyButtonTarget().contains(event.getX(), event.getY())) {
				beginGame(scene, Difficulty.EASY);
			}
			else if (difficultyChoices.getHardButtonTarge().contains(event.getX(), event.getY())) {
				beginGame(scene, Difficulty.HARD);
			}
			else if (difficultyChoices.getProButtonTarget().contains(event.getX(), event.getY())) {
				beginGame(scene, Difficulty.PRO);
			}
			else {
				showStartScreen(scene);
			}
		});
	}

	public void beginGame(Scene scene, Difficulty difficulty) {
		Scene mainScene = scene;
		graphicsContext.setFill(Color.BLACK);
		mainScene.setCursor(Cursor.NONE);

		level.setChosenDifficulty(difficulty);
		ball.setVelocityY(level.getballVelocity());

		initializeObjects();

		mainScene.setOnKeyPressed(event -> {
			String code = event.getCode().toString();
			if (!inputKeys.contains(code)) {
				inputKeys.add(code);
			}
		});

		mainScene.setOnKeyReleased(event -> inputKeys.remove(event.getCode().toString()));
		mainScene.setOnMouseClicked(event -> ball.setAsReleased(true));
		mainScene.setOnMouseMoved(event -> board.setPositionX(event.getX() - board.getWidth() / 2));

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				updateObjects(scene);
				
				if (gameStats.getNumberOfLives() < 0) {
					this.stop();
					showGameOverScreen(scene);
				}
				
				drawObjects();
			}
		}.start();
	}

	private void initializeObjects() {
		// Generating the bricks
		for (int i = 0; i < level.getMap().length; i++) {
			for (int j = 0; j < level.getMap()[i].length(); j++) {
				if (level.getMap()[i].charAt(j) != '0') {
					bricks.add(new Brick(j * Brick.width, i * Brick.height, level.getMap()[i].charAt(j)));
				}
			}
		}
	}

	private void updateObjects(Scene scene) {

		detectAndResolveCollisions(scene);

		// Moving the coins, bonuses and penalties
		for (int i = 0; i < fallingObjects.size(); i++) {
			fallingObjects.get(i).move();
		}

		// Moving the ball if the mouse is clicked or the space is pressed
		if (inputKeys.contains("SPACE")) {
			ball.setAsReleased(true);
		}
		
		if (ball.isReleased()) {
			ball.move();
		} 
		else {
			ball.stayAttachedToBoard(board);
		}
		
		// Removing time from FireBall bonus
		if (ball.isFireBall()) {
			ball.setFireBallDuration(ball.getFireBallDuration() - 1);
			if (ball.getFireBallDuration() < 0) {
				ball.setAsFireBall(false);
			}
		}

		// Moving the board with keys, in addition to using the mouse position
		board.move(inputKeys, canvas);
	}

	private void detectAndResolveCollisions(Scene scene) {
		ball.detectAndResolveCollisionsWithBoundries(canvas, gameStats, board);
		ball.detectAndResolveCollisionsWithBoard(board, level);

		// Falling objects collisions with the board
		for (int i = 0; i < fallingObjects.size(); i++) {
			if (fallingObjects.get(i) instanceof Coin) {
				boolean coinHitBoard = ((Coin) fallingObjects.get(i)).detectedAndResolvedCollisionsWithBoard(board, gameStats);
				
				if (coinHitBoard) {
					fallingObjects.remove(i);
				}
			}
			else {
				boolean artifactHitBoard = ((Artifact) fallingObjects.get(i)).detectedAndResolvedCollisionsWithBoard(board, gameStats, ball);
				
				if (artifactHitBoard) {
					fallingObjects.remove(i);
				}
			}
		}

		// Ball collisions with bricks
		for (int i = 0; i < bricks.size(); i++) {
			boolean ballHitBrick = ball.detectAndResolveCollisionsWithBrick(bricks.get(i), fallingObjects, level);
			
			if (ballHitBrick) {
				bricks.remove(i);
			}
		}
	}

	private void drawObjects() {

		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Drawing the bricks
		for (int i = 0; i < bricks.size(); i++) {
			graphicsContext.drawImage(bricks.get(i).getImage(), bricks.get(i).getPositionX(),
					bricks.get(i).getPositionY());
		}

		// Drawing the board
		graphicsContext.drawImage(board.getImage(), board.getPositionX(), board.getPositionY());

		// Drawing the falling objects (coins, bonuses, penalties)
		for (int i = 0; i < fallingObjects.size(); i++) {
			if (fallingObjects.get(i) instanceof Coin) {
				graphicsContext.drawImage(
						fallingObjects.get(i).getImage(), 
						fallingObjects.get(i).getPositionX() - Coin.radius,
						fallingObjects.get(i).getPositionY() - Coin.radius);
			}
			else {
				graphicsContext.drawImage(
						fallingObjects.get(i).getImage(), 
						fallingObjects.get(i).getPositionX() - Artifact.width,
						fallingObjects.get(i).getPositionY() - Artifact.height);
			}
		}

		// Drawing the ball (creating an offset equal to the ball's radius)
		graphicsContext.drawImage(ball.getImage(), ball.getPositionX() - Ball.radius,
				ball.getPositionY() - Ball.radius);

		// Drawing the stats bar
		String score = Integer.toString(gameStats.getScore());
		graphicsContext.fillText("Score:" + score, 10, 618);

		String lives = Integer.toString(gameStats.getNumberOfLives());
		graphicsContext.fillText("Lives:" + lives, 665, 618);
	}

	private void showGameOverScreen(Scene scene) {
		Image gameOverImage = new Image("images/game_over.png");

		AnimationTimer animationTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				graphicsContext.drawImage(gameOverImage, 0, 0);
			}
		};
		
		animationTimer.start();
		
		scene.setOnMouseClicked(event -> {
			animationTimer.stop();
			showPopUpWindow(scene);
		});
	}

	public void showPopUpWindow(Scene scene) {
		Stage popUpWindow = new Stage();
		VBox comp = new VBox();
		TextField textField = new TextField("");
		comp.getChildren().add(textField);

		Scene additionalScene = new Scene(comp, 400, 28);
		popUpWindow.setTitle("Enter your username here:");
		popUpWindow.setScene(additionalScene);
		
		textField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
				String username = textField.getText();
				showHighestScores(scene, username);
				popUpWindow.close();
			}
		});

		popUpWindow.show();
	}

	private void showHighestScores(Scene scene, String username) {
		HighScores highScores = new HighScores();
		highScores.show(graphicsContext, canvas, username, gameStats);
		
		scene.setOnMouseClicked(event -> {
			Platform.exit();
		});
	}

}