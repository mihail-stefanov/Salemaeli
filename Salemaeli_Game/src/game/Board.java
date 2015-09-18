package game;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Board extends GraphicalObject {
	public static double width = 100;
	public static double height = 10;
	private Image image;
	
	public Board (double positionX, double positionY) {
		super(positionX, positionY);
		setImage();
	}
	
	public Image getImage() {
		return this.image;
	}
	
	protected void setImage() {
		this.image = new Image("images/board.png");
	}
	
	public void move(ArrayList<String> inputKeys) {
		if (inputKeys.contains("LEFT")) {
			this.setPositionX(this.getPositionX() - 5);
		}
		if (inputKeys.contains("RIGHT")) {
			this.setPositionX(this.getPositionX() + 5);
		}
	}
}
