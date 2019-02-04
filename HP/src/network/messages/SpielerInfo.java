package network.messages;

/**
 * Created by sarah on 13.12.2015.
 */
public class SpielerInfo {
	
	private String name;
	private String character_class;
	private Spielerwerte stats;
	private String items;
	private String weapon;

	public SpielerInfo(String name, String character_class, int x, int y) {
		this.name = name;
		this.character_class = character_class;
		this.stats = new Spielerwerte(x,y);
		this.items = null;
		this.weapon = null;
	}
	
	/* getter and setter */
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getCharacter_class() {
		return character_class;
	}
	
	public void setCharacter_class( String character_class ) {
		this.character_class = character_class;
	}
	
	public Spielerwerte getStats() {
		return stats;
	}
	
	public void setStats( Spielerwerte stats ) {
		this.stats = stats;
	}
	
	public String getItems() {
		return items;
	}
	
	public void setItems( String items ) {
		this.items = items;
	}
	
	public String getWeapon() {
		return weapon;
	}
	
	public void setWeapon( String weapon ) {
		this.weapon = weapon;
	}
}
