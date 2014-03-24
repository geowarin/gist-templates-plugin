package com.geowarin.rest.api;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Map;

public class Gist {
    private String id;
    @SerializedName("html_url")
    private String htmlUrl;
    private Map<String, GistFile> files;

    public String getId() {
        return id;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Map<String, GistFile> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Gist{" +
                "id='" + id + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                '}';
    }
}
