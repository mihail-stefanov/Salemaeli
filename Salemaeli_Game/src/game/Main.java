package game;

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
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Random;
import javax.sound.midi.Synthesizer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	// Loading the game objects
	Canvas canvas = new Canvas(800, 620);
	GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

	Level level = new Level();
	Stats gameStats = new Stats(level.getInitialNumberOfLives());
	ArrayList<String> inputKeys = new ArrayList<String>();

	boolean gameEnded = false;
	ArrayList<Brick> bricks = new ArrayList<>();
	ArrayList<Coin> coins = new ArrayList<>();
	ArrayList<Bonus> bonuses = new ArrayList<>();
	ArrayList<Penalty> penalties = new ArrayList<>();
	Board board = new Board(350, 575);
	Ball ball = new Ball(board.getPositionX() + board.getWidth() / 2, board.getPositionY() - Ball.radius, 1,
			level.getballVelocity());

	public static void main(String[] args) {
		launch(args);
	}

	private void showStartScreen(Scene scene) {
		Scene beginScene = scene;
		beginScene.setCursor(Cursor.HAND);

		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);

		// Title
		graphicsContext.setFill(Color.RED);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(3);
		Font titleFont = Font.font(null, FontWeight.BOLD, 80);
		graphicsContext.setFont(titleFont);
		graphicsContext.setEffect(is);
		graphicsContext.fillText("Break&Collect!", 100, 100);

		// Main Menu
		graphicsContext.setFill(Color.GOLD);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(2);
		Font menuFont = Font.font(null, FontWeight.BOLD, 40);
		graphicsContext.setFont(menuFont);
		graphicsContext.fillText("Start Game", 250, 200);
		graphicsContext.fillText("Instructions", 250, 300);
		graphicsContext.fillText("Choose Difficulty", 250, 400);
		graphicsContext.fillText("Exit", 250, 500);

		// Images
		Image blueBrick = new Image("images/brick_blue.png");
		Image tealBrick = new Image("images/brick_teal.png");
		Image greenBrick = new Image("images/brick_green.png");
		Image magentaBrick = new Image("images/brick_magenta.png");
		Image coin = new Image("images/coin.png");

		// Buttons
		Rectangle startTarget = new Rectangle();
		startTarget.setX(250);
		startTarget.setY(175);
		startTarget.setWidth(200);
		startTarget.setHeight(100);

		Rectangle instructionTarget = new Rectangle();
		instructionTarget.setX(250);
		instructionTarget.setY(275);
		instructionTarget.setWidth(200);
		instructionTarget.setHeight(100);

		Rectangle levelTarget = new Rectangle();
		levelTarget.setX(250);
		levelTarget.setY(375);
		levelTarget.setWidth(200);
		levelTarget.setHeight(100);

		Rectangle exitTarget = new Rectangle();
		exitTarget.setX(250);
		exitTarget.setY(475);
		exitTarget.setWidth(200);
		exitTarget.setHeight(100);

		AnimationTimer startLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				graphicsContext.drawImage(blueBrick, 200, 175);
				graphicsContext.drawImage(tealBrick, 200, 275);
				graphicsContext.drawImage(greenBrick, 200, 375);
				graphicsContext.drawImage(magentaBrick, 200, 475);
			}
		};
		startLoop.start();

		beginScene.setOnMouseEntered(event -> {
			if (startTarget.contains(event.getX(), event.getY())) {
				graphicsContext.clearRect(200, 175, 40, 20);
				graphicsContext.drawImage(blueBrick, 200, 175);
			}
		});

		beginScene.setOnMouseExited(event -> {
			if (startTarget.contains(event.getX(), event.getY())) {
				graphicsContext.clearRect(200, 175, 40, 20);
				graphicsContext.drawImage(coin, 200, 175);
			}
		});

		beginScene.setOnMouseClicked(event -> {
			if (startTarget.contains(event.getX(), event.getY())) {
				startLoop.stop();
				graphicsContext.setEffect(null);
				beginGame(scene, Difficulty.EASY);
			}

			if (instructionTarget.contains(event.getX(), event.getY())) {
				startLoop.stop();
				graphicsContext.setEffect(null);
				showInstructions(scene);
			}

			if (levelTarget.contains(event.getX(), event.getY())) {
				choosingDifficulty(scene);
			}

			if (exitTarget.contains(event.getX(), event.getY())) {
				Platform.exit();
			}
		});
	}

	private void showInstructions(Scene scene) {
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, 800, 600);
		graphicsContext.setFill(Color.RED);
		graphicsContext.setLineWidth(1);
		Font titleFont = Font.font(null, FontWeight.BOLD, 30);
		graphicsContext.setFont(titleFont);

		int leftArrow = 8_678;
		String leftArrowUtf = Character.toString((char) leftArrow);
		int rigthArrow = 8_680;
		String rigthArrowUtf = Character.toString((char) rigthArrow);
		graphicsContext.fillText("The main point is that you have to\n" + "break all of the bricks while\n"
				+ "collecting the coins. Your points\n" + "will increase when you collect coins.\n"
				+ "You can move the board with the\n" + "left " + leftArrowUtf + " and rigth " + rigthArrowUtf
				+ " arrows on your\n" + "keyboard. Good luck!", 150, 150);

		scene.setOnMouseClicked(event -> {
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.fillRect(0, 0, 800, 600);
			showStartScreen(scene);
		});
	}

	private void choosingDifficulty(Scene scene) {
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, 800, 600);

		graphicsContext.setFill(Color.RED);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(3);
		Font titleFont = Font.font(null, FontWeight.BOLD, 80);
		graphicsContext.setFont(titleFont);
		graphicsContext.fillText("Choose Difficulty", 100, 100);
		graphicsContext.setLineWidth(2);
		Font levelFont = Font.font(null, FontWeight.BOLD, 40);
		graphicsContext.setFont(levelFont);
		graphicsContext.fillText("Baby", 250, 200);
		graphicsContext.fillText("Easy", 250, 300);
		graphicsContext.fillText("Hard", 250, 400);
		graphicsContext.fillText("Pro", 250, 500);

		// Buttons
		Rectangle babyTarget = new Rectangle();
		babyTarget.setX(250);
		babyTarget.setY(175);
		babyTarget.setWidth(100);
		babyTarget.setHeight(100);

		Rectangle easyTarget = new Rectangle();
		easyTarget.setX(250);
		easyTarget.setY(275);
		easyTarget.setWidth(200);
		easyTarget.setHeight(100);

		Rectangle hardTarget = new Rectangle();
		hardTarget.setX(250);
		hardTarget.setY(375);
		hardTarget.setWidth(200);
		hardTarget.setHeight(100);

		Rectangle proTarget = new Rectangle();
		proTarget.setX(250);
		proTarget.setY(475);
		proTarget.setWidth(200);
		proTarget.setHeight(100);

		scene.setOnMouseClicked(event -> {
			if (babyTarget.contains(event.getX(), event.getY())) {
				graphicsContext.setEffect(null);
				beginGame(scene, Difficulty.BABY);
			}

			if (easyTarget.contains(event.getX(), event.getY())) {
				graphicsContext.setEffect(null);
				beginGame(scene, Difficulty.EASY);
			}

			if (hardTarget.contains(event.getX(), event.getY())) {
				graphicsContext.setEffect(null);
				beginGame(scene, Difficulty.HARD);
			}

			if (proTarget.contains(event.getX(), event.getY())) {
				graphicsContext.setEffect(null);
				beginGame(scene, Difficulty.PRO);
			}
		});
	}

	public void beginGame(Scene scene, Difficulty difficulty) {
		Scene mainScene = scene;
		//mainScene.setCursor(Cursor.NONE); 

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
				drawObjects();
						
				if(gameEnded) {
					this.stop();
				}
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

		// Moving the coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).updateVelocityY();
			coins.get(i).setPositionY(coins.get(i).getPositionY() + coins.get(i).getVelocityY());
			coins.get(i).setPositionX(coins.get(i).getPositionX() + coins.get(i).getVelocityX());
		}

		// Moving the bonuses
		for (int i = 0; i < bonuses.size(); i++) {
			bonuses.get(i).updateVelocityY();
			bonuses.get(i).setPositionY(bonuses.get(i).getPositionY() + bonuses.get(i).getVelocityY());
			bonuses.get(i).setPositionX(bonuses.get(i).getPositionX() + bonuses.get(i).getVelocityX());
		}

		// Moving the penalties
		for (int i = 0; i < penalties.size(); i++) {
			penalties.get(i).updateVelocityY();
			penalties.get(i).setPositionY(penalties.get(i).getPositionY() + penalties.get(i).getVelocityY());
			penalties.get(i).setPositionX(penalties.get(i).getPositionX() + penalties.get(i).getVelocityX());
		}

		// Moving the ball if the mouse is clicked or the space is pressed
		if (inputKeys.contains("SPACE")) {
			ball.setAsReleased(true);
		}
		if (ball.isReleased()) {
			ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
			ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
		} else {
			ball.setPositionX(board.getPositionX() + board.getWidth() / 2);
			ball.setPositionY(board.getPositionY() - Ball.radius);
		}

		// Moving the board with keys, in addition to using the mouse position
		board.move(inputKeys);
	}

	private void detectAndResolveCollisions(Scene scene) {

		// Ball collisions with the walls
		boolean ballHitWalls = ball.getPositionX() < Ball.radius
				|| ball.getPositionX() > canvas.getWidth() - Ball.radius;

		if (ballHitWalls) {
			ball.setVelocityX(ball.getVelocityX() * -1);
		}

		boolean ballHitCeiling = ball.getPositionY() < Ball.radius;

		if (ballHitCeiling) {
			ball.setVelocityY(ball.getVelocityY() * -1);
		}

		boolean ballHitFloor = ball.getPositionY() > canvas.getHeight() + Ball.radius;

		if (ballHitFloor) {
			
			try {
				gameStats.setNumberOfLives(gameStats.getNumberOfLives() - 1);
			} catch (IllegalArgumentException e) {
				gameEnded = true;
				showGameOverScreen(scene);
			}

			board.setNumberOfWideBonuses(0);
			board.setWidth(board.initialBoardWidth);
			ball.setAsReleased(false);
			ball.setAsFireBall(false);
			ball.setPositionX(board.getPositionX() + board.getWidth() / 2);
			ball.setPositionY(board.getPositionY() - Ball.radius);
		}

		// Ball collisions with the board
		double boardBallDifferenceY = board.getPositionY() - ball.getPositionY();
		double boardBallDifferenceX = board.getPositionX() - ball.getPositionX();

		boolean ballHitBoard = boardBallDifferenceY < Ball.radius && boardBallDifferenceY > 0
				&& boardBallDifferenceX < Ball.radius && boardBallDifferenceX > -(board.getWidth() + Ball.radius);
		if (ballHitBoard) {
			ball.setVelocityY(level.getballVelocity());
			ball.setVelocityX(-1 * ((boardBallDifferenceX + board.getWidth() / 2) / 10));
		}

		// Coin collisions with the board
		for (int i = 0; i < coins.size(); i++) {
			double boardCoinDifferenceY = board.getPositionY() - coins.get(i).getPositionY();
			double boardCoinDifferenceX = board.getPositionX() - coins.get(i).getPositionX();

			boolean coinHitBoard = boardCoinDifferenceY < Coin.radius && boardCoinDifferenceY > -Coin.radius
					&& boardCoinDifferenceX < Coin.radius && boardCoinDifferenceX > -(board.getWidth() + Coin.radius);
			if (coinHitBoard) {
				gameStats.setScore(coins.get(i).getBonusToScore());
				coins.remove(i);
			}
		}

		// Bonuses collisions with the board
		for (int i = 0; i < bonuses.size(); i++) {
			double boardBonusDifferenceY = board.getPositionY() - bonuses.get(i).getPositionY();
			double boardBonusDifferenceX = board.getPositionX() - bonuses.get(i).getPositionX();

			boolean bonusHitBoard = boardBonusDifferenceY < Bonus.bonusHeight && boardBonusDifferenceY > -Bonus.bonusHeight
					&& boardBonusDifferenceX < Bonus.bonusWidth && boardBonusDifferenceX > -(board.getWidth() + Bonus.bonusWidth);
			if (bonusHitBoard) {
				if (bonuses.get(i) instanceof LifeBonus){
					((LifeBonus) bonuses.get(i)).takeEffect(gameStats);
				}
				else if(bonuses.get(i) instanceof WideBonus){
					((WideBonus) bonuses.get(i)).takeEffect(board);
				}
				else if(bonuses.get(i) instanceof FireBall){
					((FireBall) bonuses.get(i)).takeEffect(ball);
				}

				bonuses.remove(i);
			}
		}

		// Penalties collisions with the board
		for (int i = 0; i < penalties.size(); i++) {
			double boardPenaltyDifferenceY = board.getPositionY() - penalties.get(i).getPositionY();
			double boardPenaltyDifferenceX = board.getPositionX() - penalties.get(i).getPositionX();

			boolean penaltyHitBoard = boardPenaltyDifferenceY < Penalty.penaltyHeight && boardPenaltyDifferenceY > - Penalty.penaltyHeight
					&& boardPenaltyDifferenceX < Penalty.penaltyWidth && boardPenaltyDifferenceX > -(board.getWidth() + Penalty.penaltyWidth);
			if (penaltyHitBoard) {
				if (penalties.get(i) instanceof LifePenalty){
					((LifePenalty) penalties.get(i)).takeEffect(gameStats);
				}
				else if(penalties.get(i) instanceof WidePenalty){
					((WidePenalty) penalties.get(i)).takeEffect(board);
				}
				else if(penalties.get(i) instanceof LosePointsPenalty){
					((LosePointsPenalty) penalties.get(i)).takeEffect(gameStats);
				}

				penalties.remove(i);
			}
		}

		// Ball collisions with bricks
		for (int i = 0; i < bricks.size(); i++) {
			double objectTreshold = new Random().nextDouble(); // Treshold for coin or bonus or penalty generation.

			double brickBallDifferenceY = bricks.get(i).getPositionY() - ball.getPositionY();
			double brickBallDifferenceX = bricks.get(i).getPositionX() - ball.getPositionX();

			boolean ballHitBrick = brickBallDifferenceY > -(Brick.height + Ball.radius)
					&& brickBallDifferenceY < Ball.radius && brickBallDifferenceX > -(Brick.width + Ball.radius)
					&& brickBallDifferenceX < Ball.radius;

			if (ballHitBrick) {
				boolean ballHitBrickVertically = brickBallDifferenceX < Ball.radius / 2
						&& brickBallDifferenceX > -(Brick.width + Ball.radius / 2);
				if (ballHitBrickVertically) {
					ball.setVelocityY(ball.getVelocityY() * -1);
					ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
					if(objectTreshold >= 0.18) {
						coins.add(new Coin(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10, level.getBonusToScoreByLevel()));
					}
					else if(objectTreshold < 0.18 && objectTreshold >= 0.15){
						bonuses.add(new LifeBonus(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.15 && objectTreshold >= 0.12){
						bonuses.add(new WideBonus(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.12 && objectTreshold >= 0.09){
						bonuses.add(new FireBall(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.09 && objectTreshold >= 0.06){
						penalties.add(new LifePenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.06 && objectTreshold >= 0.03){
						penalties.add(new WidePenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.03){
						penalties.add(new LosePointsPenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}

					if(ball.isFireBall()){
						bricks.remove(i); // TODO implement fireball behavior
					}
					else {
						bricks.remove(i);
					}

				} else {
					ball.setVelocityX(ball.getVelocityX() * -1);
					ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
					if(objectTreshold >= 0.18) {
						coins.add(new Coin(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10, level.getBonusToScoreByLevel()));
					}
					else if(objectTreshold < 0.18 && objectTreshold >= 0.15){
						bonuses.add(new LifeBonus(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.15 && objectTreshold >= 0.12){
						bonuses.add(new WideBonus(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.12 && objectTreshold >= 0.09){
						bonuses.add(new FireBall(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.09 && objectTreshold >= 0.06){
						penalties.add(new LifePenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.06 && objectTreshold >= 0.03){
						penalties.add(new WidePenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}
					else if(objectTreshold < 0.03){
						penalties.add(new LosePointsPenalty(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10 ));
					}

					if(ball.isFireBall()){
				//		bricks.remove(i-1); // TODO implement multiple brick destruction when "fireBall"
						bricks.remove(i);
					//	bricks.remove(i+1);
					}
					else {
						bricks.remove(i);
					}
				}
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

		// Drawing the coins
		for (int i = 0; i < coins.size(); i++) {
			graphicsContext.drawImage(coins.get(i).getImage(), coins.get(i).getPositionX() - Coin.radius,
					coins.get(i).getPositionY() - Coin.radius);
		}

		// Drawing the bonuses
		for (int i = 0; i < bonuses.size(); i++) {
			graphicsContext.drawImage(bonuses.get(i).getImage(), bonuses.get(i).getPositionX() - Bonus.bonusWidth,
					bonuses.get(i).getPositionY() - Bonus.bonusHeight);
		}

		// Drawing the penalties
		for (int i = 0; i < penalties.size(); i++) {
			graphicsContext.drawImage(penalties.get(i).getImage(), penalties.get(i).getPositionX() - Penalty.penaltyWidth,
					penalties.get(i).getPositionY() - Penalty.penaltyHeight);
		}

		// Drawing the ball (creating an offset equal to the ball's radius, i.e.
		// 8)
		graphicsContext.drawImage(ball.getImage(), ball.getPositionX() - Ball.radius,
				ball.getPositionY() - Ball.radius);

		// Drawing the stats bar
		String score = Integer.toString(gameStats.getScore());
		graphicsContext.fillText("Score:" + score, 10d, 618d);

		String lives = Integer.toString(gameStats.getNumberOfLives());
		graphicsContext.fillText("Lives:" + lives, 665d, 618d);
	}

	private void showGameOverScreen(Scene scene) {
		Scene gameOverScene = scene;

		graphicsContext.setEffect(null);
		graphicsContext.clearRect(0, 0, 800, 620);
		graphicsContext.setFill(Color.WHITE);
		Image gameOver = new Image("images/game_over.png");
		graphicsContext.drawImage(gameOver, 0, 0);

		gameOverScene.setOnMouseClicked(event -> {
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
		
		String username = "";
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
		Random rand = new Random();
		int score = rand.nextInt(100);
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
