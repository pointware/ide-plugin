package com.example.ideplugin;

import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EditMessageTableDialogWrapper extends DialogWrapper implements OkCallbackDialog<Collection<String>> {

    private final JPanel myMainPanel = new JPanel(new BorderLayout());
    private final JBTable jbTable;
    private Consumer<Collection<String>> consumer;
    private final Project project;

    private final Consumer<Collection<String>> callback = new Consumer<>() {
        @Override
        public void accept(Collection<String> strings) {
            DefaultTableModel model = (DefaultTableModel) jbTable.getModel();
            strings.forEach(documentId -> model.addRow(new Object[]{false, documentId}));
        }
    };

    protected EditMessageTableDialogWrapper(Project project, DocumentPluginConfig documentConfig) {
        super(project, true);
        setTitle("생성할 전문을 수정하거나 삭제하세요");
        this.project = project;
        jbTable = new JBTable(new DocumentTableModel(documentConfig, false));
        myMainPanel.add(getTopButtonPanel(), BorderLayout.NORTH);
        myMainPanel.add(jbTable);
        myMainPanel.add(new JBScrollPane(jbTable));
        init();
    }

    private JPanel getTopButtonPanel() {
        final JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JButton add = new JButton("Add");
        add.addActionListener((e) -> {
            InsertDocumentDialogWrapper insertDocumentDialogWrapper = new InsertDocumentDialogWrapper(project);
            insertDocumentDialogWrapper.addOkCallback(callback);
            insertDocumentDialogWrapper.showAndGet();
        });
        final JButton remove = new JButton("Remove");
        remove.addActionListener((e) -> {
            final DefaultTableModel model = (DefaultTableModel) jbTable.getModel();

            int rowCount = model.getRowCount();
            for (int i=rowCount - 1; i >= 0; i--) {
                Boolean valueAt = (Boolean) model.getValueAt(i, 0);
                if (valueAt) {
                    int i1 = jbTable.convertRowIndexToModel(i);
                    model.removeRow(i1);
                }
            }
        });

        jPanel.add(add);
        jPanel.add(remove);
        return jPanel;
    }

    public void addOkCallback(Consumer<Collection<String>> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected void doOKAction() {
        if (consumer != null) {
            consumer.accept(getDocumentList());
        }
        super.doOKAction();
    }

    private Set<String> getDocumentList() {
        final DefaultTableModel model = (DefaultTableModel) jbTable.getModel();
        final Set<String> selectedDocument = model.getDataVector().stream()
                .map(o -> (String) o.get(1))
                .collect(Collectors.toSet());
        return selectedDocument;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return myMainPanel;
    }
}
