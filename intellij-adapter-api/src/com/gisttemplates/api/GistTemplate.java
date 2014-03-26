package com.gisttemplates.api;

import com.geowarin.rest.api.Gist;
import com.geowarin.rest.api.GistFile;

import java.util.Collection;

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

    public Collection<GistFile> getFiles() {
        return gist.getFiles().values();
    }

    public String getDescription() {
        return gist.getDescription();
    }

    public String getId() {
        return gist.getId();
    }

    public String getFilename() {
        return getFirstFile().getFilename();
    }

    public void setGist(Gist gist) {
        this.gist = gist;
    }

    public String getFirstFileContent() {
        return getFirstFile().getContent();
    }
}
