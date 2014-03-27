package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.gisttemplates.gist.GistService;
import com.gisttemplates.api.GistTemplate;
import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;

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
        super(EditorActionFactory.getInstance().createActionHandler(createGistTemplatesList()));
    }

    private static List<TemplateImpl> createGistTemplatesList() {
        List<TemplateImpl> templates = new ArrayList<TemplateImpl>();
        for (GistTemplate gist : GistService.getInstance().fetchGistList()) {
            templates.add(new LazyGistTemplate(gist));
        }
        return templates;
    }
}
