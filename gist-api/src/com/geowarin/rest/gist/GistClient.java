package com.geowarin.rest.gist;

import com.geowarin.rest.api.Gist;
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

    public Gist getGist(String gistId) throws IOException {
        return connectAndGetResult("https://api.github.com/gists/" + gistId, SINGLE_GIST_TRANSFORMER);
    }

    public List<Gist> getGists(String userName) throws IOException {
        return connectAndGetResult("https://api.github.com/users/" + userName + "/gists", MULTI_GIST_TRANSFORMER);
    }

    public List<Gist> getStarredGists() throws IOException {
        return connectAndGetResult("https://api.github.com/gists/starred", MULTI_GIST_TRANSFORMER);
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

    private <Result> Result connectAndGetResult(String url, Function<JsonElement, Result> resultTransformer) throws IOException {
        JsonReader reader = null;
        try {
            reader = connect(url);
            return resultTransformer.apply(new JsonParser().parse(reader));
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static final Function<JsonElement,Gist> SINGLE_GIST_TRANSFORMER = new Function<JsonElement, Gist>() {
        public Gist apply(JsonElement rootElement) {
            return new Gson().fromJson(rootElement.getAsJsonObject(), Gist.class);
        }
    };

    private static final Function<JsonElement, List<Gist>> MULTI_GIST_TRANSFORMER = new Function<JsonElement, List<Gist>>() {
        public List<Gist> apply(JsonElement rootElement) {
            return gistsFromJson(rootElement.getAsJsonArray());
        }
    };

    private static List<Gist> gistsFromJson(JsonArray gistsJson) {
        List<Gist> gists = new ArrayList<Gist>();
        Gson gson = new Gson();
        for (JsonElement tweetElement : gistsJson) {
            Gist gist = gson.fromJson(tweetElement, Gist.class);
            gists.add(gist);
        }
        return gists;
    }
}
