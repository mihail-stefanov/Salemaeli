package game.play;

public class Stats {
    private int score;
    private int numberOfLives;

    public Stats (int lives) {
        setNumberOfLives(lives);
        setScore(0);
    }

    public int getNumberOfLives() {
        return this.numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int currentScore) {
        this.score += currentScore;
    }
}
