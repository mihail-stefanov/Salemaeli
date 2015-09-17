package game;

import javafx.scene.image.Image;

public class Ball extends GraphicalObject {
	public static double radius = 7.5;
	private boolean released = false;
	private double velocityX;
	private double velocityY;
	private Image image;
	
	public Ball (double positionX, double positionY, double velocityX, double velocityY) {
		super(positionX, positionY);
		setVelocityX(velocityX);
		setVelocityY(velocityY);
		setImage();
	}
	
	public boolean isReleased() {
		return this.released;
	}
	
	public void release() {
		this.released = true;
	}
	
	public double getVelocityX() {
		return this.velocityX;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	public double getVelocityY() {
		return this.velocityY;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public Image getImage() {
		return this.image;
	}

	protected void setImage() {
		this.image = new Image("images/ball.png");
	}
}
