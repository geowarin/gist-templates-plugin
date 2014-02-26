package com.gisttemplates.adapter;

import com.intellij.openapi.components.ServiceManager;

/**
 * Date: 25/02/2014
 * Time: 19:52
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public abstract class GithubAdapter {

    public static GithubAdapter getInstance() {
        return ServiceManager.getService(GithubAdapter.class);
    }

    public abstract boolean isCredentialsDefined();

    public abstract String getLogin();

    public abstract String getPassword();
}
