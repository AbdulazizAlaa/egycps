package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class Branch {

    private String id;
    private String name;
    private String latitude;
    private String longitude;
    private String offer_id;

    public Branch(String id, String name, String latitude, String longitude, String offer_id) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.offer_id = offer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }
}
