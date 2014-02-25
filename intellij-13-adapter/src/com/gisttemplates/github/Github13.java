package com.gisttemplates.github;

import com.gisttemplates.adapter.GithubAdapter;
import org.jetbrains.plugins.github.util.GithubAuthData;
import org.jetbrains.plugins.github.util.GithubSettings;

/**
 * Date: 25/02/2014
 * Time: 21:17
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class Github13 extends GithubAdapter {

    @Override
    public boolean isCredentialsDefined() {
        return GithubSettings.getInstance().isAuthConfigured();
    }

    @Override
    public String getLogin() {
        return GithubSettings.getInstance().getLogin();
    }

    @Override
    public String getPassword() {
        GithubAuthData.BasicAuth basicAuth = getBasicAuth();
        assert basicAuth != null;
        return basicAuth.getPassword();
    }

    private GithubAuthData.BasicAuth getBasicAuth() {
        return GithubSettings.getInstance().getAuthData().getBasicAuth();
    }
}
