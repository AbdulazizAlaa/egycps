package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 9/29/16.
 */
public class Offer {

    private String id;
    private String title;
    private String desc;
    private String info;
    private String category_id;
    private String image;

    public Offer(String id, String title, String desc, String info, String category_id, String image) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.info = info;
        this.category_id = category_id;
        this.image = image;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
