package game;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {

		window.setTitle("Game Title"); // TODO: Change title

		Group root = new Group();
		Scene mainScene = new Scene(root);
		window.setScene(mainScene);

		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);
		
		mainScene.setCursor(Cursor.NONE); // HIDING THE CURSOR (because the board follows the cursor)
		
		// TODO: Move in a separate method (for example: loadContent)
		
		// Generating the bricks
		ArrayList<Brick> bricks = new ArrayList<>();
		
		for (int i = 0; i < BrickMatrix.brickMatrix.length; i++) {
			for (int j = 0; j < BrickMatrix.brickMatrix[i].length(); j++) {
				if (BrickMatrix.brickMatrix[i].charAt(j) != '0') {
					bricks.add(new Brick(j * 40, i * 20, BrickMatrix.brickMatrix[i].charAt(j)));
				}
			}
		}
		// Generate the board
		Board board = new Board(350, 575);

		// Generate the ball
		Ball ball = new Ball(board.getPositionX() + 50, board.getPositionY() - 8, 0, -3);
		
		ArrayList<String> inputKeys = new ArrayList<String>();
		
		// THINGS TO DO ON KEY PRESSES
		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();

				if (!inputKeys.contains(code)) {
					inputKeys.add(code);
				}
			}
		});
		
		// THINGS TO DO ON KEY RELEASES
//		mainScene.setOnKeyReleased(event -> inputKeys.remove(event.getCode().toString())); // Shorter version of the same code
		
		mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				inputKeys.remove(code);
			}
		});
		
		Rectangle square = new Rectangle(100, 100);  // TODO: To be used for mouse clicks on 
		
		// THINGS TO DO ON MOUSE CLICKS
		mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            public void handle(MouseEvent e) {
		                if ( square.contains( e.getX(), e.getY())) {
		                    // TODO: Logic can be implemented here
		                }
		                ball.Release();
		            }
		        });
		
		// THINGS TO DO WHEN THE MOUSE MOVES
		mainScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				board.setPositionX((int) e.getX() - 50);
			}
		});
		
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
//		final long gameStartTime = System.nanoTime(); // TODO: To be used on time dependent events
		
		new AnimationTimer() {
			
			@Override
			public void handle(long currentTime) {
				
				// TODO: Use the elapsed time to control automatic object movement or generation
				// TODO: To be used on time dependent events
//				double elapsedTimeInSeconds = (currentTime - gameStartTime) / 1000000000.00;
				
				// COMMANDS EXECUTING EVERY FRAME GO HERE
				
				detectAndResolveCollisions(ball, board, bricks);
				
				// Moving the ball if the mouse is clicked of the space is pressed
				if (ball.isReleased()) {
					ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
					ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
				}
				else {
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
				if (inputKeys.contains("SPACE")) {
					ball.Release();
				}
				
				// REDRAWING SCENE COMMANDS GO HERE
				graphicsContext.clearRect(0, 0, 800, 600);
				
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
				
				// Drawing the ball
				graphicsContext.drawImage(
						ball.getImage(), 
						ball.getPositionX() - 8, 
						ball.getPositionY() - 8); // Centering the image to make collision calculations easier
			}

			private void detectAndResolveCollisions(Ball ball, Board board, ArrayList<Brick> bricks) {
				int ballRadius = 8;
				int boardsWidth = 100;
				
				// Collisions with walls
				if (ball.getPositionX() < ballRadius ||
					ball.getPositionX() > canvas.getWidth() - ballRadius) {
					ball.setVelocityX(ball.getVelocityX() * -1);
				}
				
				if (ball.getPositionY() < ballRadius) {
					ball.setVelocityY(ball.getVelocityY() * -1);
				}
				
				// Collisions with board
				int boardBallDifferenceY = board.getPositionY() - ball.getPositionY();
				int boardBallDifferenceX = board.getPositionX() - ball.getPositionX();
				
				if (boardBallDifferenceY < ballRadius && 
					boardBallDifferenceY > 0 &&
					boardBallDifferenceX < ballRadius &&
					boardBallDifferenceX > - (boardsWidth + ballRadius)) {
					ball.setVelocityY(-3);
					ball.setVelocityX(-1 * ((boardBallDifferenceX + 50)/10));
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
					if (brickBallDifferenceY > - (brickHeight + ballRadius) &&
						brickBallDifferenceY < ballRadius &&
						brickBallDifferenceX > - (brickWidth + ballRadius) &&
						brickBallDifferenceX < ballRadius) {
						// Checking the location of the collision
						if (brickBallDifferenceX < ballRadius / 2 && 
							brickBallDifferenceX > - (brickWidth + ballRadius / 2)) {
							ball.setVelocityY(ball.getVelocityY() * -1);
							ball.setPositionY(ball.getPositionY() + ball.getVelocityY());
							currentBrick.setPositionX(-100); // TODO: Find alternative to hide bricks
						}
						else {
							ball.setVelocityX(ball.getVelocityX() * -1);
							ball.setPositionX(ball.getPositionX() + ball.getVelocityX());
							currentBrick.setPositionX(-100); // TODO: Find alternative to hide bricks
						}
					}
				}
			}
		}.start();

		window.show();
	}
}