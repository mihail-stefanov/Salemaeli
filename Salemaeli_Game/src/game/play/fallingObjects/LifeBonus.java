package game.play.fallingObjects;

import game.play.Stats;
import javafx.scene.image.Image;

public class LifeBonus extends Artifact {

    private Image image;

    public LifeBonus(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/lifeBonus.png");
    }

    public void takeEffect(Stats gameStats){
        gameStats.setNumberOfLives(gameStats.getNumberOfLives() + 1);
    }
}

