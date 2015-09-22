package game.play.fallingObjects;

import java.util.Random;

import game.play.GraphicalObject;

public abstract class FallingObject extends GraphicalObject {
	
	private double gravity = 0.1;
	private double velocityY = -3;
	private double velocityX = new Random().nextInt(2) - 1;

	public FallingObject(double positionX, double positionY) {
		super(positionX, positionY);
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

	public void move() {
		this.updateVelocityY();
		this.setPositionY(this.getPositionY() + this.getVelocityY());
		this.setPositionX(this.getPositionX() + this.getVelocityX());
		
	}

}
