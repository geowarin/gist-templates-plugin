package com.gisttemplates.github;

import com.gisttemplates.adapter.GithubAdapter;
import org.jetbrains.plugins.github.GithubSettings;

/**
 * Date: 25/02/2014
 * Time: 21:17
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class Github11 extends GithubAdapter {

    @Override
    public boolean isCredentialsDefined() {
        return !GithubSettings.getInstance().isAnonymous();
    }

    @Override
    public String getLogin() {
        return GithubSettings.getInstance().getLogin();
    }

    @Override
    public String getPassword() {
        return GithubSettings.getInstance().getPassword();
    }
}
