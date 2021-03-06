package game.play.fallingObjects;

import game.play.Stats;
import javafx.scene.image.Image;

public class LifePenalty extends Artifact{

    private Image image;

    public LifePenalty(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/lifePenalty.png");
    }

    public void takeEffect(Stats gameStats){
        gameStats.setNumberOfLives(gameStats.getNumberOfLives() - 1);
    }
}
