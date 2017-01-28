package com.niks.locationhelper.direction.model;


import android.location.Location;

import com.niks.baseutils.model.KeyValueModel;

import java.util.ArrayList;

public class DirectionLeg {
    private KeyValueModel distance_text,distance_value;
    private KeyValueModel duration_text,duration_value;
    private String start_address,end_address;
    private Location start_location,end_location;
    private ArrayList<DirectionStep> directionStepArrayList;

    public KeyValueModel getDistance_text() {
        return distance_text;
    }

    public void setDistance_text(KeyValueModel distance_text) {
        this.distance_text = distance_text;
    }

    public KeyValueModel getDistance_value() {
        return distance_value;
    }

    public void setDistance_value(KeyValueModel distance_value) {
        this.distance_value = distance_value;
    }

    public KeyValueModel getDuration_text() {
        return duration_text;
    }

    public void setDuration_text(KeyValueModel duration_text) {
        this.duration_text = duration_text;
    }

    public KeyValueModel getDuration_value() {
        return duration_value;
    }

    public void setDuration_value(KeyValueModel duration_value) {
        this.duration_value = duration_value;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public ArrayList<DirectionStep> getDirectionStepArrayList() {
        return directionStepArrayList;
    }

    public void setDirectionStepArrayList(ArrayList<DirectionStep> directionStepArrayList) {
        this.directionStepArrayList = directionStepArrayList;
    }
}