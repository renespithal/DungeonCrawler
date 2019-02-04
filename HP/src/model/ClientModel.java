package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.entity.Entity;
import model.entity.Monster;
import model.entity.Player;
import model.entity.monster.Goblin;
import model.entity.monster.Skelett;
import model.entity.monster.Wolf;
import model.entity.player.*;
import model.furniture.Chest;
import model.furniture.Door;
import model.furniture.NormalChest;
import model.util.Field;
import model.util.Position;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClientModel {

	private List<File> levels;
	private int connectedClients;
	private List<String> chosenClasses;

	private static Floor currentFloor;
	private Field[][] field;
	private List<Player> players;
	private List<Monster> monsters;
	private List<Chest> chests;
	private List<Door> doors;
	private final IntegerProperty mapChange = new SimpleIntegerProperty(0);
	private Player selfPlayer;
	private boolean harry;

	public ClientModel(Floor serverFloor, String nick, List<Player> playerList, List<Monster> monsterList, boolean harry) {
		this.harry = harry;
		this.currentFloor = serverFloor;
		this.players = playerList;
		searchSelfPlayer(nick);
		//this.selfPlayer = playerList.get(0); //TODO ist falsch ( nur für allein test )

		this.monsters = monsterList;
		this.field = currentFloor.getFields();
		searchChests();
		;

	}

	public void searchSelfPlayer(String s){
		for (Player player : players){
			if (player.getNick().equals(s)){
				this.selfPlayer = player;
			}

		}}
	public void searchChests(){
		chests = new LinkedList<Chest>();
		for (int i = 0; i < currentFloor.getXSize(); i ++){
			for (int j=0; j< currentFloor.getYSize(); j++){
				if (field[i][j].getContent().equals(Field.cnt.CHESTCLOSED)){
					Chest chest = new NormalChest(new Position(i,j));
					chests.add(chest);
				}
			}
		}

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

	public void setMapChange(int i){
		mapChange.set(i);
	}

	public List<File> getLevels() {
		return levels;
	}

	public void setLevels(List<File> levels) {
		this.levels = levels;
	}

	public int getConnectedClients() {
		return connectedClients;
	}

	public void setConnectedClients(int connectedClients) {
		this.connectedClients = connectedClients;
	}

	public List<String> getChosenClasses() {
		return chosenClasses;
	}

	public void setChosenClasses(List<String> chosenClasses) {
		this.chosenClasses = chosenClasses;
	}

	public static void setCurrentFloor(Floor currentFloor) {
		ClientModel.currentFloor = currentFloor;
	}

	public Field[][] getField() {
		return field;
	}

	public void setField(Field[][] field) {
		this.field = field;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public void setChests(List<Chest> chests) {
		this.chests = chests;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	public IntegerProperty mapChangeProperty() {
		return mapChange;
	}

	public Player getSelfPlayer() {
		return selfPlayer;
	}

	public void setSelfPlayer(Player selfPlayer) {
		this.selfPlayer = selfPlayer;
	}

	public boolean isHarry() {
		return harry;
	}

	public void setHarry(boolean harry) {
		this.harry = harry;
	}
}
