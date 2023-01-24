package com.example.ideplugin.config;

import com.example.ideplugin.config.DocumentConfig;
import com.example.ideplugin.config.DocumentPluginConfig;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class DocumentConfigManager {

    private final String basePath;

    public DocumentConfigManager(Project project) {
        basePath = project.getBasePath();
    }

    public DocumentPluginConfig load() {
        final File file = new File(basePath + "/mciMessage.yaml");

        if (file.exists() && file.canRead()) {
            try (FileReader fileReader = new FileReader(file)) {
                return DocumentConfig.load(fileReader);
            } catch (IOException e) {
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new DocumentConfig();
    }

    public void persist(DocumentPluginConfig documentConfig) throws IOException {
        File file = new File(basePath + "/mciMessage.yaml");
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            if (documentConfig instanceof DocumentConfig) {
                ((DocumentConfig) documentConfig).persist(fileWriter);
            } else {
                new RuntimeException("not compatible config class");
            }
        }
    }

    public void persist(DocumentPluginConfig documentConfig, Collection<String> documentIds) throws IOException {
        if (documentConfig instanceof DocumentConfig) {
            ((DocumentConfig) documentConfig).setDocumentId(documentIds);
        } else {
            new RuntimeException("not compatible config class");
        }
        persist(documentConfig);
    }
}
