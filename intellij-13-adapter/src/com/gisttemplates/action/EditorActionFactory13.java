package com.gisttemplates.action;

import com.gisttemplates.adapter.EditorActionFactory;
import com.gisttemplates.api.GistService;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.api.LazyGistTemplate;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.codeInsight.lookup.*;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.*;
import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Date: 27/03/2014
 * Time: 07:07
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class EditorActionFactory13 extends EditorActionFactory {

    @Override
    public EditorActionHandler createActionHandler() {
        return new GistTemplateActionHandler();
    }

    private class GistTemplateActionHandler extends EditorActionHandler {
        @Override
        protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
            showTemplatesLookup(editor.getProject(), editor, createGistTemplatesList());
        }

        private List<TemplateImpl> createGistTemplatesList() {
            List<TemplateImpl> templates = new ArrayList<TemplateImpl>();
            for (GistTemplate gist : GistService.getInstance().fetchGistList()) {
                templates.add(new LazyGistTemplate(gist));
            }
            return templates;
        }
    }

    public static void showTemplatesLookup(final Project project, final Editor editor, List<TemplateImpl> gistTemplatesList) {
        final LookupImpl lookup = (LookupImpl) LookupManager.getInstance(project).createLookup(editor, LookupElement.EMPTY_ARRAY, "", new TemplatesArranger());
        for (TemplateImpl template : gistTemplatesList) {
            lookup.addItem(createTemplateElement(template), new PlainPrefixMatcher(""));
        }
        showLookup(lookup);
    }

    private static void showLookup(LookupImpl lookup) {
        Editor editor = lookup.getEditor();
        Project project = editor.getProject();
        lookup.addLookupListener(new MyLookupAdapter(project, editor));
        lookup.refreshUi(false, true);
        lookup.showLookup();
    }

    private static LiveTemplateLookupElement createTemplateElement(final TemplateImpl template) {
        return new LiveTemplateLookupElementImpl(template, false) {
            @Override
            public Set<String> getAllLookupStrings() {
                String description = template.getDescription();
                if (description == null) {
                    return super.getAllLookupStrings();
                }
                return ContainerUtil.newHashSet(getLookupString(), description);
            }
        };
    }

    private static class TemplatesArranger extends LookupArranger {
        @Override
        public Pair<List<LookupElement>, Integer> arrangeItems(@NotNull Lookup lookup, boolean onExplicitAction) {
            LinkedHashSet<LookupElement> result = new LinkedHashSet<LookupElement>();
            List<LookupElement> items = getMatchingItems();
            for (LookupElement item : items) {
                if (item.getLookupString().startsWith(lookup.itemPattern(item))) {
                    result.add(item);
                }
            }
            result.addAll(items);
            ArrayList<LookupElement> list = new ArrayList<LookupElement>(result);
            int selected = lookup.isSelectionTouched() ? list.indexOf(lookup.getCurrentItem()) : 0;
            return new Pair<List<LookupElement>, Integer>(list, selected >= 0 ? selected : 0);
        }

        @Override
        public LookupArranger createEmptyCopy() {
            return new TemplatesArranger();
        }
    }

    private static class MyLookupAdapter extends LookupAdapter {
        private final Project myProject;
        private final Editor myEditor;
        private final PsiFile myFile;

        public MyLookupAdapter(Project project, Editor editor) {
            myProject = project;
            myEditor = editor;
            myFile = null;
        }

        @Override
        public void itemSelected(final LookupEvent event) {
            FeatureUsageTracker.getInstance().triggerFeatureUsed("codeassists.liveTemplates");
            final LookupElement item = event.getItem();
            if (item instanceof LiveTemplateLookupElementImpl) {
                final TemplateImpl template = ((LiveTemplateLookupElementImpl) item).getTemplate();
                new WriteCommandAction(myProject) {
                    @Override
                    protected void run(@NotNull Result result) throws Throwable {
                        ((TemplateManagerImpl) TemplateManager.getInstance(myProject)).startTemplateWithPrefix(myEditor, template, null, null);
                    }
                }.execute();
            }
        }
    }
}
