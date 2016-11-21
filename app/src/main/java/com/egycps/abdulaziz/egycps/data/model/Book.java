package com.egycps.abdulaziz.egycps.data.model;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class Book {

    private String id;
    private String title;
    private String image;
    private String file;
    private String category_library_id;

    public Book(String id, String title, String image, String file, String category_library_id) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.file = file;
        this.category_library_id = category_library_id;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCategory_library_id() {
        return category_library_id;
    }

    public void setCategory_library_id(String category_library_id) {
        this.category_library_id = category_library_id;
    }
}
