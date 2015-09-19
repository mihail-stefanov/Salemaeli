package game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	// Loading the game objects
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

	Level level = new Level(Difficulty.EASY);
	ArrayList<String> inputKeys = new ArrayList<String>();

	ArrayList<Brick> bricks = new ArrayList<>();
	ArrayList<Coin> coins = new ArrayList<>();
	Board board = new Board(350, 575);
	Ball ball = new Ball(board.getPositionX() + Board.width / 2, board.getPositionY() - Ball.radius, 1,
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
				beginGame(scene);
			}
			
			if (instructionTarget.contains(event.getX(), event.getY())) {
				startLoop.stop();
				graphicsContext.setEffect(null);
				showInstructions(scene);
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
		String leftArrowUtf = Character.toString((char)leftArrow);
		int rigthArrow = 8_680;
		String rigthArrowUtf = Character.toString((char)rigthArrow);
		graphicsContext.fillText("The main point is that you have to\n"
								+ "break all of the bricks while\n"
								+ "collecting the coins. Your points\n"
								+ "will increase when you collect coins.\n"
								+ "You can move the board with the\n"
								+ "left " 
								+ leftArrowUtf
								+ " and rigth " 
								+ rigthArrowUtf
								+ " arrows on your\n"
								+ "keyboard. Good luck!", 100, 100);
		
		scene.setOnMouseClicked(event -> {
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.fillRect(0, 0, 800, 600);
			showStartScreen(scene);
		});
	}
	
	private void beginGame(Scene scene) {
		Scene mainScene = scene;
		mainScene.setCursor(Cursor.NONE); // TODO: Still has a cursor

		initializeObjects();

		mainScene.setOnKeyPressed(event -> {
			String code = event.getCode().toString();
			if (!inputKeys.contains(code)) {
				inputKeys.add(code);
			}
		});
		
		mainScene.setOnKeyReleased(event -> inputKeys.remove(event.getCode().toString()));
		mainScene.setOnMouseClicked(event -> ball.setAsReleased(true));
		mainScene.setOnMouseMoved(event -> board.setPositionX((int) event.getX() - Board.width / 2));

		AnimationTimer gameLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				updateObjects();
				drawObjects();
			}
		};

		gameLoop.start();
	}

	private void showGameOverScreen(Scene scene) {
		// TODO: To be implemented (entered into after calling
		// "gameloop.stop();")
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

	private void updateObjects() {

		detectAndResolveCollisions();

		// Moving the coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).updateVelocityY();
			coins.get(i).setPositionY(coins.get(i).getPositionY() + coins.get(i).getVelocityY());
			coins.get(i).setPositionX(coins.get(i).getPositionX() + coins.get(i).getVelocityX());
		}

		// Moving the ball if the mouse is clicked or the space is pressed
		if (inputKeys.contains("SPACE")) {
			ball.setAsReleased(true);
		}
		if (ball.isReleased()) {
			ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
			ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
		} else {
			ball.setPositionX(board.getPositionX() + Board.width / 2);
			ball.setPositionY(board.getPositionY() - Ball.radius);
		}

		// Moving the board with keys, in addition to using the mouse position
		board.move(inputKeys);
	}

	private void detectAndResolveCollisions() {

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
			// TODO: Implement losing lives logic entry here
			ball.setAsReleased(false);
			ball.setPositionX(board.getPositionX() + Board.width / 2);
			ball.setPositionY(board.getPositionY() - Ball.radius);
		}

		// Ball collisions with the board
		double boardBallDifferenceY = board.getPositionY() - ball.getPositionY();
		double boardBallDifferenceX = board.getPositionX() - ball.getPositionX();

		boolean ballHitBoard = boardBallDifferenceY < Ball.radius && boardBallDifferenceY > 0
				&& boardBallDifferenceX < Ball.radius && boardBallDifferenceX > -(Board.width + Ball.radius);
		if (ballHitBoard) {
			ball.setVelocityY(level.getballVelocity());
			ball.setVelocityX(-1 * ((boardBallDifferenceX + Board.width / 2) / 10));
		}

		// Coin collisions with the board
		for (int i = 0; i < coins.size(); i++) {
			double boardCoinDifferenceY = board.getPositionY() - coins.get(i).getPositionY();
			double boardCoinDifferenceX = board.getPositionX() - coins.get(i).getPositionX();

			boolean coinHitBoard = boardCoinDifferenceY < Coin.radius && boardCoinDifferenceY > -Coin.radius
					&& boardCoinDifferenceX < Coin.radius && boardCoinDifferenceX > -(Board.width + Coin.radius);
			if (coinHitBoard) {
				// TODO: Implement points addition logic entry here
				coins.remove(i);
			}

		}

		// Ball collisions with bricks
		for (int i = 0; i < bricks.size(); i++) {
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
					coins.add(new Coin(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10));
					bricks.remove(i);

				} else {
					ball.setVelocityX(ball.getVelocityX() * -1);
					ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
					coins.add(new Coin(bricks.get(i).getPositionX() + 20, bricks.get(i).getPositionY() + 10));
					bricks.remove(i);
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

		// Drawing the ball (creating an offset equal to the ball's radius, i.e.
		// 8)
		graphicsContext.drawImage(ball.getImage(), ball.getPositionX() - Ball.radius,
				ball.getPositionY() - Ball.radius);
	}

	
	@Override
	public void start(Stage window) throws Exception {
		window.setTitle("Game Title"); // TODO: Change title
		Group root = new Group();
		Scene mainScene = new Scene(root);
		window.setScene(mainScene);
		root.getChildren().add(canvas);
		window.show();

		showStartScreen(mainScene);
	}
}