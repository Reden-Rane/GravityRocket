package fr.insa.gravityrocket.logic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHandler
{

    public static MediaPlayer createPlayer(String filePath, boolean loop) {
        MediaPlayer player = new MediaPlayer(new Media(SoundHandler.class.getResource(filePath).toString()));
        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return player;
    }

}
