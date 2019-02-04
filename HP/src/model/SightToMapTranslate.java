package model;

import model.util.Position;

/**
 * Created by Felix on 19.01.2016.
 */
public class SightToMapTranslate {

    private Position orgPosition;
    private Position sightPosition;

    public SightToMapTranslate(Position sightPosition, Position orgPosition){
        this.sightPosition = sightPosition;
        this.orgPosition = orgPosition;
    }


    public Position getOrgPosition() {
        return orgPosition;
    }
}
