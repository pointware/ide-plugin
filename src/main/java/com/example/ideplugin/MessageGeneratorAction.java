package com.example.ideplugin;

import com.example.ideplugin.config.DocumentConfigManager;
import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        final ModuleManager moduleManager = ModuleManager.getInstance(project);
        final DocumentConfigManager documentConfigManager = new DocumentConfigManager(project);
        final DocumentPluginConfig config = documentConfigManager.load();

        if (!config.isExistDestinationModule()) {
            final SelectModuleDialogWrapper selectModuleDialog = new SelectModuleDialogWrapper(project, moduleManager.getModules());
            selectModuleDialog.addOkCallback((module) -> {
                config.setDestinationModule(module.getName());
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
            // 전문 리스트,
            // 생성할 패스.

            Module moduleByName = ModuleManager.getInstance(project).findModuleByName(config.getDestinationModule());
            ModuleRootManager instance = ModuleRootManager.getInstance(moduleByName);
            Set<String> collect = Arrays.stream(instance.getSourceRoots())
                    .map(o -> o.getPath())
                    .collect(Collectors.toSet());

        });
        dialog.show();
    }
}