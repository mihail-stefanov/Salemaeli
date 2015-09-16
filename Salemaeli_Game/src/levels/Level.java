package levels;

 import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.*;

public class Level {
    private double ballSpeed;
    private String choosenDifficult;
    private int numberOfLives;
    private String[] map;
    
    public Level(String choosenDifficult) {
        setBallSpeed(ballSpeed);
        setLevelItem();
    }

    public double getballSpeed() {
        return this.ballSpeed;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    private void setLevelItem() {
        switch ("choosenDifficult") {
            case "baby":
                this.ballSpeed = 3;
                break;
            case "easy":
                this.ballSpeed = 4;
                this.map=loadMap("src/levels/easy.txt");
                break;
            case "hard":
                this.ballSpeed = 5;
                break;
            case "pro":
                this.ballSpeed = 7;
                this.map=loadMap("src/levels/pro.txt"); 
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
