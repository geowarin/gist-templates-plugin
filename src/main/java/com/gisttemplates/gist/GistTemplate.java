package com.gisttemplates.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;

/**
 * Date: 16/03/2014
 * Time: 16:01
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplate {
    private Gist gist;
    private final boolean isStarred;

    public GistTemplate(Gist gist, boolean starred) {
        this.gist = gist;
        isStarred = starred;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public GistFile getFirstFile() {
        return gist.getFiles().values().iterator().next();
    }

    public boolean isLoaded() {
        return getFirstFile().getContent() != null;
    }

    public String getDescription() {
        return gist.getDescription();
    }

    public void load() throws IOException {
        GistService gistService = new GistService(GistApplicationCache.getInstance().getGithubClient());
        gist = gistService.getGist(gist.getId());
    }

    public Object getId() {
        return gist.getId();
    }
}
