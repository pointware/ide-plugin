package com.example.ideplugin;

import com.example.gen.EngineGenerator;
import com.example.ideplugin.config.DocumentConfigManager;
import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        final DocumentConfigManager documentConfigManager = new DocumentConfigManager(project);
        final DocumentPluginConfig config = documentConfigManager.load();

        if (!config.isExistDestinationModule()) {
            final SelectModuleDialogWrapper selectModuleDialog = new SelectModuleDialogWrapper(project);
            selectModuleDialog.addOkCallback((module) -> {
                config.setDestinationModule(module);
                showSelectDocument(project, documentConfigManager, config);
            });
            selectModuleDialog.setSize(600, 400);
            selectModuleDialog.show();
        } else {
            showSelectDocument(project, documentConfigManager, config);
        }
    }

    private static void showSelectDocument(Project project, DocumentConfigManager documentConfigManager, DocumentPluginConfig config) {
        final TableDialogWrapper dialog = new TableDialogWrapper(project, config);
        dialog.setSize(600, 400);
        dialog.addOkCallback((documentIds) -> {
            try {
                documentConfigManager.persist(config);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            ProgressManager.getInstance()
                    .runProcessWithProgressSynchronously(() -> {
                new EngineGenerator().generate();
            }, "processing title", false, project);

        });
        dialog.show();
    }
}