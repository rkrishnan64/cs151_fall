package Assignment_5;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Skins for Slot Machine
 *
 */
public class SlotSkin {
	HashMap<String, String> strings;
	HashMap<String, Image> images;
	String requiredStrings = "width,height,labelX,labelY,playX,playY,numWheels,numSymbols,symbolHeight,symbolWidth";
	String defaultSkin = "default_skin";
	
	/**
	 * Default Constructor
	 * Will load the default skin
	 */
	SlotSkin() {
		loadSkin(defaultSkin);
	}
	
	/**
	 * Load a skin file
	 * @param skinPath The path to the directory that contains images and skin.txt
	 * @return TRUE if loading of skin was successful, FALSE otherwise
	 */
	public boolean loadSkin(String skinPath) {
		// Setup new variables
		HashMap<String, String> newStrings = new HashMap<String, String>();
		HashMap<String, Image> newImages = new HashMap<String, Image>();
					
		// Parse text file to get paths to each file and number of wheels and number of symbols
		try {
			// Add trailing slash to path and get scanner object
			if(skinPath.indexOf('/') != skinPath.length()) skinPath += '/';
			Scanner skinFile = new Scanner(new File(skinPath+"skin.txt"));
			
			// Get keys and values from file
			while(skinFile.hasNextLine()){
				String thisLine = skinFile.nextLine();
				if(thisLine.contains(":")){
					String[] pairs = thisLine.split(":");
					if(pairs.length == 2) {
						if(pairs[1].contains(".")) {
							Image thisImage = ImageIO.read(new File(skinPath + pairs[1]));
							newImages.put(pairs[0], thisImage);
						} else {
							newStrings.put(pairs[0], pairs[1]);
						}
					}
				}
			}
			skinFile.close();
			
			// Check for required fields
			String[] fields = requiredStrings.split(",");
			for(int i = 0; i < fields.length; i++){
				if(!newStrings.containsKey(fields[i])) {
					System.out.println("Error loading skin: required field '"+fields[i]+"' not present");
					return false;
				}
			}
			
			// Check for wheels data
			for(int i = 1; i <= Integer.parseInt(newStrings.get("numWheels")); i++){
				if(!(newStrings.containsKey("wheel"+i+"X") && newStrings.containsKey("wheel"+i+"Y"))) {
					System.out.println("Error loading skin: location of wheel missing");
					return false;
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error loading skin: file not found");
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Error loading skin: file not readable");
			e.printStackTrace();
			return false;
		}
		
		// Copy new values to current values
		strings = new HashMap<String, String>();
		images = new HashMap<String, Image>();
		strings.putAll(newStrings);
		images.putAll(newImages);
		
		// Return
		System.out.println("Skin loaded successfully.");
		return true;
	}
	
	/**
	 * Get a integer value by name
	 * @param key The label for the value
	 * @return The integer value with that value
	 */
	public int getInt(String key) {
		// Return stored string as integer
		return Integer.parseInt(strings.get(key));
	}
	
	/**
	 * Get an image object by name
	 * @param key The label for the image
	 * @return The image with that label
	 */
	public Image getImage(String key) {
		// Returm matching image
		return images.get(key);
	}
}
