package game.start;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class DifficultyChoices {
	private Rectangle babyButtonTarget;
	private Rectangle easyButtonTarget;
	private Rectangle hardButtonTarge;
	private Rectangle proButtonTarget;
	
	public DifficultyChoices(GraphicsContext graphicsContext, Canvas canvas) {
		show(graphicsContext, canvas);
		initializeBabyButtonTarget();
		initializeEasyButtonTarget();
		initializeHardButtonTarge();
		initializeProButtonTarget();
	}

	private void show(GraphicsContext graphicsContext, Canvas canvas) {
		Image blueBrick = new Image("images/brick_blue.png");
		Image tealBrick = new Image("images/brick_teal.png");
		Image greenBrick = new Image("images/brick_green.png");
		Image magentaBrick = new Image("images/brick_magenta.png");
		
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
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

		graphicsContext.drawImage(blueBrick, 200, 175);
		graphicsContext.drawImage(tealBrick, 200, 275);
		graphicsContext.drawImage(greenBrick, 200, 375);
		graphicsContext.drawImage(magentaBrick, 200, 475);
	}

	public Rectangle getBabyButtonTarget() {
		return babyButtonTarget;
	}

	private void initializeBabyButtonTarget() {
		this.babyButtonTarget = new Rectangle();
		babyButtonTarget.setX(250);
		babyButtonTarget.setY(175);
		babyButtonTarget.setWidth(100);
		babyButtonTarget.setHeight(100);
	}

	public Rectangle getEasyButtonTarget() {
		return easyButtonTarget;
	}

	private void initializeEasyButtonTarget() {
		this.easyButtonTarget = new Rectangle();
		easyButtonTarget.setX(250);
		easyButtonTarget.setY(275);
		easyButtonTarget.setWidth(200);
		easyButtonTarget.setHeight(100);
	}

	public Rectangle getHardButtonTarge() {
		return hardButtonTarge;
	}

	private void initializeHardButtonTarge() {
		this.hardButtonTarge = new Rectangle();
		hardButtonTarge.setX(250);
		hardButtonTarge.setY(375);
		hardButtonTarge.setWidth(200);
		hardButtonTarge.setHeight(100);
	}

	public Rectangle getProButtonTarget() {
		return proButtonTarget;
	}

	public void initializeProButtonTarget() {
		this.proButtonTarget = new Rectangle();
		proButtonTarget.setX(250);
		proButtonTarget.setY(475);
		proButtonTarget.setWidth(200);
		proButtonTarget.setHeight(100);
	}
	
	
}
