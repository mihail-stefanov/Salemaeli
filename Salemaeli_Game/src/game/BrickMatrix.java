package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BrickMatrix {

	// This matrix is used to position the bricks on the scene
	// There are to be 20 rows and 20 columns
	public static String[] brickMatrix = loadbrickMatrix();

	private static String[] loadbrickMatrix() {
		List<String> lines = new ArrayList<String>();
		{
			try {
				Scanner reader = new Scanner(new FileReader("src/levels/brick_file.txt"));
				
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


