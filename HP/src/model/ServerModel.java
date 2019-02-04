package model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.entity.Entity;
import model.entity.Monster;
import model.entity.Player;
import model.entity.monster.Boss;
import model.entity.monster.Goblin;
import model.entity.monster.Skelett;
import model.entity.monster.Wolf;
import model.entity.monster.enemiesHP.*;
import model.entity.player.Mage;
import model.furniture.Chest;
import model.furniture.Door;
import model.furniture.NormalChest;
import model.furniture.WalkingChest;
import model.util.Position;
import network.messages.Spielfeld;

public class ServerModel {

	private boolean sp=false;
	private List<File> levels;
	private int connectedClients;
	private List<String> chosenClasses;

	private static Floor currentFloor;
	private List<Player> players;
	private List<Monster> monsters;
	private List<Chest> chests;
	private List<Door> doors;
	private final IntegerProperty mapChange = new SimpleIntegerProperty(0);
	private Spielfeld[] map;
	private DifficultySettings difficultySettings = new DifficultySettings("easy");
	private boolean finalLevel =false;
	private final IntegerProperty floorChange = new SimpleIntegerProperty(0);
	private boolean harry;

	public ServerModel(List<File> levels, List<Player> players, boolean harry) throws IOException {
		this.levels = levels;
		this.harry = harry;
		this.connectedClients = players.size();
		if (connectedClients == 1) {
			sp = true;
		}
		currentFloor = new Floor(levels.get(0), connectedClients);
		this.map = updateMap();
		this.players = players;
		createMonsters();
		createChests();
		createDoors();
		initPlayers();
	}

	public ServerModel(List<File> levels, List<Player> players, String difficulty)  throws IOException {
		this.levels = levels;
		this.connectedClients=players.size();
		if(connectedClients==1){
			sp = true;
		}
		difficultySettings = new DifficultySettings(difficulty);
		currentFloor = new Floor(levels.get(0), connectedClients);
		//map = updateMap();
		this.players= players;
		createMonsters();
		createChests();
		createDoors();
		initPlayers();
	}

	private void initPlayers() {
		for(int i=0; i<players.size();i++){
			players.get(i).addInformation(this, currentFloor, currentFloor.getInitialPlayerPositions().get(i),1 );
		}
	}

	public Spielfeld[] updateMap() throws IOException {

		int arraySize = 0;

		for(int z=0; z<levels.size();z++){
			if(z==currentFloor.getLevel()) {
				FloorReader floor = new FloorReader(levels.get(z), connectedClients);
				arraySize = floor.getxSize() * floor.getySize();
			}
		}
		Spielfeld[] result = new Spielfeld[arraySize];

		arraySize=0;
		for(int z=0; z<levels.size();z++) {
			if (z == currentFloor.getLevel()) {
				FloorReader floor = new FloorReader(levels.get(z), connectedClients);

				for (int x = 0; x < floor.getxSize(); x++) {
					for (int y = 0; y < floor.getySize(); y++) {
						Spielfeld spielfeld = new Spielfeld(x, y, z, floor.getFieldAtPos(new Position(x, y)).toString());
						result[arraySize] = spielfeld;
						arraySize++;
					}
				}
			}
		}
		return result;
	}

	public void loadNextLevel() throws IOException {
		int currentLevel = currentFloor.getLevel();
		if(currentLevel+2==levels.size()){
			finalLevel=true;
		};
		currentFloor.mapImport(levels.get(currentLevel+1));
		createChests();
		createDoors();
		createMonsters();
		transferPlayersToNewLevel();
	}

	private void createDoors() {
		List<Position> allDoorPositions = currentFloor.getDoorPositions();
		doors = new LinkedList<Door>();
		int doorNumber = allDoorPositions.size();
		for(int i=0; i<doorNumber; i++){
			doors.add(new Door(allDoorPositions.get(i)));
		}
	}

	private void createChests() {
		List<Position> allChestPositions = currentFloor.getChestPositions();
		chests = new LinkedList<Chest>();
		int chestNumber = allChestPositions.size();
		for(int i=1; i<=chestNumber; i++){
			if(i%3==0){chests.add(new NormalChest(allChestPositions.get(i-1)));}
			if(i%3==1){chests.add(new NormalChest(allChestPositions.get(i-1)));}
			if(i%3==2){chests.add(new WalkingChest(allChestPositions.get(i-1),i-1, this));}
		}
	}

	private void createMonsters() {
		/*TODO implement correct method */
		List<Position> allMonsterPositions =currentFloor.getMonsterPositions();
		if (!harry) {

		monsters = new LinkedList<Monster>();
		int monsterNumber = allMonsterPositions.size();
		for(int i=0; i<monsterNumber; i++){
			switch(i%3){
				case 0: monsters.add(new Skelett(allMonsterPositions.get(i), this));

					break;
				case 1: monsters.add(new Goblin(allMonsterPositions.get(i), this));
					break;
				case 2: monsters.add(new Wolf(allMonsterPositions.get(i), this));

					break;
			}
		}
		if(finalLevel){
			monsters.add(new Boss(currentFloor.getStairDown(), this));
		}
		} else {
			monsters = new LinkedList<Monster>();
			int monsterNumber = allMonsterPositions.size();
			for (int i = 0; i < monsterNumber; i++) {
				switch (i % 4) {
					case 0:
						monsters.add(new Dementor(allMonsterPositions.get(i), this));

						break;
					case 1:
						monsters.add(new Bellatrix(allMonsterPositions.get(i), this));

						break;
					case 2:
						monsters.add(new Draco(allMonsterPositions.get(i), this));

						break;
					case 3:
						monsters.add(new Lucius(allMonsterPositions.get(i), this));
						break;

				}
			}
			if (finalLevel) {
				monsters.add(new Voldemort(currentFloor.getStairDown(), this));
			}
		}
	}


	private void transferPlayersToNewLevel(){
		List<Position> allPlayerPositions = currentFloor.getInitialPlayerPositions();
		for(int i=0;i<connectedClients; i++){
			players.get(i).setPos(allPlayerPositions.get(i));
			players.get(i).setFloor(currentFloor);
		}
	}

	public Entity getEntityAtPosition(Position pos){
		Entity result = null;
		for(int i=0; i<monsters.size();i++){
			Monster curMonster = monsters.get(i);
			if(curMonster.getPos().equalsPos(pos)){
				result = curMonster;
			}
		}
		for(int j=0; j<players.size();j++){
			Player curPlayer = players.get(j);
			if(curPlayer.getPos().equalsPos(pos)){
				result=curPlayer;
			}
		}
		return result;
	}

	public List<Chest> getChests() {
		return chests;
	}

	public List<Door> getDoors() {
		return doors;
	}

	public Floor getCurrentFloor() {
		return currentFloor;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public IntegerProperty getMapChangeProperty(){
		return mapChange;
	}

	public int getMapChange(){
		return mapChange.get();
	}

	public void setMapChange(int i) throws IOException {
		mapChange.set(i);
		map = updateMap();
	}

	public Spielfeld[] getMap() {
		return map;
	}

	public void setMap(Spielfeld[] map) {
		this.map = map;
	}

	public boolean isSp() {
		return sp;
	}

	public DifficultySettings getDifficultySettings() {
		return difficultySettings;
	}

	public void signalFloorChange() {
		floorChange.set(floorChange.get()+1);
	}

	public IntegerProperty getFloorChange() {
		return floorChange;
	}

	public boolean isHarry() {
		return harry;
	}

	public void setHarry(boolean harry) {
		this.harry = harry;
	}
}
