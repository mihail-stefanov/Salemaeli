package game.start;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class StartScreen {
	
	private Rectangle startButtonTarget;
	private Rectangle instructionButtonTarget;
	private Rectangle levelButtonTarget;
	private Rectangle exitButtonTarget;
	
	public StartScreen(GraphicsContext graphicsContext, Canvas canvas) {
		show(graphicsContext, canvas);
		initializeStartButtonTarget();
		initializeInstructionButtonTarget();
		initializeLevelButtonTarget();
		initializeExitButtonTarget();
	}
	
	public void show (GraphicsContext graphicsContext, Canvas canvas) {
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Image blueBrick = new Image("images/brick_blue.png");
		Image tealBrick = new Image("images/brick_teal.png");
		Image greenBrick = new Image("images/brick_green.png");
		Image magentaBrick = new Image("images/brick_magenta.png");

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
		

		graphicsContext.drawImage(blueBrick, 200, 175);
		graphicsContext.drawImage(tealBrick, 200, 275);
		graphicsContext.drawImage(greenBrick, 200, 375);
		graphicsContext.drawImage(magentaBrick, 200, 475);
		graphicsContext.setEffect(null);
	}

	public void createButtons() {
		
	}

	public Rectangle getStartButtonTarget() {
		return startButtonTarget;
	}

	private void initializeStartButtonTarget() {
		this.startButtonTarget = new Rectangle();
		this.startButtonTarget.setX(250);
		this.startButtonTarget.setY(175);
		this.startButtonTarget.setWidth(200);
		this.startButtonTarget.setHeight(100);
	}

	public Rectangle getInstructionButtonTarget() {
		return instructionButtonTarget;
	}

	private void initializeInstructionButtonTarget() {
		this.instructionButtonTarget = new Rectangle();
		instructionButtonTarget.setX(250);
		instructionButtonTarget.setY(275);
		instructionButtonTarget.setWidth(200);
		instructionButtonTarget.setHeight(100);
	}

	public Rectangle getLevelButtonTarget() {
		return levelButtonTarget;
	}

	private void initializeLevelButtonTarget() {
		this.levelButtonTarget = new Rectangle();
		levelButtonTarget.setX(250);
		levelButtonTarget.setY(375);
		levelButtonTarget.setWidth(200);
		levelButtonTarget.setHeight(100);
	}

	public Rectangle getExitButtonTarget() {
		return exitButtonTarget;
	}

	private void initializeExitButtonTarget() {
		this.exitButtonTarget = new Rectangle();
		exitButtonTarget.setX(250);
		exitButtonTarget.setY(475);
		exitButtonTarget.setWidth(200);
		exitButtonTarget.setHeight(100);
	}
}
