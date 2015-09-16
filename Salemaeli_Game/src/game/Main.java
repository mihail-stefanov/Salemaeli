package game;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

	// Loading the game objects
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
	
	Level level = new Level(Difficulty.EASY);
	ArrayList<String> inputKeys = new ArrayList<String>();
	
	ArrayList<Brick> bricks = new ArrayList<>();
	Board board = new Board(350, 575);
	Ball ball = new Ball(board.getPositionX() + 50, board.getPositionY() - 8, 0, (int) level.getballSpeed());

	public static void main(String[] args) {
		launch(args);
	}

	private void showStartScreen(Scene scene) {
//		// TODO: To be implemented
//		Scene beginScene = scene;
//		beginScene.setCursor(Cursor.HAND);
//
//		
//		AnimationTimer startLoop = new AnimationTimer() {
//			
//			@Override
//			public void handle(long now) {
//				// TODO Auto-generated method stub
//				// TODO: To be implemented
//			}
//		};
//		startLoop.start();
//		
//		beginScene.setOnMouseClicked(event -> {
//			startLoop.stop();
			beginGame(scene);
//		});
	}
	
	private void beginGame(Scene scene) {

		Scene mainScene = scene;
		
		mainScene.setCursor(Cursor.NONE); // HIDING THE CURSOR

		initializeObjects();
		
		// THINGS TO DO ON KEY PRESSES
		mainScene.setOnKeyPressed(event -> {
			String code = event.getCode().toString();
			if (!inputKeys.contains(code)) {
				inputKeys.add(code);
			}
		});

		// THINGS TO DO ON KEY RELEASES
		mainScene.setOnKeyReleased(event -> inputKeys.remove(event.getCode().toString()));
		
		// THINGS TO DO ON MOUSE CLICKS
		mainScene.setOnMouseClicked(event -> ball.Release());

		// THINGS TO DO WHEN THE MOUSE MOVES
		mainScene.setOnMouseMoved(event -> board.setPositionX((int) event.getX() - 50));

		// final long gameStartTime = System.nanoTime(); 
		// TODO: To be used on time dependent events
		AnimationTimer gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				updateObjects();
				drawObjects();
			}
		};
		
		gameLoop.start();
	}
	
	
	private void showGameOverScreen(Scene scene) {
		// TODO: To be implemented (entered into after calling "gameloop.stop();")
	}
	
	private void initializeObjects() {
		// Generating the bricks
		for (int i = 0; i < level.getMap().length; i++) {
			for (int j = 0; j < level.getMap()[i].length(); j++) {
				if (level.getMap()[i].charAt(j) != '0') {
					bricks.add(new Brick(j * 40, i * 20, level.getMap()[i].charAt(j)));
				}
			}
		}
	}

	private void updateObjects() {

		detectAndResolveCollisions(canvas, ball, board, bricks);

		// Moving the ball if the mouse is clicked or the space is pressed
		if (inputKeys.contains("SPACE")) {
			ball.Release();
		}
		if (ball.isReleased()) {
			ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
			ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
		} else {
			ball.setPositionX(board.getPositionX() + 50);
			ball.setPositionY(board.getPositionY() - 8);
		}

		// Moving the board with keys, in addition to using the mouse position
		if (inputKeys.contains("LEFT")) {
			board.setPositionX(board.getPositionX() - 5);
		}
		if (inputKeys.contains("RIGHT")) {
			board.setPositionX(board.getPositionX() + 5);
		}
	}

	private void detectAndResolveCollisions(Canvas canvas, Ball ball, Board board, ArrayList<Brick> bricks) {
		int ballRadius = 8;
		int boardsWidth = 100;

		// Collisions with walls
		if (ball.getPositionX() < ballRadius || ball.getPositionX() > canvas.getWidth() - ballRadius) {
			ball.setVelocityX(ball.getVelocityX() * -1);
		}

		if (ball.getPositionY() < ballRadius) {
			ball.setVelocityY(ball.getVelocityY() * -1);
		}

		// Collisions with board
		int boardBallDifferenceY = board.getPositionY() - ball.getPositionY();
		int boardBallDifferenceX = board.getPositionX() - ball.getPositionX();

		if (boardBallDifferenceY < ballRadius && boardBallDifferenceY > 0 && boardBallDifferenceX < ballRadius
				&& boardBallDifferenceX > -(boardsWidth + ballRadius)) {
			ball.setVelocityY((int)level.getballSpeed());
			ball.setVelocityX(-1 * ((boardBallDifferenceX + 50) / 10));
		}

		// Collisions with bricks
		Iterator<Brick> brickIterator = bricks.iterator();
		while (brickIterator.hasNext()) {
			Brick currentBrick = brickIterator.next();

			int brickHeight = 20;
			int brickWidth = 40;
			int brickBallDifferenceY = currentBrick.getPositionY() - ball.getPositionY();
			int brickBallDifferenceX = currentBrick.getPositionX() - ball.getPositionX();

			// Checking if the ball has collided with the brick
			if (brickBallDifferenceY > -(brickHeight + ballRadius) && brickBallDifferenceY < ballRadius
					&& brickBallDifferenceX > -(brickWidth + ballRadius) && brickBallDifferenceX < ballRadius) {
				// Checking the location of the collision
				if (brickBallDifferenceX < ballRadius / 2 && brickBallDifferenceX > -(brickWidth + ballRadius / 2)) {
					ball.setVelocityY(ball.getVelocityY() * -1);
					ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
					currentBrick.setPositionX(-100); // TODO: Find alternative
														// to hide bricks
				} else {
					ball.setVelocityX(ball.getVelocityX() * -1);
					ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
					currentBrick.setPositionX(-100); // TODO: Find alternative
														// to hide bricks
				}
			}
		}
	}

	private void drawObjects() {

		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Drawing the bricks
		for (int i = 0; i < bricks.size(); i++) {
			graphicsContext.drawImage(
					bricks.get(i).getImage(), 
					bricks.get(i).getPositionX(), 
					bricks.get(i).getPositionY());
		}

		// Drawing the board
		graphicsContext.drawImage(
				board.getImage(), 
				board.getPositionX(), 
				board.getPositionY());

		// Drawing the ball (creating an offset equal to the ball's radius, i.e. 8)
		graphicsContext.drawImage(
				ball.getImage(), 
				ball.getPositionX() - 8, 
				ball.getPositionY() - 8); 
	}

	@Override
	public void start(Stage window) throws Exception {
		window.setTitle("Game Title");  // TODO: Change title
		Group root = new Group();
		Scene mainScene = new Scene(root);
		window.setScene(mainScene);
		root.getChildren().add(canvas);
		window.show();
		
		showStartScreen(mainScene);
	}
}