package game.end;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class FileManeger {
	private FileManeger() {
	}

	public static String ReadFromFile() {
		String line = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("src/high_scores/high_scores.txt"));
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return line;
	}

	public static void WriteToFile(String output) {
		File file = new File("src/high_scores/high_scores.txt");

		try {
			FileWriter fileOutput = new FileWriter(file, false);
			fileOutput.write(output);
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}