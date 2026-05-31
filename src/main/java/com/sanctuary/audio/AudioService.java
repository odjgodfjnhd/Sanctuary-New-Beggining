package com.sanctuary.audio;

import com.sanctuary.config.AudioConfig;
import com.sanctuary.core.GameService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.logging.Logger;

public class AudioService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(AudioService.class.getName());

    private MediaPlayer backgroundMusicPlayer;
    private double musicVolume = AudioConfig.DEFAULT_MUSIC_VOLUME;

    @Override
    public void initialize() {
        if (AudioConfig.BACKGROUND_MUSIC_ENABLED) {
            playBackgroundMusic(MusicTrack.MAIN_THEME);
        }
    }

    @Override
    public void dispose() {
        stopBackgroundMusic();
    }

    public void playBackgroundMusic(MusicTrack track) {
        stopBackgroundMusic();

        URL musicResource = getClass().getResource(track.getResourcePath());

        if (musicResource == null) {
            LOGGER.warning(() -> "Background music resource not found: " + track.getResourcePath());
            return;
        }

        Media media = new Media(musicResource.toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(media);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.setVolume(musicVolume);

        backgroundMusicPlayer.setOnReady(() ->
                LOGGER.info(() -> "Background music started: " + track.getResourcePath())
        );

        backgroundMusicPlayer.setOnError(() ->
                LOGGER.warning(() -> "Background music error: "
                        + backgroundMusicPlayer.getError())
        );

        backgroundMusicPlayer.play();
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            return;
        }

        backgroundMusicPlayer.stop();
        backgroundMusicPlayer.dispose();
        backgroundMusicPlayer = null;

        LOGGER.info("Background music stopped");
    }

    public void setMusicVolume(double volume) {
        musicVolume = clamp(volume, 0.0, 1.0);

        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public boolean isBackgroundMusicPlaying() {
        return backgroundMusicPlayer != null
                && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}