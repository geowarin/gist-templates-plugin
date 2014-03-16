package com.gisttemplates.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

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

    public String getContent() {
        return getFirstFile().getContent();
    }
}
