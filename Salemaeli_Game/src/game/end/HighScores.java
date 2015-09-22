package game.end;

import game.play.Stats;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class HighScores {

	public void show(GraphicsContext graphicsContext, Canvas canvas, String username, Stats gameStats) {
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		String line = FileManeger.ReadFromFile();
		String[] highestScoresInput = generatingNewHighestScores(line, username, gameStats);
		String output = String.join(" ", highestScoresInput);
		FileManeger.WriteToFile(output);

		String textResult = generatingTextForConsole(highestScoresInput);
		graphicsContext.setFill(Color.RED);
		graphicsContext.setLineWidth(1);
		Font titleFont = Font.font(null, FontWeight.BOLD, 50);
		graphicsContext.setFont(titleFont);
		graphicsContext.fillText("High scores!", 250, 200);

		Font scoreFont = Font.font(null, FontWeight.BOLD, 30);
		graphicsContext.setFont(scoreFont);
		graphicsContext.fillText(textResult, 290, 300);
		
	}
	
	private String generatingTextForConsole(String[] highestScoresInput) {
		StringBuilder result = new StringBuilder("1. ");

		int position = 1;
		for (int i = 0; i < highestScoresInput.length; i++) {
			result.append(highestScoresInput[i]);

			if (i % 2 == 0) {
				result.append(" - ");
			} else {
				if (position != 5) {
					result.append("\n" + ++position + ". ");
				}
			}
		}

		return result.toString();
	}

	private String[] generatingNewHighestScores(String line, String username, Stats gameStats) {
		int score = gameStats.getScore();
		String[] highestScoresInput = line.split(" ");

		for (int i = 1; i < highestScoresInput.length; i += 2) {
			if (score > Integer.parseInt(highestScoresInput[i])) {
				for (int j = highestScoresInput.length - 1; j >= i + 2; j -= 2) {
					highestScoresInput[j] = highestScoresInput[j - 2];
					highestScoresInput[j - 1] = highestScoresInput[j - 3];
				}

				highestScoresInput[i] = Integer.toString(score);
				highestScoresInput[i - 1] = username;
				break;
			}
		}

		return highestScoresInput;
	}

}
