package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {
	private double ballVelocity;
	private Difficulty chosenDifficulty;
	private int initialNumberOfLives;
	private int bonusToScoreByLevel;
	private String[] map;

	public Level() {
		setChosenDifficulty(Difficulty.EASY);
		setLevelItems();
	}
	
	public Level(Difficulty chosenDifficult) {
		setChosenDifficulty(chosenDifficult);
		setLevelItems();
	}

	public double getballVelocity() {
		return this.ballVelocity;
	}

	private void setBallVelocity(double ballVelocity) {
		this.ballVelocity = ballVelocity;
	}
	
	public void setChosenDifficulty(Difficulty chosenDifficulty) {
		this.chosenDifficulty = chosenDifficulty;
	}

	public int getInitialNumberOfLives() {
		return this.initialNumberOfLives;
	}

	private void setInitialNumberOfLives(int numberOfLives) {
		this.initialNumberOfLives = numberOfLives;
	}

	public int getBonusToScoreByLevel() {
		return bonusToScoreByLevel;
	}

	public void setBonusToScoreByLevel(int bonus) {
		this.bonusToScoreByLevel = bonus;
	}
	
	public String[] getMap() {
		return this.map;
	}
	
	private void setLevelItems() {
		switch (chosenDifficulty) {
		case BABY:
			setBallVelocity(-3);
			this.map = loadMap("src/levels/baby.txt"); // TODO: Level to be added
			setInitialNumberOfLives(5);
			setBonusToScoreByLevel(5);
			break;
		case EASY:
			setBallVelocity(-4);
			this.map = loadMap("src/levels/easy.txt");
			setInitialNumberOfLives(4);
			setBonusToScoreByLevel(10);
			break;
		case HARD:
			setBallVelocity(-5);
			this.map = loadMap("src/levels/hard.txt"); 
			setInitialNumberOfLives(3);
			setBonusToScoreByLevel(25);
			break;
		case PRO:
			setBallVelocity(-7);
			this.map = loadMap("src/levels/pro.txt");
			setInitialNumberOfLives(1);
			setBonusToScoreByLevel(50);
			break;
		default:
			throw new Error("Cannot find correct level difficulty! Please check is it baby, easy, hard or pro!");
		}
	}

	private String[] loadMap(String levelFile) {
		List<String> lines = new ArrayList<String>();
		{
			try {
				Scanner reader = new Scanner(new FileReader(levelFile));

				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					lines.add(line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return lines.toArray(new String[lines.size()]);
		}
	}
}