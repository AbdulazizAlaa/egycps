package com.egycps.abdulaziz.egycps.data.model;

import android.util.Log;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class News {

    private String id;
    private String title;
    private String description;
    private String image;
    private String created_at;

    public News(String id, String title, String description, String image, String created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
