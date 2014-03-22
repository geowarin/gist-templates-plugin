package com.gisttemplates.adapter;

import com.intellij.openapi.components.ServiceManager;

import javax.swing.*;

/**
 * Date: 22/03/2014
 * Time: 12:17
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public abstract class Icons {

    public static Icons getInstance() {
        return ServiceManager.getService(Icons.class);
    }

    public abstract Icon warning();

    public abstract Icon github();

    public abstract Icon favorite();

    public abstract Icon textFile();

}
