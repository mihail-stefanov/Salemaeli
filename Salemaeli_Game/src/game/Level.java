package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {
	private double ballSpeed;
	private Difficulty chosenDifficulty;
	private int numberOfLives;
	private String[] map;

	public Level(Difficulty chosenDifficult) {
		setChosenDifficulty(chosenDifficult);
		setLevelItems();
	}

	public double getballSpeed() {
		return this.ballSpeed;
	}

	private void setBallSpeed(double ballSpeed) {
		this.ballSpeed = ballSpeed;
	}
	
	private void setChosenDifficulty(Difficulty chosenDifficulty) {
		this.chosenDifficulty = chosenDifficulty;
	}
	
	public int getNumberOfLives() {
		return this.numberOfLives;
	}
	
	private void setNumberOfLives(int numberOfLives) {
		this.numberOfLives = numberOfLives;
	}
	
	public String[] getMap() {
		return this.map;
	}
	
	private void setLevelItems() {
		switch (chosenDifficulty) {
		case BABY:
			setBallSpeed(3);
			this.map = loadMap("src/levels/baby.txt"); // TODO: Level to be added
			setNumberOfLives(5);
			break;
		case EASY:
			setBallSpeed(4);
			this.map = loadMap("src/levels/easy.txt");
			setNumberOfLives(4);
			break;
		case HARD:
			setBallSpeed(5);
			this.map = loadMap("src/levels/hard.txt"); 
			setNumberOfLives(3);
			break;
		case PRO:
			setBallSpeed(7);
			this.map = loadMap("src/levels/pro.txt");
			setNumberOfLives(1);
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