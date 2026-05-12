package com.sanctuary.input;

public interface InputHandler {

    void register();

    default void unregister() {
    }
}