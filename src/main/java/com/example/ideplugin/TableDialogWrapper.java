package com.example.ideplugin;

import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TableDialogWrapper extends DialogWrapper implements OkCallbackDialog<Collection<String>> {

    private final JPanel myMainPanel = new JPanel(new BorderLayout());
    private final JBTable jbTable;

    private final DocumentPluginConfig documentConfig;
    private Consumer<Collection<String>> consumer;

    protected TableDialogWrapper(Project project, DocumentPluginConfig documentConfig) {
        super(project, true);
        this.documentConfig = documentConfig;

        setTitle("생성할 전문을 선택하세요");
        jbTable = new JBTable(new DocumentTableModel(documentConfig, true));
        myMainPanel.add(new TableTopPanel(jbTable), BorderLayout.NORTH);
        myMainPanel.add(jbTable);
        myMainPanel.add(new JBScrollPane(jbTable));

        init();
    }

    public void addOkCallback(Consumer<Collection<String>> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected void doOKAction() {
        if (consumer != null) {
            consumer.accept(getSelectedDocument());
        }
        super.doOKAction();
    }

    private Set<String> getSelectedDocument() {
        final DefaultTableModel model = (DefaultTableModel) jbTable.getModel();
        final Set<String> selectedDocument = model.getDataVector().stream()
                .filter(o -> (Boolean) o.get(0))
                .map(o -> (String) o.get(1))
                .collect(Collectors.toSet());
        return selectedDocument;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return myMainPanel;
    }
}
