
package com.webdoc.Fragments.video.VideoResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Video {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
