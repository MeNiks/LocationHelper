package com.niks.locationhelper.direction.model;


import android.location.Location;

import com.niks.baseutils.model.KeyValueModel;

public class DirectionStep {
    private KeyValueModel distance_text,distance_value,polyline;
    private KeyValueModel duration_text,duration_value;
    private Location start_location,end_location;
    private String html_instructions,travel_mode;

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

    public KeyValueModel getPolyline() {
        return polyline;
    }

    public void setPolyline(KeyValueModel polyline) {
        this.polyline = polyline;
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

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }
}
