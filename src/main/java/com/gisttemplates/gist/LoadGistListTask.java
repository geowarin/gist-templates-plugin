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
class LoadGistListTask implements ThrowableComputable<List<GistTemplate>, IOException> {
    private final GistService gistService;
    private final String githubUserName;
    private final boolean includeFavorites;

    LoadGistListTask(GistService gistService, String githubUserName, boolean includeFavorites) {
        this.gistService = gistService;
        this.githubUserName = githubUserName;
        this.includeFavorites = includeFavorites;
    }

    @Override
    public List<GistTemplate> compute() throws IOException {
        List<Gist> gistList = gistService.getGists(githubUserName);
        ProgressManager.getInstance().getProgressIndicator().setFraction(0.5);
        List<Gist> starredGists = includeFavorites ? gistService.getStarredGists() : Collections.<Gist>emptyList();
        ProgressManager.getInstance().getProgressIndicator().setFraction(1);
        return loadGists(gistList, starredGists);
    }

    private List<GistTemplate> loadGists(List<Gist> gistList, List<Gist> starredGists) throws IOException {
        List<GistTemplate> loadedGists = new ArrayList<GistTemplate>(gistList.size());
        for (Gist gist : gistList) {
            loadedGists.add(new GistTemplate(gist, false));
        }
        for (Gist gist : starredGists) {
            loadedGists.add(new GistTemplate(gist, true));
        }
        return loadedGists;
    }
}
