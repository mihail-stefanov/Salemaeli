package game;

import game.start.*;
import game.play.*;
import game.play.fallingObjects.*;
import game.end.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private Ball ball = new Ball(board.getPositionX() + board.getWidth() / 2, board.getPositionY() - Ball.radius, 1,
			level.getballVelocity());
	
	public static void main(String[] args) {
		launch(args);
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
//		mainScene.setCursor(Cursor.NONE); // TODO: Use to fix board size bonuses and penalties

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
		mainScene.setOnMouseMoved(event -> board.setPositionX((int) event.getX() - board.getWidth() / 2));

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
			fallingObjects.get(i).updateVelocityY();
			fallingObjects.get(i).setPositionY(fallingObjects.get(i).getPositionY() + fallingObjects.get(i).getVelocityY());
			fallingObjects.get(i).setPositionX(fallingObjects.get(i).getPositionX() + fallingObjects.get(i).getVelocityX());
		}

		// Moving the ball if the mouse is clicked or the space is pressed
		if (inputKeys.contains("SPACE")) {
			ball.setAsReleased(true);
		}
		if (ball.isReleased()) {
			ball.move();
		} else {
			ball.setPositionX(board.getPositionX() + board.getWidth() / 2); // TODO: Convert to a method
			ball.setPositionY(board.getPositionY() - Ball.radius);
		}

		// Moving the board with keys, in addition to using the mouse position
		board.move(inputKeys);
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
		Scene gameOverScene = scene;

		graphicsContext.setEffect(null);
		graphicsContext.clearRect(0, 0, 800, 620);
		graphicsContext.setFill(Color.WHITE);
		Image gameOver = new Image("images/game_over.png"); 

		AnimationTimer at = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				graphicsContext.drawImage(gameOver, 0, 0);
				
			}
		};
		
		at.start();
		
		gameOverScene.setOnMouseClicked(event -> {
			at.stop();
			graphicsContext.fillRect(0, 0, 800, 620);
			showStage(scene);
		});
	}

	public void showStage(Scene scene) {
		Stage newStage = new Stage();
		VBox comp = new VBox();
		TextField field = new TextField("");
		comp.getChildren().add(field);

		Scene additionalScene = new Scene(comp, 400, 28);
		newStage.setTitle("Enter your username here:");
		newStage.setScene(additionalScene);
		
		field.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
					String username = field.getText();
					showHighestScores(scene, username);
					newStage.close();
				}
			}
		});

		newStage.show();
	}

	private void showHighestScores(Scene scene, String username) {
		String line = FileManeger.ReadFromFile();
		String[] highestScoresInput = generatingNewHighestScores(line, username);
		String output = String.join(" ", highestScoresInput);
		FileManeger.WriteToFile(output);

		String textResult = generatingTextForConsole(highestScoresInput);
		graphicsContext.setFill(Color.RED);
		graphicsContext.setLineWidth(1);
		Font titleFont = Font.font(null, FontWeight.BOLD, 50);
		graphicsContext.setFont(titleFont);
		graphicsContext.fillText("High scores!", 250, 200);

		Font scoreFont = Font.font(null, FontWeight.BOLD, 30);
		graphicsContext.setFont(scoreFont);
		graphicsContext.fillText(textResult, 290, 300);

		scene.setOnMouseClicked(event -> {
			Platform.exit();
		});
	}

	private String generatingTextForConsole(String[] highestScoresInput) {
		StringBuilder result = new StringBuilder("1. ");

		int position = 1;
		for (int i = 0; i < highestScoresInput.length; i++) {
			result.append(highestScoresInput[i]);

			if (i % 2 == 0) {
				result.append(" - ");
			} else {
				if (position != 5) {
					result.append("\n" + ++position + ". ");
				}
			}
		}

		return result.toString();
	}

	private String[] generatingNewHighestScores(String line, String username) {
		int score = gameStats.getScore();
		String[] highestScoresInput = line.split(" ");

		for (int i = 1; i < highestScoresInput.length; i += 2) {
			if (score > Integer.parseInt(highestScoresInput[i])) {
				for (int j = highestScoresInput.length - 1; j >= i + 2; j -= 2) {
					highestScoresInput[j] = highestScoresInput[j - 2];
					highestScoresInput[j - 1] = highestScoresInput[j - 3];
				}

				highestScoresInput[i] = Integer.toString(score);
				highestScoresInput[i - 1] = username;
				break;
			}
		}

		return highestScoresInput;
	}

	@Override
	public void start(Stage window) throws Exception {
		final URL resource = getClass().getResource("/sounds/PinguinDance.mp4");
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		// mediaPlayer.setMute(true);

		window.setTitle("Break&Collect");
		Group root = new Group();
		Scene mainScene = new Scene(root);
		window.setScene(mainScene);
		window.getIcons().add(new Image("images/wall32.PNG"));
		root.getChildren().add(canvas);
		window.show();

		showStartScreen(mainScene);
	}
}
