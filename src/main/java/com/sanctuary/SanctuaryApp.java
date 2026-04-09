package com.sanctuary;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.sanctuary.core.GameState;
import com.sanctuary.entities.EntityType;
import com.sanctuary.entities.MovementComponent;
import com.sanctuary.entities.Player;
import com.sanctuary.maps.GameMap;
import com.sanctuary.ui.SanctuaryMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SanctuaryApp extends GameApplication {

    private GameMap currentMap;
    private Entity player;

    private Map<KeyCode, Boolean> keys = new HashMap<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Sanctuary: The New Beginning");
        settings.setVersion("0.2.0");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new SanctuaryMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        System.out.println("=== НАЧАЛО initGame ===");

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        // Загружаем карту (она сама создаст игрока)
        currentMap = new GameMap("level1");
        currentMap.load();

        // Получаем игрока
        player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);

        if (player == null) {
            System.err.println("ОШИБКА: Игрок не создан!");
            return;
        }

        // Привязываем камеру к игроку
        FXGL.getGameScene().getViewport().bindToEntity(player,
                FXGL.getAppWidth() / 2,
                FXGL.getAppHeight() / 2);

        System.out.println("=== КОНЕЦ initGame ===");
    }

    @Override
    protected void initInput() {
        keys.put(KeyCode.LEFT, false);
        keys.put(KeyCode.RIGHT, false);
        keys.put(KeyCode.UP, false);
        keys.put(KeyCode.DOWN, false);
        keys.put(KeyCode.A, false);
        keys.put(KeyCode.D, false);
        keys.put(KeyCode.W, false);
        keys.put(KeyCode.S, false);

        FXGL.getInput().addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            if (keys.containsKey(code)) {
                keys.put(code, true);
                updateMovement();
            }
        });

        FXGL.getInput().addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, event -> {
            KeyCode code = event.getCode();
            if (keys.containsKey(code)) {
                keys.put(code, false);
                updateMovement();
            }
        });
    }

    private void updateMovement() {
        // Получаем игрока заново каждый раз
        Entity p = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
        if (p == null) {
            System.out.println("Игрок не найден в updateMovement");
            return;
        }

        MovementComponent move = p.getComponent(MovementComponent.class);
        if (move == null) {
            System.out.println("MovementComponent не найден");
            return;
        }

        double dx = 0, dy = 0;

        if (keys.get(KeyCode.LEFT) || keys.get(KeyCode.A)) dx = -1;
        if (keys.get(KeyCode.RIGHT) || keys.get(KeyCode.D)) dx = 1;
        if (keys.get(KeyCode.UP) || keys.get(KeyCode.W)) dy = -1;
        if (keys.get(KeyCode.DOWN) || keys.get(KeyCode.S)) dy = 1;

        System.out.println("Движение: dx=" + dx + ", dy=" + dy);  // Для отладки

        move.setMoveX(dx);
        move.setMoveY(dy);

        if (dx != 0 || dy != 0) {
            move.cancelMoveToTarget();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}