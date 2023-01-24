package com.example.ideplugin.test;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SelectFromListDialog;

import javax.swing.*;
import java.awt.*;

public class MyDialogWrapper extends SelectFromListDialog {

    public MyDialogWrapper(Project project) {
//        Object[] strings =

        super(project,
                new JCheckBox[]{
                        new JCheckBox("cb1"),
                        new JCheckBox("cb2"),
                        new JCheckBox("cb3")
                },
                new ToStringAspect() {
                    @Override
                    public String getToStirng(Object obj) {
                        return "getToString";
                    }
                },
                "title",
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JCheckBox checkBox = new JCheckBox("select All");
        this.addToDialog(checkBox, BorderLayout.NORTH);
    }

//    protected MyDialogWrapper(Project project) {
//        super(project);
//
//        setTitle("Test DialogWrapper");
//    }
//
//    @Override
//    protected @Nullable JComponent createCenterPanel() {
//        JPanel dialogPanel = new JPanel(new BorderLayout());
//
//        JLabel label = new JLabel("testing");
//        label.setPreferredSize(new Dimension(100, 100));
//        dialogPanel.add(label, BorderLayout.CENTER);
//
//        return dialogPanel;
//    }
}
