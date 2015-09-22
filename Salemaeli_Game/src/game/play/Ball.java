package game.play;

import java.util.ArrayList;
import java.util.Random;

import game.*;
import game.play.fallingObjects.Coin;
import game.play.fallingObjects.FallingObject;
import game.play.fallingObjects.FireBallBonus;
import game.play.fallingObjects.LifeBonus;
import game.play.fallingObjects.LifePenalty;
import game.play.fallingObjects.LosePointsPenalty;
import game.play.fallingObjects.WideBonus;
import game.play.fallingObjects.WidePenalty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Ball extends GraphicalObject {
	public static double radius = 7.5;
	private boolean released = false;
	private boolean fireBall = false;
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
	
	public void setAsReleased(boolean released) {

		this.released = released;
	}

	public boolean isFireBall() {
		return this.fireBall;
	}

	public void setAsFireBall(boolean fireBall){
		this.fireBall = fireBall;
		setImage();
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
		if(this.isFireBall()){
			this.image = new Image("images/ballRed.png");
		}
		else{
			this.image = new Image("images/ball.png");
		}
	}
	
	public void detectAndResolveCollisionsWithBoundries(Canvas canvas, Stats gameStats, Board board) {
		
		// Ball collisions with the walls
		boolean ballHitWalls = this.getPositionX() < Ball.radius || 
								this.getPositionX() > canvas.getWidth() - Ball.radius;

		if (ballHitWalls) {
			this.setVelocityX(this.getVelocityX() * -1);
		}

		boolean ballHitCeiling = this.getPositionY() < Ball.radius;

		if (ballHitCeiling) {
			this.setVelocityY(this.getVelocityY() * -1);
		}

		// Ball collisions with the floor
		boolean ballHitFloor = this.getPositionY() > canvas.getHeight() + Ball.radius;

		if (ballHitFloor) {
			gameStats.setNumberOfLives(gameStats.getNumberOfLives() - 1);
			board.setNumberOfWideBonuses(0);
			board.setWidth(Board.initialBoardWidth);
			this.setAsReleased(false);
			this.setAsFireBall(false);
			this.setPositionX(board.getPositionX() + board.getWidth() / 2);
			this.setPositionY(board.getPositionY() - Ball.radius);
		}
	}

	public void detectAndResolveCollisionsWithBoard(Board board, Level level) {

		double boardBallDifferenceY = board.getPositionY() - this.getPositionY();
		double boardBallDifferenceX = board.getPositionX() - this.getPositionX();

		boolean ballHitBoard = boardBallDifferenceY < Ball.radius && 
				boardBallDifferenceY > 0 && 
				boardBallDifferenceX < Ball.radius && 
				boardBallDifferenceX > -(board.getWidth() + Ball.radius);
				
		if (ballHitBoard) {
			this.setVelocityY(level.getballVelocity());
			this.setVelocityX(-1 * ((boardBallDifferenceX + board.getWidth() / 2) / 10));
		}
		
	}

	public void move() {
		this.setPositionX(this.getPositionX() + this.getVelocityX());
		this.setPositionY(this.getPositionY() + this.getVelocityY());
	}

	public boolean detectAndResolveCollisionsWithBrick(Brick brick, ArrayList<FallingObject> fallingObjects, Level level) {
		
		// Threshold for coin or bonus or penalty generation
		double objectTreshold = new Random().nextDouble(); 

		double brickBallDifferenceY = brick.getPositionY() - this.getPositionY();
		double brickBallDifferenceX = brick.getPositionX() - this.getPositionX();

		boolean ballHitBrick = brickBallDifferenceY > -(Brick.height + Ball.radius)
				&& brickBallDifferenceY < Ball.radius && brickBallDifferenceX > -(Brick.width + Ball.radius)
				&& brickBallDifferenceX < Ball.radius;

		double newObjectX = brick.getPositionX() + 20;
		double newObjectY = brick.getPositionY() + 10;
		if (ballHitBrick) {
			boolean ballHitBrickVertically = brickBallDifferenceX < Ball.radius / 2 && 
												brickBallDifferenceX > -(Brick.width + Ball.radius / 2);
			
			if (!this.isFireBall()) {
				if (ballHitBrickVertically) {
					this.setVelocityY(this.getVelocityY() * -1);
					this.setPositionY(this.getPositionY() + this.getVelocityY());
				} 
				else {
					this.setVelocityX(this.getVelocityX() * -1);
					this.setPositionX(this.getPositionX() + this.getVelocityX());
				}
			} 
			
			if (objectTreshold >= 0.17) {
				fallingObjects.add(new Coin(newObjectX, newObjectY,	level.getBonusToScoreByLevel()));
			} 
			else if (objectTreshold < 0.17 && objectTreshold >= 0.15) {
				fallingObjects.add(new FireBallBonus(newObjectX, newObjectY));
			} 
			else if (objectTreshold < 0.15 && objectTreshold >= 0.12) {
				fallingObjects.add(new WideBonus(newObjectX, newObjectY));
			} 
			else if (objectTreshold < 0.12 && objectTreshold >= 0.09) {
				fallingObjects.add(new LifeBonus(newObjectX, newObjectY));
			} 
			else if (objectTreshold < 0.09 && objectTreshold >= 0.06) {
				fallingObjects.add(new LifePenalty(newObjectX, newObjectY));
			} 
			else if (objectTreshold < 0.06 && objectTreshold >= 0.03) {
				fallingObjects.add(new WidePenalty(newObjectX, newObjectY));
			} 
			else if (objectTreshold < 0.03) {
				fallingObjects.add(new LosePointsPenalty(newObjectX, newObjectY));
			}
		}
		
		return ballHitBrick;
	}
}
