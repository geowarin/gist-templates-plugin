package com.gisttemplates.adapter;


import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

/**
 * Date: 27/03/2014
 * Time: 07:01
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public abstract class EditorActionFactory {

    public static EditorActionFactory getInstance() {
        return ServiceManager.getService(EditorActionFactory.class);
    }

    public abstract EditorActionHandler createActionHandler();
}
