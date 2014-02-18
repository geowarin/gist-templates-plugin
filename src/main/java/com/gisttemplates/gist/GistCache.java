package com.gisttemplates.gist;

import com.intellij.openapi.diagnostic.Logger;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:32
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistCache {
    private final String githubUserName;
    private final List<Gist> gists = new ArrayList<Gist>();
    private static final Logger LOG = Logger.getInstance(GistCache.class.getName());


    public GistCache(String githubUserName) {
        this.githubUserName = githubUserName;
    }

    public void fetch(GitHubClient githubClient) {
        GistService gistService = new GistService(githubClient);
        try {
            List<Gist> gistList = gistService.getGists(githubUserName);
            for (Gist gist : gistList) {
                gists.add(gistService.getGist(gist.getId()));
            }

        } catch (IOException e) {
            LOG.error("Error while fetching gist for " + githubUserName, e);
        }
    }

    public List<Gist> getGists() {
        return gists;
    }
}
