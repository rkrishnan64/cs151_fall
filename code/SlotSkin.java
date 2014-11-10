import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	String defaultSkin = "default_skin";
	
	SlotSkin() {
		loadSkin(defaultSkin);
	}
	
	private boolean loadSkin(String skinName) {
		// Parse text file to get paths to each file and number of wheels and number of symbols
		try {
			// Add trailing slash to path and get scanner object
			String skinPath = skinName;
			if(skinPath.indexOf('/') != skinPath.length()) skinPath += '/';
			Scanner skinFile = new Scanner(new File(skinPath+"skin.txt"));
			
			// Get number of wheels and symbols
			int wheels = Integer.parseInt(skinFile.nextLine());
			int symbolLength = Integer.parseInt(skinFile.nextLine());
			
			// Get symbol paths
			ArrayList<String> symbolPaths = new ArrayList<String>();
			for(int i = 0; i < symbolLength; i++){
				symbolPaths.add(skinFile.nextLine());
			}
			
			// Get extra image paths
			String winPath = skinFile.nextLine();
			String lossPath = skinFile.nextLine();
			String playPath = skinFile.nextLine();
			String playPressedPath = skinFile.nextLine();
			String backgroundPath = skinFile.nextLine();
			
			// Get each image file from parsed paths
			Image winImage = ImageIO.read(new File(skinPath + winPath));
			Image lossImage = ImageIO.read(new File(skinPath + lossPath));
			Image playImage = ImageIO.read(new File(skinPath + playPath));
			Image playPressedImage = ImageIO.read(new File(skinPath + playPressedPath));
			Image backgroundImage = ImageIO.read(new File(skinPath + backgroundPath));
			
			// Get each image file for symbols
			ArrayList<Image> symbolImages = new ArrayList<Image>();
			for(String path:symbolPaths) {
				symbolImages.add(ImageIO.read(new File(skinPath + path)));
			}
			
			// Assign images and values to skin
			numWheels = wheels;
			numSymbols = symbolLength;
			win = winImage;
			loss = lossImage;
			play = playImage;
			playPressed = playPressedImage;
			background = backgroundImage;
			symbols = (ArrayList<Image>) symbolImages.clone();
			
		} catch (FileNotFoundException e1) {
			System.out.println("Error loading skin");
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error loading skin");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Skin "+skinName+" loaded successfully.");
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
