package game;

import javafx.scene.image.Image;

public class LosePointsPenalty extends Penalty {

    private int penaltyToScore = -30;
    private Image image;

    public LosePointsPenalty(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/minusCoin.png");
    }

    public void takeEffect(Stats gameStats){
        gameStats.setScore(penaltyToScore);
    }
}
