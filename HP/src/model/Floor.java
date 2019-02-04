package model;

import java.io.*;
import java.util.*;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.util.Field;
import model.util.Position;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;

public class Floor {
	
	private int level;
	private int xSize;
	private int ySize;
	private String name;
	private int overallPlayerNumber;
	private int playerCount;
	private List<Position> initialPlayerPositions;
	private List<Position> chestPositions;
	private List<Position> monsterPositions;
	private List<Position> doorPositions;
	private Position stairDown;

	private final ObjectProperty<Field[] []> fields = new SimpleObjectProperty<Field[] []>();
	//private Field[][] fields;

	public Floor( File initialLevel, int connectedClients ) throws IOException {
		level = -1;
		overallPlayerNumber = connectedClients;
		initialPlayerPositions = new LinkedList<Position>();
		chestPositions = new LinkedList<Position>();
		monsterPositions = new LinkedList<Position>();
		doorPositions = new LinkedList<Position>();
		mapImport(initialLevel);
	}
	public Floor(Field[][] fields,SpielerInfo[] spielerInfos,int x,int y){

		this.fields.setValue(fields);
		this.fields.set(fields);
		setPlayers(spielerInfos);
		this.xSize = x;
		this.ySize = y;
	}

	public void mapImport(File loadLevel) throws IOException {
		initialPlayerPositions.clear();
		chestPositions.clear();
		monsterPositions.clear();
		doorPositions.clear();
		playerCount=0;
		level++;
		FileReader fileReader = new FileReader(loadLevel);
		BufferedReader read = new BufferedReader(fileReader);

		// First line contains xSize & height
		String ln = read.readLine();
		String[] zahlenString = ln.split(",", 3);
		xSize = Integer.parseInt(zahlenString[0]);
		ySize = Integer.parseInt(zahlenString[1]);

		fields.setValue(new Field[xSize][ySize]);

		ln = read.readLine();
		int i=0;
		int whileCount=0;
		while (whileCount<ySize-1) {
			String[] temp = ln.split(",", xSize + 1);
			for (int j = 0; j < temp.length-1; j++) {
				fields.get()[i][whileCount] = parseField(temp[j], i, whileCount);
				i++;
			}
			i=0;
			whileCount++;
			ln = read.readLine();
		}

		String[] temp = ln.split(",", xSize);
		for (int j = 0; j < temp.length; j++) {
			fields.get()[i][whileCount] = parseField(temp[j], i, whileCount);
			i++;
		}

		setUnusesPlayerFieldsToFloor();
	}

	public void setUnusesPlayerFieldsToFloor(){
		List<Field.cnt> relevantPlayerContents = new LinkedList<Field.cnt>();
		int count=1;
		while(count<=overallPlayerNumber){
		switch(count) {
			case 1:relevantPlayerContents.add(Field.cnt.PLAYER1);count++;break;
			case 2:relevantPlayerContents.add(Field.cnt.PLAYER2);count++;break;
			case 3:relevantPlayerContents.add(Field.cnt.PLAYER3);count++;break;
			case 4:relevantPlayerContents.add(Field.cnt.PLAYER4);count++;break;
			case 5:relevantPlayerContents.add(Field.cnt.PLAYER5);count++;break;
			case 6:relevantPlayerContents.add(Field.cnt.PLAYER6);count++;break;
			case 7:relevantPlayerContents.add(Field.cnt.PLAYER7);count++;break;
			case 8:relevantPlayerContents.add(Field.cnt.PLAYER8);count++;break;
			}
		}
		for(int i=0; i<xSize; i++){
			for(int j=0; j<ySize; j++){
				Field.cnt content = fields.get()[i][j].getContent();
				if(content.toString().contains("P") &&!relevantPlayerContents.contains(content)){
					fields.get()[i][j].setContent(Field.cnt.FLOOR);
				}
			}
		}
	}

	public Field parseField(String test, int posX, int posY){
		Field result = new Field(new Position(posX, posY), Field.cnt.EMPTY);
		switch(test){
			case "w": result = new Field(new Position(posX, posY), Field.cnt.WALL); break;
			case "m": result = new Field(new Position(posX, posY), Field.cnt.MONSTER);
								monsterPositions.add(new Position(posX,posY));break;
			case "f": result = new Field(new Position(posX, posY), Field.cnt.FLOOR); break;
			case "c": result = new Field(new Position(posX, posY), Field.cnt.CHESTCLOSED);
								chestPositions.add(new Position(posX, posY));break;
			case "su": result = new Field(new Position(posX, posY), Field.cnt.STAIRUP); break;
			case "sd": result = new Field(new Position(posX, posY), Field.cnt.STAIRDOWN);
								stairDown= new Position(posX, posY);break;
			case "dc": result = new Field(new Position(posX, posY), Field.cnt.DOORCLOSED);
								doorPositions.add(new Position(posX, posY)); break;
			case "do": result = new Field(new Position(posX, posY), Field.cnt.DOOROPEN);
								doorPositions.add(new Position(posX, posY)); break;
			case "p":   playerCount++;
				switch(playerCount){
					case 1: result = new Field(new Position(posX, posY), Field.cnt.PLAYER1);break;
					case 2: result = new Field(new Position(posX, posY), Field.cnt.PLAYER2);break;
					case 3: result = new Field(new Position(posX, posY), Field.cnt.PLAYER3);break;
					case 4: result = new Field(new Position(posX, posY), Field.cnt.PLAYER4);break;
					case 5: result = new Field(new Position(posX, posY), Field.cnt.PLAYER5);break;
					case 6: result = new Field(new Position(posX, posY), Field.cnt.PLAYER6);break;
					case 7: result = new Field(new Position(posX, posY), Field.cnt.PLAYER7);break;
					case 8: result = new Field(new Position(posX, posY), Field.cnt.PLAYER8);break;
				};
				initialPlayerPositions.add(new Position(posX, posY));break;
		}
		return result;
	}
	public void setPlayers(SpielerInfo[] spielerInfos){
		for (int i = 0; i> spielerInfos.length; i++){
			int x =spielerInfos[i].getStats().getPlayer_positions().getX();
			int y = spielerInfos[i].getStats().getPlayer_positions().getY();
			fields.get()[x][y].setContent(getRightPlayer(i+1));
		}

	}
	public Field.cnt getRightPlayer(int position){
		switch (position){
			case 1:{
				return Field.cnt.PLAYER1;
			}
			case 2:{
				return Field.cnt.PLAYER2;
			}
			case 3:{
				return Field.cnt.PLAYER3;
			}
			case 4:{
				return Field.cnt.PLAYER4;
			}
			case 5:{
				return Field.cnt.PLAYER5;
			}
			case 6:{
				return Field.cnt.PLAYER6;
			}
			case 7:{
				return Field.cnt.PLAYER7;
			}
			case 8:{
				return Field.cnt.PLAYER8;


		}
			default:{
				return Field.cnt.FLOOR;
			}
	}}

	public Field getFieldAtPos(Position pos){
		Field result=null;
		try {
			result = fields.get()[pos.getX()][pos.getY()];
		}
		catch(ArrayIndexOutOfBoundsException e){
		}
		return result;
	}

	public int getLevel() {
		return level;
	}

	public List<Position> getInitialPlayerPositions() {
		return initialPlayerPositions;
	}

	public Field[][] getFields() {
		return fields.get();
	}

	public ObjectProperty<Field[][]> getFieldsProperty(){
		return fields;
	}

	public List<Position> getMonsterPositions() {
		return monsterPositions;
	}

	public List<Position> getChestPositions() {
		return chestPositions;
	}

	public List<Position> getDoorPositions() {
		return doorPositions;
	}

	public int getYSize() {
		return ySize;
	}

	public int getXSize() {
		return xSize;
	}

	public Position getStairDown() {
		return stairDown;
	}
}
