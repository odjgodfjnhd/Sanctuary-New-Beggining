package com.sanctuary.maps;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.core.GameState;
import com.sanctuary.entities.EntityType;
import com.sanctuary.entities.GameEntityFactory;

public class GameMap {
    private String mapName;

    public GameMap(String mapName) {
        this.mapName = mapName;
    }

    public void load() {
        System.out.println("=== Загрузка карты ===");

        // Очищаем все сущности
        FXGL.getGameWorld().getEntities().forEach(Entity::removeFromWorld);

        // Регистрируем фабрику
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());

        // Загружаем карту
        try {
            FXGL.setLevelFromMap(mapName + ".tmx");
            System.out.println("Карта загружена: " + mapName + ".tmx");
        } catch (Exception e) {
            System.err.println("Ошибка загрузки карты: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Находим точку спавна
        var spawnPoints = FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER_SPAWN);
        System.out.println("Найдено точек спавна: " + spawnPoints.size());

        if (!spawnPoints.isEmpty()) {
            Entity spawn = spawnPoints.get(0);
            GameState.spawnX = spawn.getX();
            GameState.spawnY = spawn.getY();
            spawn.removeFromWorld();
            System.out.println("Спавн установлен на: (" + GameState.spawnX + ", " + GameState.spawnY + ")");
        } else {
            System.out.println("Спавн не найден, использую (400, 300)");
            GameState.spawnX = 400;
            GameState.spawnY = 300;
        }

        // Создаём игрока
        com.sanctuary.entities.Player.create(GameState.spawnX, GameState.spawnY);
        System.out.println("Игрок создан");
    }

    public double getStartX() { return GameState.spawnX; }
    public double getStartY() { return GameState.spawnY; }
    public String getName() { return mapName; }
}