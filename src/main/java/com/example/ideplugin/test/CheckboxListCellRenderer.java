package com.example.ideplugin.test;

import javax.swing.*;
import java.awt.*;

public class CheckboxListCellRenderer<T> extends JCheckBox implements ListCellRenderer<T> {
    @Override
    public Component getListCellRendererComponent(JList<? extends T> list,
                                                  T value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());
        setText("asdf"+index);
        setSelected(isSelected);
        return this;
    }
}
