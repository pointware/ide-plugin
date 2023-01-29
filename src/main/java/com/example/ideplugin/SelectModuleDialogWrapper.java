package com.example.ideplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SelectModuleDialogWrapper extends DialogWrapper
        implements OkCallbackDialog<String> {
    private final JPanel myMainPanel = new JPanel(new BorderLayout());
    private Consumer<String> consumer;
    private final JList<String> moduleList;

    protected SelectModuleDialogWrapper(Project project) {
        super(project, true);
        setTitle("select module");
        VirtualFile[] contentSourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
        String basePath = project.getBasePath();
        Set<String> collect = Arrays.stream(contentSourceRoots)
                .map(o -> o.getPath().substring(basePath.length()+1))
                .filter(o -> o.endsWith("/main/java"))
                .collect(Collectors.toSet());

        moduleList = new JBList<>(collect);
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.setCellRenderer((list1, value, index, isSelected, cellHasFocus) -> new JBLabel(value));
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

    public void addOkCallback(Consumer<String> consumer) {
        this.consumer = consumer;
    }
}
