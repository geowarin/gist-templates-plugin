package com.geowarin.rest.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Gist {
    private String id;
    @SerializedName("html_url")
    private String htmlUrl;
    private Map<String, GistFile> files;
    private String description;

    public String getId() {
        return id;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Map<String, GistFile> getFiles() {
        return files;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Gist{" +
                "id='" + id + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                '}';
    }

    public void setFiles(Map<String, GistFile> files) {
        this.files = files;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
