package com.example.ideplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InsertDocumentDialogWrapper extends DialogWrapper implements OkCallbackDialog<Collection<String>> {
    private final JPanel myMainPanel = new JPanel(new BorderLayout());
    private Consumer<Collection<String>> consumer;
    private final JTextArea textArea;
    protected InsertDocumentDialogWrapper(@Nullable Project project) {
        super(project, false);

        textArea = new JTextArea(10, 50);
        myMainPanel.add(textArea);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return myMainPanel;
    }

    @Override
    public void addOkCallback(Consumer<Collection<String>> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();

        String text = textArea.getText();
        Set<String> collect = Arrays.stream(text.split("\n|,"))
                .filter(o -> !o.isBlank())
                .collect(Collectors.toSet());
        consumer.accept(collect);
    }
}
