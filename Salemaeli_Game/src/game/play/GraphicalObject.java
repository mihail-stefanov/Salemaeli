package game.play;

import javafx.scene.image.Image;

public abstract class GraphicalObject {
	private double positionX;
	private double positionY;
	
	public GraphicalObject(double positionX, double positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}
	
	public double getPositionX() {
		return this.positionX;
	}
	
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	
	public double getPositionY() {
		return this.positionY;
	}
	
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	
	public abstract Image getImage();
	protected abstract void setImage();
}
