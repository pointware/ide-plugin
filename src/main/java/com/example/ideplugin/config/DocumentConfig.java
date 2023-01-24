package com.example.ideplugin.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

final class DocumentConfig implements DocumentPluginConfig {
    private static final Yaml yaml = new Yaml();

    private Map<String, Object> getConfigMap() {
        return configMap;
    }

    private final Map<String, Object> configMap = new HashMap<>();

    void persist(FileWriter fileWriter) {
        yaml.dump(configMap, fileWriter);
    }

    static DocumentConfig load(FileReader fileReader) {
        DocumentConfig documentConfig = new DocumentConfig();
        Map<String, Object> loadConfig = yaml.load(fileReader);
        if (loadConfig != null) {
            documentConfig.getConfigMap().putAll(loadConfig);
        }
        return documentConfig;
    }

    DocumentConfig() {
        configMap.put("documentId", new ArrayList<>());
        configMap.put("moduleName", "");
    }

    public String getDestinationModule() {
        return (String) configMap.get("moduleName");
    }

    public void setDestinationModule(String moduleName) {
        configMap.put("moduleName", moduleName);
    }

    public boolean isExistDestinationModule() {
        String destinationModule = getDestinationModule();
        if (destinationModule == null || destinationModule.isBlank()) {
            return false;
        }
        return true;
    }

    public List<String> getDocumentId() {
        return (List<String>) configMap.getOrDefault("documentId", new ArrayList<>());
    }

    public void addDocumentId(String documentId) {
        getDocumentId().add(documentId);
    }

    public void setDocumentId(Collection<String> documentIds) {
        List<String> documentId = getDocumentId();
        documentId.clear();
        documentId.addAll(documentIds);
    }
}
