package network.messages;

/**
 * Created by sarah on 13.12.2015.
 */
public class Spielerwerte {
	
	private int level;
	private int exp;
	private Spielerposition player_positions;
	private int hpcur;
	private int hpmax;
	private int str;
	private int dex;
	private int wis;

	public Spielerwerte(int x, int y) {
		this.level = 1;
		this.exp = 0;
		this.player_positions = new Spielerposition(x, y);
		this.hpcur = 0;
		this.hpmax = 0;
		this.str = 0;
		this.dex = 0;
		this.wis = 0;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel( int level ) {
		this.level = level;
	}
	
	public int getExp() {
		return exp;
	}
	
	public void setExp( int exp ) {
		this.exp = exp;
	}
	
	public Spielerposition getPlayer_positions() {
		return player_positions;
	}
	
	public void setPlayer_positions( Spielerposition player_positions ) {
		this.player_positions = player_positions;
	}
	
	public int getHpcur() {
		return hpcur;
	}
	
	public void setHpcur( int hpcur ) {
		this.hpcur = hpcur;
	}
	
	public int getHpmax() {
		return hpmax;
	}
	
	public void setHpmax( int hpmax ) {
		this.hpmax = hpmax;
	}
	
	public int getStr() {
		return str;
	}
	
	public void setStr( int str ) {
		this.str = str;
	}
	
	public int getDex() {
		return dex;
	}
	
	public void setDex( int dex ) {
		this.dex = dex;
	}
	
	public int getWis() {
		return wis;
	}
	
	public void setWis( int wis ) {
		this.wis = wis;
	}
}
