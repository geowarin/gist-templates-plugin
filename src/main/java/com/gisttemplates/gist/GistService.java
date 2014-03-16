package com.gisttemplates.gist;

import com.gisttemplates.adapter.GithubAdapter;
import com.gisttemplates.configuration.GistTemplatesSettings;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 16/03/2014
 * Time: 14:51
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistService {
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
        List<GistTemplate> allGists = new ArrayList<GistTemplate>();
        for (GistAccountFetcher cache : getAccountList()) {
            List<GistTemplate> gistTemplates = cache.fetchGistsList(getGithubClient(), project);
            allGists.addAll(gistTemplates);
        }
        return allGists;
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
