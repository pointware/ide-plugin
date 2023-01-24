package com.example.ideplugin.config;

import java.util.List;

public interface DocumentPluginConfig {
    void setDestinationModule(String moduleName);
    String getDestinationModule();

    List<String> getDocumentId();
    void addDocumentId(String documentId);

    boolean isExistDestinationModule();
}
