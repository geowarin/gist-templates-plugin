package com.geowarin.rest.gist;

import com.geowarin.rest.api.Gist;
import com.google.common.base.Function;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.intellij.util.Base64Converter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GistClient {
    private final String credentials;
    private static final String HEADER_AUTHORIZATION = "Authorization";

    public GistClient() {
        credentials = null;
    }

    public GistClient(String user, String password) {
        this.credentials = "Basic " + Base64Converter.encode(user + ':' + password);
    }

    private <Result> Result connect(String url, Function<JsonElement, Result> function) throws IOException {
        JsonReader reader = null;
        try {
            reader = connect(url);
            return function.apply(new JsonParser().parse(reader));
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public Gist getGist(String gistId) throws IOException {
        return connect("https://api.github.com/gists/" + gistId, new Function<JsonElement, Gist>() {
            @Override public Gist apply(JsonElement rootElement) {
                JsonObject gistsJson = rootElement.getAsJsonObject();
                return new Gson().fromJson(gistsJson, Gist.class);
            }
        });
    }

    public List<Gist> getGists(String userName) throws IOException {

        JsonReader reader = connect("https://api.github.com/users/" + userName + "/gists");

        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(reader);
        JsonArray gistsJson = rootElement.getAsJsonArray();

        List<Gist> gists = new ArrayList<Gist>();
        Gson gson = new Gson();
        for (JsonElement tweetElement : gistsJson) {
            Gist gist = gson.fromJson(tweetElement, Gist.class);
            gists.add(gist);
        }

        return gists;
    }

    public List<Gist> getStarredGists() throws IOException {

        JsonReader reader = connect("https://api.github.com/gists/starred");

        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(reader);
        JsonArray gistsJson = rootElement.getAsJsonArray();

        List<Gist> gists = new ArrayList<Gist>();
        Gson gson = new Gson();
        for (JsonElement tweetElement : gistsJson) {
            Gist gist = gson.fromJson(tweetElement, Gist.class);
            gists.add(gist);
        }

        return gists;
    }


    private JsonReader connect(String fetchUrl) throws IOException {
        HttpURLConnection request = (HttpURLConnection) new URL(fetchUrl).openConnection();

        if (credentials != null) {
            request.setRequestProperty(HEADER_AUTHORIZATION, credentials);
        }

        String rateLimit = request.getHeaderField("X-RateLimit-Limit");
        System.out.println("rateLimit = " + rateLimit);

        request.connect();
        return new JsonReader(new InputStreamReader(request.getInputStream()));
    }

}
