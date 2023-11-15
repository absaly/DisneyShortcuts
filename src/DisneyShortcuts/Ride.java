package DisneyShortcuts;

public class Ride {

	private String name;
	private int x;
	private int y;
	private String theme;
	private int rideID;
	
	public Ride(String name, int x, int y, String theme, int rideID) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.theme = theme;
		this.rideID = rideID;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getTheme() {
		return theme;
	}

	public int getRideID() {
		return rideID;
	}
	
}
