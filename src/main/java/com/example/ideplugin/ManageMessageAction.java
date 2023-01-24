package com.example.ideplugin;

import com.example.ideplugin.config.DocumentConfigManager;
import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ManageMessageAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        final DocumentConfigManager documentConfigManager = new DocumentConfigManager(project);

        final DocumentPluginConfig config = documentConfigManager.load();

        showEditDocumentDialog(project, documentConfigManager, config);
    }

    private static void showEditDocumentDialog(Project project, DocumentConfigManager documentConfigManager, DocumentPluginConfig config) {
        final EditMessageTableDialogWrapper dialog = new EditMessageTableDialogWrapper(project, config);
        dialog.setSize(600, 400);
        dialog.addOkCallback((documentIds) -> {
            try {
                documentConfigManager.persist(config, documentIds);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        dialog.show();
    }
}