package com.gisttemplates.api;

import com.intellij.openapi.components.ServiceManager;

import java.util.List;

public abstract class GistService {
    public static GistService getInstance() {
        return ServiceManager.getService(GistService.class);
    }

    public abstract void loadGist(GistTemplate gistTemplate);

    public abstract List<GistTemplate> fetchGistList();
}
