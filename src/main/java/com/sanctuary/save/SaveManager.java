package com.sanctuary.save;

import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.entities.EntityType;

import java.io.*;

public class SaveManager {

    private static final String SAVE_FILE = "sanctuary_save.dat";

    public static void saveGame() {
        var player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
        if (player == null) return;

        // Просто сохраняем имя карты как строку, без getWorldProperty
        SaveData data = new SaveData(
                "start_map", // временно фиксированное имя
                player.getX(),
                player.getY()
        );

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(data);
            System.out.println("Игра сохранена!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (SaveData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Нет сохранённой игры");
            return null;
        }
    }
}