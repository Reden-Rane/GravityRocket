package fr.insa.gravityrocket.logic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Gestionnaire des sons du jeu contenant l'ensemble des players.
 */
public class SoundHandler
{

    public final MediaPlayer   menuMusicPlayer;
    public final MediaPlayer[] musicPlayers = new MediaPlayer[8];

    public final MediaPlayer boosterSoundPlayer;
    public final MediaPlayer explosionSoundPlayer;
    public final MediaPlayer dangerSoundPlayer;
    public final MediaPlayer successSoundPlayer;
    public final MediaPlayer shootingSoundPlayer;
    public final MediaPlayer alienSpeechSoundPlayer;

    public SoundHandler() {
        this.menuMusicPlayer = createPlayer("/sounds/music/faster_than_light.wav", true);

        this.musicPlayers[0] = createPlayer("/sounds/music/kerbal_space_program.wav", true);
        this.musicPlayers[1] = createPlayer("/sounds/music/interstellar_0.wav", true);
        this.musicPlayers[2] = createPlayer("/sounds/music/guardians_of_the_galaxy.wav", true);
        this.musicPlayers[3] = createPlayer("/sounds/music/star_wars.wav", true);
        this.musicPlayers[4] = createPlayer("/sounds/music/mass_effect.wav", true);
        this.musicPlayers[5] = createPlayer("/sounds/music/batman.wav", true);
        this.musicPlayers[6] = createPlayer("/sounds/music/the_martian.wav", true);
        this.musicPlayers[7] = createPlayer("/sounds/music/interstellar_1.wav", true);

        this.boosterSoundPlayer = createPlayer("/sounds/rocket_booster.wav", true);
        this.explosionSoundPlayer = createPlayer("/sounds/explosion.wav", false);
        this.dangerSoundPlayer = createPlayer("/sounds/alarm.wav", true);
        this.dangerSoundPlayer.setVolume(0.5);
        this.successSoundPlayer = createPlayer("/sounds/success.wav", false);

        this.shootingSoundPlayer = createPlayer("/sounds/pew_pew.wav", false);
        this.alienSpeechSoundPlayer = createPlayer("/sounds/alien_speech.wav", false);
        this.alienSpeechSoundPlayer.setVolume(0.5);
    }

    /**
     * Arrête tous les sons du jeu
     */
    public void stopAllSounds() {
        dangerSoundPlayer.stop();
        successSoundPlayer.stop();
        explosionSoundPlayer.stop();
        boosterSoundPlayer.stop();
        alienSpeechSoundPlayer.stop();
        shootingSoundPlayer.stop();
    }

    /**
     * Crée un player pour le son donné
     *
     * @param filePath Le son à jouer
     * @param loop     Vrai si le son doit être joué en boucle
     *
     * @return L'instance du player
     */
    public MediaPlayer createPlayer(String filePath, boolean loop) {
        MediaPlayer player = new MediaPlayer(new Media(SoundHandler.class.getResource(filePath).toString()));
        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return player;
    }

}
