package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.api.GistService;
import com.gisttemplates.api.LazyGistTemplate;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.editor.actionSystem.EditorAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09/02/2014
 * Time: 16:01
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistTemplateEditorAction extends EditorAction {

    public GistTemplateEditorAction() {
        super(EditorActionFactory.getInstance().createActionHandler());
    }
}
