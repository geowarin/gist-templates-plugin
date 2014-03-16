package com.gisttemplates.gist;

import com.gisttemplates.adapter.GithubAdapter;
import com.gisttemplates.configuration.GistTemplatesSettings;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 16/03/2014
 * Time: 14:51
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistApplicationCache {
    private GistTemplatesSettings gistTemplatesSettings;
    private final List<GistCache> caches = new ArrayList<GistCache>();
    private GistCache cacheForGithubUser;
    private boolean areCachesInitialized = false;

    public GistApplicationCache() {
        gistTemplatesSettings = GistTemplatesSettings.getInstance();
    }

    public static GistApplicationCache getInstance() {
        return ServiceManager.getService(GistApplicationCache.class);
    }

    public void invalidateCaches() {
        areCachesInitialized = false;
    }

    public List<GistTemplate> getAllGists(Project project) {
        if (!areCachesInitialized || caches.isEmpty()) {
            initCaches(project);
            areCachesInitialized = true;
        }

        List<GistTemplate> allGists = new ArrayList<GistTemplate>();
        for (GistCache cache : caches) {
            allGists.addAll(cache.getGists());
        }
        return allGists;
    }

    private void initCaches(Project project) {
        initCachesList();
        for (GistCache cache : caches) {
            cache.fetchGists(getGithubClient(), project);
        }
    }

    private void initCachesList() {
        caches.clear();
        if (usePersonalGithubAccount()) {
            cacheForGithubUser = new GistCache(GithubAdapter.getInstance().getLogin(), true);
            caches.add(cacheForGithubUser);
        }
    }

    private boolean usePersonalGithubAccount() {
        return gistTemplatesSettings.isUseGithubAccount()
                && GithubAdapter.getInstance().isCredentialsDefined();
    }

    private GitHubClient getGithubClient() {
        GithubAdapter githubAdapter = GithubAdapter.getInstance();
        GitHubClient client = new GitHubClient();
        if (usePersonalGithubAccount()) {
            client.setCredentials(githubAdapter.getLogin(), githubAdapter.getPassword());
        }
        return client;
    }
}
