package com.example.nasaphotoapp;

import com.google.gson.annotations.SerializedName;

import java.time.temporal.Temporal;

public class NasaPhoto {
    @SerializedName("title")
    private String title;

    @SerializedName("explanation")
    private String desc;

    @SerializedName("url")
    private String url;

    @SerializedName("date")
    private String date;

    public String getTitle() { return title; }

    public String getDesc() { return desc; }

    public String getUrl() { return url; }

    public String getDate() { return date; }
}
