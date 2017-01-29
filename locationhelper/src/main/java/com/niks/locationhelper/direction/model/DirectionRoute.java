package com.niks.locationhelper.direction.model;


import java.util.ArrayList;
/**
 * Created by niks
 */
public class DirectionRoute {
    private ArrayList<DirectionLeg> directionLegArrayList;

    public ArrayList<DirectionLeg> getDirectionLegArrayList() {
        return directionLegArrayList;
    }

    public void setDirectionLegArrayList(ArrayList<DirectionLeg> directionLegArrayList) {
        this.directionLegArrayList = directionLegArrayList;
    }
}
