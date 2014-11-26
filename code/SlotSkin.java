package Assignment_4;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class SlotSkin {
	int width;
	int height;
	int numWheels;
	int numSymbols;
	int symbolWidth;
	int symbolHeight;
	Image win;
	Image loss;
	Image play;
	Image playPressed;
	Image background;
	Point labelLocation;
	Point buttonLocation;
	ArrayList<Image> symbols;
	ArrayList<Point> wheelLocations;
	String defaultSkin = "default_skin";
	
	SlotSkin() {
		loadSkin(defaultSkin);
	}
	
	public boolean loadSkin(String skinPath) {
		// Parse text file to get paths to each file and number of wheels and number of symbols
		try {
			// Add trailing slash to path and get scanner object
			if(skinPath.indexOf('/') != skinPath.length()) skinPath += '/';
			Scanner skinFile = new Scanner(new File(skinPath+"skin.txt"));
			
			// Get main dimensions of program
			int width = Integer.parseInt(skinFile.nextLine());
			int height = Integer.parseInt(skinFile.nextLine());
			
			// Get location of main JLabel
			int labelX = Integer.parseInt(skinFile.nextLine());
			int labelY = Integer.parseInt(skinFile.nextLine());
			Point labelLocation = new Point(labelX, labelY);
			
			// Get location of play button
			int buttonX = Integer.parseInt(skinFile.nextLine());
			int buttonY = Integer.parseInt(skinFile.nextLine());
			Point buttonLocation = new Point(buttonX, buttonY);
			
			// Get number of wheels and symbols
			int wheels = Integer.parseInt(skinFile.nextLine());
			ArrayList<Point> wheelLocations = new ArrayList<Point>();
			for(int i = 0; i < wheels; i++) {
				int x = Integer.parseInt(skinFile.nextLine());
				int y = Integer.parseInt(skinFile.nextLine());
				wheelLocations.add(new Point(x, y));
			}
			
			// Get number of symbols and dimensions
			int symbolLength = Integer.parseInt(skinFile.nextLine());
			int symbolWidth = Integer.parseInt(skinFile.nextLine());
			int symbolHeight = Integer.parseInt(skinFile.nextLine());
			
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
			this.width = width;
			this.height = height;
			this.labelLocation = labelLocation;
			this.buttonLocation = buttonLocation;
			numWheels = wheels;
			numSymbols = symbolLength;
			this.labelLocation = labelLocation;
			this.symbolWidth = symbolWidth;
			this.symbolHeight = symbolHeight;
			win = winImage;
			loss = lossImage;
			play = playImage;
			playPressed = playPressedImage;
			background = backgroundImage;
			symbols = (ArrayList<Image>) symbolImages.clone();
			this.wheelLocations = (ArrayList<Point>) wheelLocations.clone();
		} catch (FileNotFoundException e1) {
			System.out.println("Error loading skin");
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error loading skin");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("Skin loaded successfully.");
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

	public int getSymbolWidth() {
		return symbolWidth;
	}

	public int getSymbolHeight() {
		return symbolHeight;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Point getLabelLocation() {
		return labelLocation;
	}
	
	public Point getButtonLocation() {
		return buttonLocation;
	}

	public Image getSymbol(int index) {
		return symbols.get(index);
	}

	public Point wheelLocation(int i) {
		return wheelLocations.get(i);
	}
}
