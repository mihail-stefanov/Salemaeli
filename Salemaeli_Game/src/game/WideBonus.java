package game;

import java.util.Random;
import javafx.scene.image.Image;

public class WideBonus extends Bonus{

    public static double widthBonus = 50;
    private Image image;

    public WideBonus(double positionX, double positionY) {
        super(positionX, positionY);
        setImage();
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    protected void setImage() {
        this.image = new Image("images/wideBonus.png");
    }

    public void takeEffect(Board board){
        board.setNumberOfWideBonuses(board.getNumberOfWideBonuses() + 1);
        board.setWidth(board.getWidth() + board.getNumberOfWideBonuses() * widthBonus);
    }
}
