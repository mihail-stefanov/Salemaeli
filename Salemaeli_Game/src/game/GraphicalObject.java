package game;

import javafx.scene.image.Image;

public abstract class GraphicalObject {
	private int positionX;
	private int positionY;
	
	public GraphicalObject(int positionX, int positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public int getPositionY() {
		return this.positionY;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public abstract Image getImage();
	protected abstract void setImage();
}
