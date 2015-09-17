package game;

import javafx.scene.image.Image;

public class Ball extends GraphicalObject {
	private boolean released = false;
	private int velocityX;
	private int velocityY;
	protected Image image;
	
	public Ball (int positionX, int positionY, int velocityX, int velocityY) {
		super(positionX, positionY);
		setVelocityX(velocityX);
		setVelocityY(velocityY);
		setImage();
	}
	
	public boolean isReleased() {
		return this.released;
	}
	
	public void Release() {
		this.released = true;
	}
	
	public int getVelocityX() {
		return this.velocityX;
	}
	
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	
	public int getVelocityY() {
		return this.velocityY;
	}
	
	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public Image getImage() {
		return this.image;
	}

	protected void setImage() {
		this.image = new Image("images/ball.png");
	}
}
