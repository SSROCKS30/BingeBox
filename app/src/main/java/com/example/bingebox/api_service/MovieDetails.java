package com.example.bingebox.api_service;

import com.example.bingebox.api_service.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    @SerializedName("i")
    @Expose
    private Image imgUrl;
    @SerializedName("l")
    @Expose
    private String title;
    @SerializedName("qid")
    @Expose
    private String type;
    @SerializedName("y")
    @Expose
    private String year;

    public Image getImage() {
        return imgUrl;
    }

    public void setImage(Image imgUrl) {
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

}

