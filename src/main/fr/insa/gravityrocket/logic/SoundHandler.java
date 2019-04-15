package fr.insa.gravityrocket.logic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHandler
{

    public final MediaPlayer musicSoundPlayer;
    public final MediaPlayer boosterSoundPlayer;
    public final MediaPlayer explosionSoundPlayer;
    public final MediaPlayer dangerSoundPlayer;
    public final MediaPlayer successSoundPlayer;
    public final MediaPlayer shootingSoundPlayer;
    public final MediaPlayer alienSpeechSoundPlayer;

    public SoundHandler() {
        this.musicSoundPlayer = createPlayer("/sounds/music/music01.wav", true);
        this.boosterSoundPlayer = createPlayer("/sounds/rocket_booster.wav", true);
        this.explosionSoundPlayer = createPlayer("/sounds/explosion.wav", false);
        this.dangerSoundPlayer = createPlayer("/sounds/alarm.wav", true);
        this.dangerSoundPlayer.setVolume(0.5);
        this.successSoundPlayer = createPlayer("/sounds/success.wav", false);

        this.shootingSoundPlayer = createPlayer("/sounds/pew_pew.wav", false);
        this.alienSpeechSoundPlayer = createPlayer("/sounds/alien_speech.wav", false);
        this.alienSpeechSoundPlayer.setVolume(0.5);
    }

    public MediaPlayer createPlayer(String filePath, boolean loop) {
        MediaPlayer player = new MediaPlayer(new Media(SoundHandler.class.getResource(filePath).toString()));
        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return player;
    }

}
