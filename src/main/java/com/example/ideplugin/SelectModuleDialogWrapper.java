package com.example.ideplugin;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SelectModuleDialogWrapper extends DialogWrapper
        implements OkCallbackDialog<Module> {
    private final JPanel myMainPanel = new JPanel(new BorderLayout());
    private Consumer<Module> consumer;
    private final JList<Module> moduleList;

    protected SelectModuleDialogWrapper(Project project, Module[] modules) {
        super(project, true);
        setTitle("select module");
        moduleList = new JBList<>(modules);
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.setCellRenderer((list1, value, index, isSelected, cellHasFocus) -> new JBLabel(value.getName()));
        myMainPanel.add(moduleList);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return myMainPanel;
    }

    @Override
    protected void doOKAction() {
        if (moduleList.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(myMainPanel, "모듈을 선택하세요");
            return;
        }
        super.doOKAction();
        if (consumer != null) {
            consumer.accept(moduleList.getSelectedValue());
        }
    }

    public void addOkCallback(Consumer<Module> consumer) {
        this.consumer = consumer;
    }
}
