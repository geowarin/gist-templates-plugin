package com.gisttemplates.gist;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Multimap;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
