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
    @SerializedName("q")
    @Expose
    private String type;
    @SerializedName("yr")
    @Expose
    private String year;

    public Image getI() {
        return imgUrl;
    }

    public void setI(Image imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getL() {
        return title;
    }

    public void setL(String title) {
        this.title = title;
    }

    public String getQ() {
        return type;
    }

    public void setQ(String q) {
        this.type = type;
    }


    public String getYr() {
        return year;
    }

    public void setYr(String yr) {
        this.year = year;
    }

}

