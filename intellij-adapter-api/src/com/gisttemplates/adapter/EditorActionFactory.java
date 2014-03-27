package com.gisttemplates.adapter;


import com.gisttemplates.api.GistTemplate;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

import java.util.List;

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

    public abstract EditorActionHandler createActionHandler(List<TemplateImpl> gistTemplates);
}
