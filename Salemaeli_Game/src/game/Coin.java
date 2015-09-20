package game;

import java.util.Random;

import javafx.scene.image.Image;

public class Coin extends GraphicalObject {
	public static double radius = 15;
	private Image image;
	private double gravity = 0.1;
	private double velocityY = -3;
	private double velocityX = new Random().nextInt(2) - 1;
    private int bonusToScore;

	public Coin(double positionX, double positionY, int bonusToScore) {
		super(positionX, positionY);
		setImage();
		setBonusToScore(bonusToScore);
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

	public int getBonusToScore() {
		return this.bonusToScore;
	}

	public void setBonusToScore(int bonus) {
		this.bonusToScore = bonus;
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