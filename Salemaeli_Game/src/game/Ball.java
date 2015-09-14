package game;

import javafx.scene.image.Image;

public class Ball {
	private int positionX;
	private int positionY;
	private int velocityX;
	private int velocityY;
	private Image image;
	
	public Ball (int positionX, int positionY, int velocityX, int velocityY) {
		setPositionX(positionX);
		setPositionY(positionY);
		setVelocityX(velocityX);
		setVelocityY(velocityY);
		setImage();
	}
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public int getPositionY() {
		return this.positionY;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
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
	
	private void setImage() {
		this.image = new Image("images/ball.png");
	}
}
