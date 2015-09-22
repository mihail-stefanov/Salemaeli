package game.play.fallingObjects;

import javafx.scene.image.Image;
import java.util.Random;

import game.play.GraphicalObject;

public abstract class Bonus extends GraphicalObject {
    public static double bonusWidth = 15;
    public static double bonusHeight = 11;
    private double velocityY = -3;
    private double velocityX = new Random().nextInt(2) - 1;
    private double gravity = 0.1;

    public Bonus(double positionX, double positionY) {
        super(positionX, positionY);
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public void updateVelocityY() {
        this.velocityY += gravity;
    }

    public abstract Image getImage();
    protected abstract void setImage();
}
