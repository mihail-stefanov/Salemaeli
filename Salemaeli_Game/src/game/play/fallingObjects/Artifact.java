package game.play.fallingObjects;

import game.play.Ball;
import game.play.Board;
import game.play.Stats;

public abstract class Artifact extends FallingObject {
    public static double width = 15;
    public static double height = 11;

    public Artifact(double positionX, double positionY) {
        super(positionX, positionY);
    }

	public boolean detectedAndResolvedCollisionsWithBoard(Board board, Stats gameStats, Ball ball) {

		// Bonuses collisions with the board
		double boardArtifactDifferenceY = board.getPositionY() - this.getPositionY();
		double boardArtifactDifferenceX = board.getPositionX() - this.getPositionX();

		boolean artifactHitBoard = boardArtifactDifferenceY < Artifact.height && 
								boardArtifactDifferenceY > -Artifact.height && 
								boardArtifactDifferenceX < Artifact.width && 
								boardArtifactDifferenceX > -(board.getWidth() + Artifact.width);
		
		if (artifactHitBoard) {
			
			if (this instanceof LifeBonus) {
				((LifeBonus) this).takeEffect(gameStats);
			} 
			else if (this instanceof WideBonus) {
				((WideBonus) this).takeEffect(board);
			} 
			else if (this instanceof FireBallBonus) {
				((FireBallBonus) this).takeEffect(ball);
			}
			else if (this instanceof LifePenalty) {
				((LifePenalty) this).takeEffect(gameStats);
			} 
			else if (this instanceof WidePenalty) {
				((WidePenalty) this).takeEffect(board);
			} 
			else if (this instanceof LosePointsPenalty) {
				((LosePointsPenalty) this).takeEffect(gameStats);
			}
		}
		
		return artifactHitBoard;
	}
}
