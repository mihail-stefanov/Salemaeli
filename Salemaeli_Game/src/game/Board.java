package game;

import javafx.scene.image.Image;

public class Board extends GraphicalObject {
	private Image image;
	
	public Board (int positionX, int positionY) {
		super(positionX, positionY);
		setImage();
	}
	
	public Image getImage() {
		return this.image;
	}
	
	protected void setImage() {
		this.image = new Image("images/board.png");
	}
}
