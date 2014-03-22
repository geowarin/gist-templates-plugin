package com.gisttemplates.icons;

import com.gisttemplates.adapter.Icons;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.plugins.github.GithubUtil;

import javax.swing.*;

/**
 * Date: 22/03/2014
 * Time: 12:24
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@SuppressWarnings("UnusedDeclaration")
public class Icons11 extends Icons {
    private static final Icon Warning = IconLoader.getIcon("/general/balloonWarning.png");
    public static final Icon Favorite = IconLoader.getIcon("/general/toolWindowFavorites.png");
    public static final Icon Text = IconLoader.getIcon("/fileTypes/text.png");


    @Override public Icon warning() {
        return Warning;
    }

    @Override public Icon github() {
        return GithubUtil.GITHUB_ICON;
    }

    @Override public Icon favorite() {
        return Favorite;
    }

    @Override public Icon textFile() {
        return Text;
    }
}
