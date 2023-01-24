package com.example.ideplugin.test;

import javax.swing.*;

public class SelectDocumentPanel {
    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("SelectDocumentPanel");
        frame.setContentPane(new SelectDocumentPanel().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        frame.setVisible(true);
    }
}
