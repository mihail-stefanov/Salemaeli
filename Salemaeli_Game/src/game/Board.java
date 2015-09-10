package game;

import javafx.scene.image.Image;

public class Board {
	private int positionX;
	private int positionY;
	private Image image;
	
	public Board (int positionX, int positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
		setImage();
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
	
	public Image getImage() {
		return this.image;
	}
	
	private void setImage() {
		this.image = new Image("images/board.png");
	}
}
