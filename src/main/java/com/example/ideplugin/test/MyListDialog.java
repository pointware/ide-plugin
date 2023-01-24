package com.example.ideplugin.test;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class MyListDialog extends DialogWrapper {
    private final DefaultListModel<String> myModel = new DefaultListModel<>();
    private final JList<String> myList = new JBList<>(myModel);
    private final JPanel myMainPanel = new JPanel(new BorderLayout());

    public MyListDialog(Project project) {
        super(project, true);
        myList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        setTitle("title");

        myModel.addElement("1");
        myModel.addElement("2");
        myModel.addElement("3");


        myList.setModel(myModel);
        myList.add(new JCheckBox("select 1"));
        myList.add(new JCheckBox("select 2"));
        myList.add(new JCheckBox("select 3"));

        myList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//                setOKActionEnabled(myList.getSelectedValues().length > 0);
            }
        });

//        myList.setSelectedIndex(0);

        myList.setCellRenderer(new CheckboxListCellRenderer<>());

        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        myMainPanel.add(myList);
        return myMainPanel;
    }
}
