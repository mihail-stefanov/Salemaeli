package game;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Board extends GraphicalObject {
	public static double height = 10;
	public static double initialBoardWidth = 100;
	private double width;
	private Image image;
	private int numberOfWideBonuses;

	public Board(double positionX, double positionY) {
		super(positionX, positionY);
		setImage();
		setWidth(initialBoardWidth);
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width){
		this.width = width;
	}

	public Image getImage() {
		return this.image;
	}
	protected void setImage() {
		if(this.getNumberOfWideBonuses() == 0){
			this.image = new Image("images/board.png");
		}
		else if(this.getNumberOfWideBonuses() == 1){
			this.image = new Image("images/wideBoard.png");
		}
		else if(this.getNumberOfWideBonuses() > 1){
			this.image = new Image("images/widestBoard.png");
		}
	}

	public int getNumberOfWideBonuses(){
		return numberOfWideBonuses;
	}

	public void setNumberOfWideBonuses(int numberOfWideBonuses){
		this.numberOfWideBonuses = numberOfWideBonuses;
		this.setImage();
	}
	public void move(ArrayList<String> inputKeys) {
		if (inputKeys.contains("LEFT")) {
			if (this.getPositionX() >= 5) {
				this.setPositionX(this.getPositionX() - 5);
			}
		}
		if (inputKeys.contains("RIGHT")) {
			if (this.getPositionX() <= 695) {
				this.setPositionX(this.getPositionX() + 5);
			}
		}
	}
}
