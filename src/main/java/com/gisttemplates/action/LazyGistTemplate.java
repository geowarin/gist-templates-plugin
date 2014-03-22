package com.gisttemplates.action;

import com.gisttemplates.gist.GistService;
import com.gisttemplates.api.GistTemplate;
import com.intellij.codeInsight.template.impl.TemplateImpl;

/**
 * Date: 16/03/2014
 * Time: 16:40
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class LazyGistTemplate extends TemplateImpl {
    private GistTemplate gist;

    public LazyGistTemplate(GistTemplate gist) {
        super((gist.isStarred() ? "★ " : "") + gist.getFilename(), "gists");
        this.gist = gist;
        setDescription(gist.getDescription());
    }

    @Override
    public String getTemplateText() {
        GistService.getInstance().loadGist(gist);
        return gist.getFirstFileContent();
    }
}
