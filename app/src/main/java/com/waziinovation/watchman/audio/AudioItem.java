package com.waziinovation.watchman.audio;

/**
 * Created by Philip on 4/22/2016.
 */

public class AudioItem {
    private  String title;
    private String content;
    private String details;
    private String url;

    public AudioItem(String audioTitle, String content, String details,String url) {
        this.title = audioTitle;
        this.content = content;
        this.details = details;
        this.url = url;

    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public String getUrl() {
        return url;
    }
}
