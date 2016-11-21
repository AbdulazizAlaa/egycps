package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class Magazine {

    private String id;
    private String title;
    private String image;
    private String pdf;

    public Magazine(String id, String title, String image, String pdf) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.pdf = pdf;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
