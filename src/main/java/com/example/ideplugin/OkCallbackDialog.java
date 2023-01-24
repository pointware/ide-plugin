package com.example.ideplugin;

import java.util.function.Consumer;

public interface OkCallbackDialog<T> {
    void addOkCallback(Consumer<T> consumer);
}
