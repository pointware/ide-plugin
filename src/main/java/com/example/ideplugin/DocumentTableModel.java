package com.example.ideplugin;

import com.example.ideplugin.config.DocumentPluginConfig;

import javax.swing.table.DefaultTableModel;

public class DocumentTableModel extends DefaultTableModel {
    private final Class[] columnClass = new Class[]{Boolean.class, String.class};
    private static final Object[] columns = new Object[]{"isUpdate", "Document"};

    public DocumentTableModel(DocumentPluginConfig config, boolean selected) {
        super(config.getDocumentId().stream()
                .map(documentId -> new Object[]{selected, documentId})
                .toArray(size -> new Object[size][2]), columns);
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }
}
