package com.gisttemplates;

import com.gisttemplates.gist.GistCache;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import org.eclipse.egit.github.core.Gist;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 20:56
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplateApplication implements ApplicationComponent {
    private final List<GistCache> caches = new ArrayList<GistCache>();

    public GistTemplateApplication() {
    }

    public void initComponent() {
        addCacheForUser("geowarin");
    }

    public void disposeComponent() {
    }

    public void addCacheForUser(String githubUserName) {
        GistCache gistCache = new GistCache(githubUserName);
        gistCache.fetch();
        caches.add(gistCache);
    }

    public List<Gist> getAllGists() {
        List<Gist> allGists = new ArrayList<Gist>();
        for (GistCache cache : caches) {
            allGists.addAll(cache.getGists());
        }
        return allGists;
    }

    public String getComponentName() {
        return "GistTemplateApplication";
    }
}
