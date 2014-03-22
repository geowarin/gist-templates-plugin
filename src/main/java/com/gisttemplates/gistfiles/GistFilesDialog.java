package com.gisttemplates.gistfiles;

import com.gisttemplates.adapter.GUIFactory;
import com.gisttemplates.api.GistTemplate;
import com.gisttemplates.adapter.Icons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.intellij.ui.ListSpeedSearch;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBList;
import com.intellij.util.Function;
import org.eclipse.egit.github.core.GistFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 21/03/2014
 * Time: 11:14
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
public class GistFilesDialog extends DialogWrapper {
    private JPanel mainPanel;
    private JList templateList;
    private CheckboxTree filesTree;
    private JTextArea description;
    private List<GistTemplate> templates;

    public GistFilesDialog(@Nullable Project project, @NotNull List<GistTemplate> templates) {
        super(project);
        this.templates = templates;
        setTitle("Select Files to Insert");
        mainPanel.setPreferredSize(new Dimension(500, 400));
        init();
        templateList.setSelectedIndex(0);
    }

    @Nullable @Override protected JComponent createCenterPanel() {
        return mainPanel;
    }

    @Nullable @Override public JComponent getPreferredFocusedComponent() {
        return templateList;
    }

    private void createUIComponents() {
        templateList = new JBList(templates);
        new ListSpeedSearch(templateList, new Function<Object, String>() {
            @Override public String fun(Object o) {
                return ((GistTemplate) o).getFilename();
            }
        });
        templateList.setCellRenderer(GUIFactory.getInstance().createGistTemplateColoredListCellRenderer());
        templateList.addListSelectionListener(new MyListSelectionListener());

        GistTemplate firstTemplate = templates.get(0);
        CheckedTreeNode checkedTreeNode = getCheckedTreeNode(firstTemplate);
        filesTree = new CheckboxTree(new MyCheckboxTreeCellRenderer(), checkedTreeNode);
    }

    private CheckedTreeNode getCheckedTreeNode(GistTemplate firstTemplate) {
        CheckedTreeNode checkedTreeNode = new CheckedTreeNode(firstTemplate);
        for (GistFile gistFile : firstTemplate.getFiles()) {
            checkedTreeNode.add(new CheckedTreeNode(gistFile));
        }
        return checkedTreeNode;
    }

    public List<GistFile> getSelectedFiles() {
        return Arrays.asList(filesTree.getCheckedNodes(GistFile.class, null));
    }

    public GistTemplate getSelectedTemplate() {
        return (GistTemplate) templateList.getSelectedValue();
    }

    private static class MyCheckboxTreeCellRenderer extends CheckboxTree.CheckboxTreeCellRenderer {
        @Override
        public void customizeRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Object userObject = treeNode.getUserObject();
            if (userObject == null) {
                return;
            }

            if (userObject instanceof GistTemplate) {
                GistTemplate gistTemplate = (GistTemplate) userObject;
                getTextRenderer().append(gistTemplate.getFilename(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, Color.BLACK));
                getTextRenderer().setIcon(Icons.getInstance().github());
                getTextRenderer().setToolTipText(gistTemplate.getDescription());
            } else if (userObject instanceof GistFile) {
                GistFile gistFile = (GistFile) userObject;
                getTextRenderer().append(gistFile.getFilename(), new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, Color.BLACK));
                getTextRenderer().setIcon(Icons.getInstance().textFile());
            }
        }
    }

    private class MyListSelectionListener implements ListSelectionListener {
        @Override public void valueChanged(@NotNull ListSelectionEvent e) {
            GistTemplate selectedTemplate = (GistTemplate) templateList.getSelectedValue();
            if (e.getValueIsAdjusting() || selectedTemplate == null) {
                return;
            }

            CheckedTreeNode checkedTreeNode = getCheckedTreeNode(selectedTemplate);
            description.setText(selectedTemplate.getDescription());
            filesTree.setModel(new DefaultTreeModel(checkedTreeNode));
        }
    }
}
