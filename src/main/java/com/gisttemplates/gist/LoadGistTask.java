package com.gisttemplates.gist;

import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.util.ThrowableComputable;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 16/03/2014
 * Time: 15:40
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
class LoadGistTask implements ThrowableComputable<List<GistTemplate>, IOException> {
    private final GistService gistService;
    private final String githubUserName;
    private final boolean includeFavorites;

    LoadGistTask(GistService gistService, String githubUserName, boolean includeFavorites) {
        this.gistService = gistService;
        this.githubUserName = githubUserName;
        this.includeFavorites = includeFavorites;
    }

    @Override
    public List<GistTemplate> compute() throws IOException {
        List<Gist> gistList = gistService.getGists(githubUserName);
        List<Gist> starredGists = includeFavorites ? gistService.getStarredGists() : Collections.<Gist>emptyList();
        return loadGists(gistList, starredGists, gistService);
    }

    private List<GistTemplate> loadGists(List<Gist> gistList, List<Gist> starredGists, GistService gistService) throws IOException {
        List<GistTemplate> loadedGists = new ArrayList<GistTemplate>(gistList.size());
        for (Gist gist : gistList) {
            Gist loadedGist = gistService.getGist(gist.getId());
            loadedGists.add(new GistTemplate(loadedGist, false));
            ProgressManager.getInstance().getProgressIndicator().setFraction((double) loadedGists.size() / gistList.size());
        }
        for (Gist gist : starredGists) {
            Gist loadedGist = gistService.getGist(gist.getId());
            loadedGists.add(new GistTemplate(loadedGist, true));
            ProgressManager.getInstance().getProgressIndicator().setFraction((double) loadedGists.size() / gistList.size());
        }
        return loadedGists;
    }
}
