package com.example.bingebox.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Entity_Movie {
    @ColumnInfo(name = "id_no")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "image_url")
    private String imgUrl;
    @ColumnInfo(name = "movie_title")
    private String title;
    @ColumnInfo(name = "movie_type")
    private String type;
    @ColumnInfo(name = "movie_year")
    private String year;
    @ColumnInfo(name = "movie_staus")
    private String status;
//    @ColumnInfo(name = "movie_rating")
//    private String rating;

    public Entity_Movie(String imgUrl, String title, String type, String year, String status) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.type = type;
        this.year = year;
        this.status = status;
    }

    public Entity_Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
