package com.gisttemplates.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:32
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistCache {
    private Date lastUpdate;
    private final String githubUserName;
    private final List<Gist> gists = new ArrayList<Gist>();

    public GistCache(String githubUserName) {
        this.githubUserName = githubUserName;
//        GitHubClient client = new GitHubClient();
//        client.setCredentials("user", "passw0rd");
//        List<Gist> starredGists = gistService.getStarredGists();
    }

    public void fetch() {
        GistService gistService = new GistService();
        try {
            List<Gist> gistList = gistService.getGists(githubUserName);
            for (Gist gist : gistList) {
                gists.add(gistService.getGist(gist.getId()));
            }
            lastUpdate = new Date();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<Gist> getGists() {
        return gists;
    }
}
