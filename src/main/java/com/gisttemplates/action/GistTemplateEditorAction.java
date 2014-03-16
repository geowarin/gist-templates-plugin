package com.gisttemplates.action;

import com.gisttemplates.gist.GistApplicationCache;
import com.gisttemplates.gist.GistTemplate;
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
        super(new GistTemplateActionHandler());
    }

    private static class GistTemplateActionHandler extends EditorActionHandler {
        @Override
        public void execute(Editor editor, DataContext dataContext) {
            List<TemplateImpl> templates = new ArrayList<TemplateImpl>();
            Project project = editor.getProject();

            GistApplicationCache cache = GistApplicationCache.getInstance();
            for (GistTemplate gist : cache.getAllGists(project)) {
                TemplateImpl template = new GistTemplateImpl(gist);
                templates.add(template);
            }

            ListTemplatesHandler.showTemplatesLookup(project, editor, "", templates);
        }
    }
}
