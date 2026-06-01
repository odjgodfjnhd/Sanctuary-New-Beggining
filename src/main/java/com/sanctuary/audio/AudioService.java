package com.sanctuary.audio;

import com.sanctuary.config.AudioConfig;
import com.sanctuary.core.GameService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

public class AudioService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(AudioService.class.getName());

    private Optional<MediaPlayer> backgroundMusicPlayer = Optional.empty();
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
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(musicVolume);

        mediaPlayer.setOnReady(() ->
                LOGGER.info(() -> "Background music started: " + track.getResourcePath())
        );

        mediaPlayer.setOnError(() ->
                LOGGER.warning(() -> "Background music error: " + mediaPlayer.getError())
        );

        backgroundMusicPlayer = Optional.of(mediaPlayer);
        mediaPlayer.play();
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer.isEmpty()) {
            return;
        }

        backgroundMusicPlayer.ifPresent(mediaPlayer -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        });

        backgroundMusicPlayer = Optional.empty();

        LOGGER.info("Background music stopped");
    }

    public void setMusicVolume(double volume) {
        musicVolume = clampMusicVolume(volume);

        backgroundMusicPlayer.ifPresent(mediaPlayer ->
                mediaPlayer.setVolume(musicVolume)
        );
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public boolean isBackgroundMusicPlaying() {
        return backgroundMusicPlayer
                .map(mediaPlayer -> mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
                .orElse(false);
    }

    private double clampMusicVolume(double volume) {
        return Math.max(
                AudioConfig.MIN_MUSIC_VOLUME,
                Math.min(AudioConfig.MAX_MUSIC_VOLUME, volume)
        );
    }
}
