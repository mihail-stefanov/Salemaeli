package game.start;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Instructions {
	
	private Instructions() {
	}

	public static void show (GraphicsContext graphicsContext, Canvas canvas) {
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(Color.RED);
		graphicsContext.setLineWidth(1);
		Font titleFont = Font.font(null, FontWeight.BOLD, 30);
		graphicsContext.setFont(titleFont);

		int leftArrow = 8_678;
		String leftArrowUtf = Character.toString((char) leftArrow);
		int rigthArrow = 8_680;
		String rigthArrowUtf = Character.toString((char) rigthArrow);
		graphicsContext.fillText("The main point is that you have to\n" 
									+ "break all of the bricks while\n"
									+ "collecting the coins. Your points\n" 
									+ "will increase when you collect coins.\n"
									+ "You can move the board with the\n" 
									+ "left " 
									+ leftArrowUtf 
									+ " and rigth "
									+ rigthArrowUtf
									+ " arrows on your\n" + "keyboard. Good luck!", 150, 150);
	}
	
}
