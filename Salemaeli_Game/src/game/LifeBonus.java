package game;

import java.util.Random;
import javafx.scene.image.Image;

public class LifeBonus extends Bonus{

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

