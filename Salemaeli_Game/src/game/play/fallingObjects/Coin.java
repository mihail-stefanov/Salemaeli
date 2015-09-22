package game.play.fallingObjects;

import game.play.Board;
import game.play.Stats;
import javafx.scene.image.Image;

public class Coin extends FallingObject {
	public static double radius = 15;
	private Image image;
    private int bonusToScore;

	public Coin(double positionX, double positionY, int bonusToScore) {
		super(positionX, positionY);
		setImage();
		setBonusToScore(bonusToScore);
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
	
	public boolean detectedAndResolvedCollisionsWithBoard(Board board, Stats gameStats) {
		
		double boardCoinDifferenceY = board.getPositionY() - this.getPositionY();
		double boardCoinDifferenceX = board.getPositionX() - this.getPositionX();

		boolean coinHitBoard = boardCoinDifferenceY < Coin.radius && 
								boardCoinDifferenceY > -Coin.radius && 
								boardCoinDifferenceX < Coin.radius && 
								boardCoinDifferenceX > -(board.getWidth() + Coin.radius);
		if (coinHitBoard) {
			gameStats.setScore(this.getBonusToScore());
		}
		return coinHitBoard;
	}
}