import java.util.ArrayList;

public class Track {
	private long ID;
	private String Name;
	private String Artist;
	private String Path;
	private ArrayList<String> Tags;
	
	/**
	 * Constructor
	 * @param iD The new ID
	 * @param name The new Name
	 * @param artist The new Artist
	 * @param path The new path
	 * @param tags The new tags
	 */
	public Track(long iD, String name, String artist, String path) {
		ID = iD;
		Name = name;
		Artist = artist;
		Path = path;
		Tags = new ArrayList<String>();
	}
	
	/* Getters */
	/**
	 * @return the ID
	 */
	public long getID() {
		return ID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @return the artist
	 */
	public String getArtist() {
		return Artist;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return Path;
	}
	/**
	 * @return true if tag is in Tags
	 */
	public boolean hasTag(String tag) {
		return Tags.contains(tag);
	}
	
	/* Setters */
	/**
	 * @param iD the iD to set
	 */
	public void setID(long iD) {
		ID = iD;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		Artist = artist;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		Path = path;
	}
	/**
	 * @param tag The new tag
	 */
	public void addTag(String tag) {
		if(!hasTag(tag)){
			Tags.add(tag);
		}
	}
	
}