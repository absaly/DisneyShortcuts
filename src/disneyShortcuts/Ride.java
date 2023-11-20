package disneyShortcuts;

/**
 * Represents an instance of a ride at Disneyland.
 * 
 * @author Abbas Al Younis + Noah Ewell
 */
public class Ride {
	// declare variables
	private String name;
	private int x;
	private int y;
	private String theme;
	private int rideID;
	
	// constructor initializes fields
	public Ride(String name, int x, int y, String theme, int rideID) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.theme = theme;
		this.rideID = rideID;
	}

	/**
	 * @return the ride name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the ride x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the ride y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the ride theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @return the ride ID
	 */
	public int getRideID() {
		return rideID;
	}

	@Override
	public String toString() {
		return "Location: " + "(" + x + ", " + y + ") - Name: " + name + " - Theme: " + theme + " - rideID: " + rideID;
	}
	
}
