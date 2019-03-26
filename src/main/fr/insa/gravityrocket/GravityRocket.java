package fr.insa.gravityrocket;

import fr.insa.gravityrocket.graphics.GameView;
import fr.insa.gravityrocket.logic.GameModel;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.logic.input.MouseHandler;
import fr.insa.gravityrocket.sound.SoundHelper;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Classe principale du jeu depuis laquelle ce dernier est lancé. Elle contient l'instance de la fenêtre ainsi que la
 * référence au modèle (partie logique du jeu). Cette classe est un singleton, elle n'est instanciée qu'une fois
 */
public class GravityRocket extends Application
{

    public static final String GAME_TITLE = "Gravity Rocket";

    private static final int TICKS_PER_SECOND = 20;

    private static GravityRocket instance;

    private final GameView  gameView;
    private final GameModel gameModel;

    private final KeyboardHandler keyboardHandler;
    private final MouseHandler    mouseHandler;

    private final MediaPlayer musicPlayer;

    private GravityRocket() {
        GravityRocket.instance = this;

        this.keyboardHandler = new KeyboardHandler();
        this.mouseHandler = new MouseHandler();

        this.gameModel = new GameModel();
        this.gameModel.setupDefaultLevel();

        this.gameView = new GameView(this.gameModel, this.keyboardHandler, this.mouseHandler);

        this.musicPlayer = SoundHelper.createPlayer("/sounds/music/rayman_music.wav", true);
        this.musicPlayer.play();

        startGameLoop();

    }

    public static void main(String[] args) {
        new GravityRocket();
    }

    /**
     * Démarre le thread pour mettre à jour le jeu et le rendu graphique
     */
    private void startGameLoop() {

        int interval = 1000 / TICKS_PER_SECOND;

        Timer timer = new Timer(interval, actionEvent -> {
            getGameModel().update(interval / 1000.0);
            getGameView().render();
        });

        timer.start();
    }

    public GameView getGameView() {
        return gameView;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public KeyboardHandler getKeyboardHandler() {
        return keyboardHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public static GravityRocket getInstance() {
        return instance;
    }

    //TODO A déplacer vers une classe plus appropriée
    public MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }

    /**
     * Méthode utile à la construction du contexte JavaFX pour utiliser les MediaPlayer
     */
    @Override
    public void start(Stage stage) throws Exception {}

}
