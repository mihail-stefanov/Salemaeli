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
		
		// TODO: Move in a separate class
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
				
				graphicsContext.clearRect(0, 0, 800, 600);
				
				// Drawing the bricks
				for (int i = 0; i < bricks.size(); i++) {
					graphicsContext.drawImage(
							bricks.get(i).getImage(), 
							bricks.get(i).getPositionX(),
							bricks.get(i).getPositionY());
				}
				
				// Moving the board
				if (inputKeys.contains("LEFT")) {
					board.setPositionX(board.getPositionX() - 5);
				}
				if (inputKeys.contains("RIGHT")) {
					board.setPositionX(board.getPositionX() + 5);
				}
				
				// Drawing the board
				graphicsContext.drawImage(
						board.getImage(), 
						board.getPositionX(), 
						board.getPositionY());
			}
		}.start();

		window.show();
	}
}
