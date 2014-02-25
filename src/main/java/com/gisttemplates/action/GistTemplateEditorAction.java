package com.gisttemplates.action;

import com.gisttemplates.GistTemplatesApplication;
import com.intellij.codeInsight.template.impl.ListTemplatesHandler;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

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

    private static TemplateImpl createTemplateFromGist(Gist gist, boolean starred) {
        GistFile firstFile = gist.getFiles().values().iterator().next();
        String filename = (starred ? "★ " : "") + firstFile.getFilename();
        TemplateImpl template = new TemplateImpl(filename, "gists");
        template.addTextSegment(firstFile.getContent());
        template.setDescription(gist.getDescription());
        return template;
    }

    private static class GistTemplateActionHandler extends EditorActionHandler {
        @Override
        public void execute(Editor editor, DataContext dataContext) {
            List<TemplateImpl> templates = new ArrayList<TemplateImpl>();

            GistTemplatesApplication application = GistTemplatesApplication.getInstance();
            for (Gist gist : application.getAllGists()) {
                TemplateImpl template = createTemplateFromGist(gist, false);
                templates.add(template);
            }
            for (Gist starredGist : application.getStarredGists()) {
                TemplateImpl template = createTemplateFromGist(starredGist, true);
                templates.add(template);
            }

            ListTemplatesHandler.showTemplatesLookup(editor.getProject(), editor, "", templates);
        }
    }
}
