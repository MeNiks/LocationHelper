package com.niks.locationhelper.places.model;

import java.io.Serializable;

/**
 * Created by niks
 */

public class PlacePrediction implements Serializable{
    private static final long serialVersionUID = 4458531217616869407L;
    private String id,description,place_id,reference;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public static class PlaceTypes{
        public static String GEOCODE="geocode";
    }
}
