package fr.insa.gravityrocket.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHelper
{

    public static MediaPlayer createPlayer(String filePath, boolean loop) {
        MediaPlayer player = new MediaPlayer(new Media(SoundHelper.class.getResource(filePath).toString()));
        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return player;
    }

}
