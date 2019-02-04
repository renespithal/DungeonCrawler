package model;

import model.util.Field;
import model.util.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 11.01.2016.
 */
public class FloorReader {

    private int overallPlayerNumber;
    private int xSize;
    private int ySize;
    private Field[] [] fields;
    private List<Position> initialPlayerPositions = new LinkedList<>();
    private List<Position> chestPositions= new LinkedList<>();
    private List<Position> monsterPositions= new LinkedList<>();
    private List<Position> doorPositions= new LinkedList<>();
    private int playerCount;

    public FloorReader(File level, int connectedClients) throws IOException {
        overallPlayerNumber = connectedClients;
        FileReader fileReader = new FileReader(level);
        BufferedReader read = new BufferedReader(fileReader);

        // First line contains xSize & height
        String ln = read.readLine();
        String[] zahlenString = ln.split(",", 3);
        xSize = Integer.parseInt(zahlenString[0]);
        ySize = Integer.parseInt(zahlenString[1]);


        fields= new Field[xSize][ySize];

        ln = read.readLine();
        int i=0;
        int whileCount=0;
        while (whileCount<ySize-1) {
            String[] temp = ln.split(",", xSize + 1);
            for (int j = 0; j < temp.length-1; j++) {
                fields[i][whileCount] = parseField(temp[j], i, whileCount);
                i++;
            }
            i=0;
            whileCount++;
            ln = read.readLine();
        }

        String[] temp = ln.split(",", xSize);
        for (int j = 0; j < temp.length; j++) {
            fields[i][whileCount] = parseField(temp[j], i, whileCount);
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
                Field.cnt content = fields[i][j].getContent();
                if(content.toString().contains("P") &&!relevantPlayerContents.contains(content)){
                    fields[i][j].setContent(Field.cnt.FLOOR);
                }
            }
        }
    }

    public Field getFieldAtPos(Position pos){
        return fields[pos.getX()] [pos.getY()];
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
            case "sd": result = new Field(new Position(posX, posY), Field.cnt.STAIRDOWN); break;
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


    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }


}
