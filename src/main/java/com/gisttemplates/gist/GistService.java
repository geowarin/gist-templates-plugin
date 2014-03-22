package com.gisttemplates.gist;

import com.gisttemplates.adapter.GithubAdapter;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.configuration.GistTemplatesSettings;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 16/03/2014
 * Time: 14:51
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistService {
    public static final String WEBSITE_URL = "http://geowarin.github.io/gist-templates-plugin";
    private GistTemplatesSettings gistTemplatesSettings;

    public GistService() {
        gistTemplatesSettings = GistTemplatesSettings.getInstance();
    }

    public static GistService getInstance() {
        return ServiceManager.getService(GistService.class);
    }

    public void loadGist(GistTemplate gistTemplate) {
        try {
            Gist gist = loadGist(gistTemplate.getId());
            gistTemplate.setGist(gist);
        } catch (IOException e) {
            notifyFetchError(e, gistTemplate.getId());
        }
    }

    public List<GistTemplate> fetchGistList(Project project) {
        List<GistAccountFetcher> accountList = getAccountList();
        if (accountList.isEmpty()) {
            notifyPluginNotConfigured();
            return Collections.emptyList();
        }

        List<GistTemplate> allGists = new ArrayList<GistTemplate>();
        for (GistAccountFetcher cache : accountList) {
            List<GistTemplate> gistTemplates = cache.fetchGistsList(getGithubClient(), project);
            allGists.addAll(gistTemplates);
        }
        return allGists;
    }

    private void notifyPluginNotConfigured() {
        String message =
                String.format("To use gist templates plugin, please configure your github account in the options. More details <a href=\"%s\">here</a>",
                        WEBSITE_URL);
        Notifications.Bus.notify(new Notification("GistTemplates", "No account configured", message, NotificationType.WARNING, new MyNotificationListener()));
    }

    private class MyNotificationListener implements NotificationListener {
        @Override
        public void hyperlinkUpdate(@NotNull Notification notification, @NotNull HyperlinkEvent event) {
            NotificationListener.URL_OPENING_LISTENER.hyperlinkUpdate(notification, event);
        }
    }


    private GitHubClient getGithubClient() {
        GithubAdapter githubAdapter = GithubAdapter.getInstance();
        GitHubClient client = new GitHubClient();
        if (usePersonalGithubAccount()) {
            client.setCredentials(githubAdapter.getLogin(), githubAdapter.getPassword());
        }
        return client;
    }

    private void notifyFetchError(IOException e, String gistId) {
        Notifications.Bus.notify(new Notification("GistTemplates", "Error while fetching gist " + gistId, e.getMessage(), NotificationType.ERROR));
    }

    private Gist loadGist(String gistId) throws IOException {
        org.eclipse.egit.github.core.service.GistService gistService = new org.eclipse.egit.github.core.service.GistService(getGithubClient());
        return gistService.getGist(gistId);
    }

    private List<GistAccountFetcher> getAccountList() {
        List<GistAccountFetcher> caches = new ArrayList<GistAccountFetcher>();
        if (usePersonalGithubAccount()) {
            GistAccountFetcher cacheForGithubUser = new GistAccountFetcher(GithubAdapter.getInstance().getLogin(), true);
            caches.add(cacheForGithubUser);
        }
        return caches;
    }

    private boolean usePersonalGithubAccount() {
        return gistTemplatesSettings.isUseGithubAccount()
                && GithubAdapter.getInstance().isCredentialsDefined();
    }
}
