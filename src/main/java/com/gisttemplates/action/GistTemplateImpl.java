package com.gisttemplates.action;

import com.gisttemplates.gist.GistTemplate;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.IOException;

/**
 * Date: 16/03/2014
 * Time: 16:40
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplateImpl extends TemplateImpl {
    private GistTemplate gist;

    public GistTemplateImpl(GistTemplate gist) {
        super((gist.isStarred() ? "★ " : "") + gist.getFirstFile().getFilename(), "gists");
        this.gist = gist;
        setDescription(gist.getDescription());
    }

    @Override
    public String getTemplateText() {
        loadGist();
        addTextSegment(gist.getFirstFile().getContent());
        return super.getTemplateText();
    }

    private void loadGist() {
        if (!gist.isLoaded()) {
            try {
                gist.load();
            } catch (IOException e) {
                notifyFetchError(e);
            }
        }
    }

    private void notifyFetchError(IOException e) {
        Notifications.Bus.notify(new Notification("GistTemplates", "Error while fetching gist " + gist.getId(), e.getMessage(), NotificationType.ERROR));
    }

}
