package game.play.fallingObjects;

import game.play.Board;
import javafx.scene.image.Image;

public class WidePenalty extends Artifact{

    public static double widthBonus = 40;
    private Image image;

    public WidePenalty(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/widePenalty.png");
    }

    public void takeEffect(Board board) {
        board.setNumberOfWideBonuses(board.getNumberOfWideBonuses() - 1);
        board.setWidth(100 + board.getNumberOfWideBonuses() * widthBonus);
    }
}
