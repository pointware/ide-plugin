package com.example.ideplugin;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@State(name = "MessageGen", storages = { @Storage(value = "mesggen.xml") })
public class SettingManager implements PersistentStateComponent<SettingManager> {

    public static SettingManager getInstance() {
        return ServiceManager.getService(SettingManager.class);
    }

    private String moduleName;
    public SettingManager() {
        if (moduleName == null) {
            moduleName = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    @Override
    public @Nullable SettingManager getState() {
        return null;
    }

    @Override
    public void loadState(@NotNull SettingManager state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
