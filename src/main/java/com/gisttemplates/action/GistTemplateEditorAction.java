package com.gisttemplates.action;

import com.gisttemplates.GistTemplatesApplication;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.jetbrains.annotations.NotNull;

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

            // TODO : com.intellij.codeInsight.lookup.impl.LookupImpl#insertLookupString
            final GistTemplatesApplication application = GistTemplatesApplication.getInstance();
            MyActionTemplate refreshAction = new MyActionTemplate("refresh") {
                @Override
                public void onAction() {
                    System.out.println("REFRESH");
//                    application.invalidateCaches();
//                    application.getAllGists();
                }
            };

            List<TemplateImpl> templates = new ArrayList<TemplateImpl>();
//            for (Gist gist : application.getAllGists()) {
//                TemplateImpl template = createTemplateFromGist(gist, false);
//                templates.add(template);
//            }
//            for (Gist starredGist : application.getStarredGists()) {
//                TemplateImpl template = createTemplateFromGist(starredGist, true);
//                templates.add(template);
//            }

            new GistTemplateHandler(editor, editor.getProject()).showTemplatesLookup("", refreshAction, templates);
        }
    }

//    public static abstract class MyActionTemplate extends TemplateImpl {
//        public MyActionTemplate(@NotNull String key) {
//            super(key, "gistAction");
//        }
//
//        public abstract void onAction();
//    }

    public static abstract class MyActionTemplate {
        private final String key;

        public abstract void onAction();


        public MyActionTemplate(@NotNull String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }


    private static TemplateImpl createTemplateFromGist(Gist gist, boolean starred) {
        GistFile firstFile = gist.getFiles().values().iterator().next();
        String filename = (starred ? "★ " : "") + firstFile.getFilename();
        TemplateImpl template = new TemplateImpl(filename, "gists");
        template.addTextSegment(firstFile.getContent());
        template.setDescription(gist.getDescription());
        return template;
    }
}
