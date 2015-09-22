package game.play;

import javafx.scene.image.Image;

public class Brick extends GraphicalObject {
	public static double width = 40;
	public static double height = 20;
	private char color;
	private Image image;
	
	public Brick (double positionX, double positionY, char colorLetter) { 
		super(positionX, positionY);
		this.color = colorLetter;
		setImage();
	}
	
	public Image getImage() {
		return this.image;
	}
	
	protected void setImage() {
		switch (color) {
		case 'b':
			this.image = new Image("images/brick_blue.png");
			break;
		case 'n':
			this.image = new Image("images/brick_brown.png");
			break;
		case 'g':
			this.image = new Image("images/brick_green.png");
			break;
		case 'm':
			this.image = new Image("images/brick_magenta.png");
			break;
		case 'o':
			this.image = new Image("images/brick_orange.png");
			break;
		case 'r':
			this.image = new Image("images/brick_red.png");
			break;
		case 't':
			this.image = new Image("images/brick_teal.png");
			break;
		case 'y':
			this.image = new Image("images/brick_yellow.png");
			break;
		default:
			throw new Error("Cannot find sprite for brick! Please check if the file is present!");
		}
	}
}
