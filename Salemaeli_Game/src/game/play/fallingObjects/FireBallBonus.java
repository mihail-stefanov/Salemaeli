package game.play.fallingObjects;

import game.play.Ball;
import javafx.scene.image.Image;

public class FireBallBonus extends Artifact{

    private Image image;

    public FireBallBonus(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/ballRedBonus.png");
    }

    public void takeEffect(Ball ball) {
        ball.setAsFireBall(true);
    }
}
