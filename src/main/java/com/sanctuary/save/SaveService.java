package com.sanctuary.save;

import com.almasb.fxgl.entity.Entity;
import com.sanctuary.core.GameService;
import com.sanctuary.game.GameSession;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

public class SaveService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(SaveService.class.getName());

    private final GameSession session;
    private final GameSaveRepository gameSaveRepository;

    public SaveService(GameSession session, GameSaveRepository gameSaveRepository) {
        this.session = session;
        this.gameSaveRepository = gameSaveRepository;
    }

    public void saveCurrentGame() {
        Entity player = session.getPlayer();

        if (player == null || !player.isActive()) {
            throw new IllegalStateException("Cannot save game: player is not active");
        }

        String currentMapId = session.getCurrentMapId();

        if (currentMapId == null || currentMapId.isBlank()) {
            throw new IllegalStateException("Cannot save game: current map id is not set");
        }

        GameSaveData saveData = new GameSaveData(
                currentMapId,
                player.getX(),
                player.getY(),
                LocalDateTime.now()
        );

        gameSaveRepository.save(saveData);

        LOGGER.info(() -> "Current game saved at map: " + currentMapId);
    }

    public boolean hasSave() {
        return gameSaveRepository.exists();
    }

    public Optional<GameSaveData> loadSave() {
        return gameSaveRepository.load();
    }

    public void prepareSessionForSavedGame() {
        GameSaveData saveData = loadSave()
                .orElseThrow(() -> new IllegalStateException("No game save found"));

        session.prepareSavedGame(saveData);
    }
}