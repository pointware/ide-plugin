package com.example.ideplugin;

import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableTopPanel extends JPanel implements ActionListener{
    private final JBTable jbTable;
    private final JButton selectAll;
    private final JButton none;
    public TableTopPanel(JBTable jbTable) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.jbTable = jbTable;

        selectAll = new JButton("selectAll");
        selectAll.addActionListener(this);

        none = new JButton("none");
        none.addActionListener(this);

        add(selectAll);
        add(none);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableModel model = jbTable.getModel();
        boolean enabled = false;
        if (e.getSource() == selectAll) {
            enabled = true;
        }
        for (int index = 0; index < model.getRowCount(); index++) {
            model.setValueAt(enabled, index, 0);
        }
    }
}
