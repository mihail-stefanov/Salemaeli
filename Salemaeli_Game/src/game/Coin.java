package game;

import java.util.Random;

import javafx.scene.image.Image;

public class Coin extends GraphicalObject {
	protected Image image;
	private double gravity = 0.1;
	private double velocityY = -3;
	private double velocityX = new Random().nextInt(2) - 1;

	public Coin(int positionX, int positionY) {
		super(positionX, positionY);
		setImage();
	}
	
	public double getVelocityY() {
		return this.velocityY;
	}
	
	public double getVelocityX() {
		return this.velocityX;
	}
	
	public void updateVelocityY() {
		this.velocityY += gravity;
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	@Override
	protected void setImage() {
		this.image = new Image("images/coin.png");		
	}

}