package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 9/29/16.
 */
public class Offer {

    private String id;
    private String name;
    private String description;
    private String information;
    private String category_offer_id;
    private String image;

    public Offer(String id, String name, String description, String information, String category_offer_id, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.information = information;
        this.category_offer_id = category_offer_id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCategory_offer_id() {
        return category_offer_id;
    }

    public void setCategory_offer_id(String category_offer_id) {
        this.category_offer_id = category_offer_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
