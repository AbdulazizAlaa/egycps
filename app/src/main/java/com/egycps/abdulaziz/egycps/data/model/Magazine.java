package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class Magazine {

    private String id;
    private String title;
    private String image;
    private String pdf;
    private String category_id;

    public Magazine(String id, String title, String image, String pdf, String category_id) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.pdf = pdf;
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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
