import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class SlotSkin {
	int numWheels;
	int numSymbols;
	Image win;
	Image loss;
	Image play;
	Image playPressed;
	Image background;
	ArrayList<Image> symbols;
	String defaultSkin = "default_skin/skin.txt";
	
	SlotSkin() {
		loadSkin(defaultSkin);
	}

	private boolean loadSkin(String skinPath) {
		// Parse text file to get paths to each file and number of wheels and number of symbols
		String winPath = "the path read from skin file";
		
		// Get each image file from parsed paths
		File winFile = new File(winPath);
		try {
			win = ImageIO.read(winFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error loading skin");
			return false;
		}
		
		System.out.println("Skin "+skinPath+" loaded successfully.");
		return true;
	}

	public int getNumWheels() {
		return numWheels;
	}

	public int getNumSymbols() {
		return numSymbols;
	}

	public Image getWin() {
		return win;
	}

	public Image getLoss() {
		return loss;
	}

	public Image getPlay() {
		return play;
	}

	public Image getPlayPressed() {
		return playPressed;
	}

	public Image getBackground() {
		return background;
	}

	public Image getSymbol(int index) {
		return symbols.get(index);
	}
}
