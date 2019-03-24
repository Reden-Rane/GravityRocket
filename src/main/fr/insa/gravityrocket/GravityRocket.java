package fr.insa.gravityrocket;

import fr.insa.gravityrocket.graphics.GameWindow;
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

    public static final int WINDOW_WIDTH  = 1500;
    public static final int WINDOW_HEIGHT = 1000;

    private static final String GAME_TITLE       = "Gravity Rocket";
    private static final int    TICKS_PER_SECOND = 20;

    private static GravityRocket instance;

    private final GameWindow gameWindow;
    private final GameModel  gameModel;

    private final KeyboardHandler keyboardHandler;
    private final MouseHandler    mouseHandler;

    private final MediaPlayer musicPlayer;

    private GravityRocket() {
        GravityRocket.instance = this;

        this.keyboardHandler = new KeyboardHandler();
        this.mouseHandler = new MouseHandler();

        this.gameWindow = new GameWindow(GAME_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, this.keyboardHandler, this.mouseHandler);

        this.gameModel = new GameModel();
        this.gameModel.setupDefaultLevel();

        this.musicPlayer = SoundHelper.createPlayer("/sounds/music/music01.wav", true);
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
            getGameWindow().render();
        });

        timer.start();
    }

    public GameWindow getGameWindow() {
        return gameWindow;
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

    @Override
    public void start(Stage stage) throws Exception {}

    public MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }

}
