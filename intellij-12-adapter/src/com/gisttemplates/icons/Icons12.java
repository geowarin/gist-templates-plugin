package com.gisttemplates.icons;

import com.gisttemplates.adapter.Icons;
import com.intellij.icons.AllIcons;
import icons.GithubIcons;

import javax.swing.*;

/**
 * Date: 22/03/2014
 * Time: 12:25
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@SuppressWarnings("UnusedDeclaration")
public class Icons12 extends Icons {
    @Override public Icon warning() {
        return AllIcons.General.BalloonWarning;
    }

    @Override public Icon github() {
        return GithubIcons.Github_icon;
    }

    @Override public Icon favorite() {
        return AllIcons.Toolwindows.ToolWindowFavorites;
    }

    @Override public Icon textFile() {
        return AllIcons.FileTypes.Text;
    }
}
