//package game;
//
//import javafx.scene.image.Image;
//
//public class Brick {
//	private int positionX;
//	private int positionY;
//	private char color;
//	private Image image;
//	
//	public Brick (int positionX, int positionY, char colorLetter) { 
//		setPositionX(positionX);
//		setPositionY(positionY);
//		this.color = colorLetter;
//		setImage();
//	}
//	
//	public int getPositionX() {
//		return this.positionX;
//	}
//	
//	public void setPositionX(int positionX) {
//		this.positionX = positionX;
//	}
//	
//	public int getPositionY() {
//		return this.positionY;
//	}
//	
//	public void setPositionY(int positionY) {
//		this.positionY = positionY;
//	}
//	
//	public Image getImage() {
//		return this.image;
//	}
//	
//	private void setImage() {
//		switch (color) {
//		case 'b':
//			this.image = new Image("images/brick_blue.png");
//			break;
//		case 'n':
//			this.image = new Image("images/brick_brown.png");
//			break;
//		case 'g':
//			this.image = new Image("images/brick_green.png");
//			break;
//		case 'm':
//			this.image = new Image("images/brick_magenta.png");
//			break;
//		case 'o':
//			this.image = new Image("images/brick_orange.png");
//			break;
//		case 'r':
//			this.image = new Image("images/brick_red.png");
//			break;
//		case 't':
//			this.image = new Image("images/brick_teal.png");
//			break;
//		case 'y':
//			this.image = new Image("images/brick_yellow.png");
//			break;
//		default:
//			throw new Error("Cannot find sprite for brick! Please check if the file is present!");
//		}
//	}
//}
