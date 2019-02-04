package master;

import model.util.Position;
import network.messages.SpielzugMessage;

/**
 * Created by Felix on 10.12.2015.
 */
public class ActingInformation {

    String entityType;
    int indexInList;
    SpielzugMessage spielzug;
    int spielzügeIndex;
    Position nextField;

    public ActingInformation(String entityType, int indexInList, SpielzugMessage spielzug, Position nextField){
        this.entityType=entityType;
        this.indexInList=indexInList;
        this.spielzug=spielzug;
        this.nextField=nextField;
    }

    public int getIndexInList() {
        return indexInList;
    }

    public int getSpielzügeIndex() {
        return spielzügeIndex;
    }

    public SpielzugMessage getSpielzug() {
        return spielzug;
    }

    public Position getNextField() {
        return nextField;
    }

    public String getEntityType() {
        return entityType;
    }
}
