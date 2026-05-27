package com.sanctuary.game;

public class GameContext {

    private final GameSession session;
    private final GameServices services;
    private final GameControllers controllers;

    public GameContext(
            GameSession session,
            GameServices services,
            GameControllers controllers
    ) {
        this.session = session;
        this.services = services;
        this.controllers = controllers;
    }

    public GameSession getSession() {
        return session;
    }

    public GameServices getServices() {
        return services;
    }

    public GameControllers getControllers() {
        return controllers;
    }
}