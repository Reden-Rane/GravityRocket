package fr.insa.gravityrocket;

import fr.insa.gravityrocket.graphics.GameWindow;
import fr.insa.gravityrocket.input.KeyboardHandler;
import fr.insa.gravityrocket.input.MouseHandler;

import javax.swing.*;

/**
 * Classe principale du jeu depuis laquelle ce dernier est lancé. Elle contient l'instance de la fenêtre ainsi que la
 * référence au modèle (partie logique du jeu). Cette classe est un singleton, elle n'est instanciée qu'une fois
 */
public class GravityRocket
{
    private static final String GAME_TITLE       = "Gravity Rocket";
    private static final int    TICKS_PER_SECOND = 20;

    private static GravityRocket instance;

    private final GameWindow gameWindow;
    private final GameModel  gameModel;

    private final KeyboardHandler keyboardHandler;
    private final MouseHandler    mouseHandler;

    private GravityRocket() {
        this.keyboardHandler = new KeyboardHandler();
        this.mouseHandler = new MouseHandler();

        this.gameWindow = new GameWindow(GAME_TITLE, 1500, 1000, this.keyboardHandler, this.mouseHandler);

        this.gameModel = new GameModel();
        this.gameModel.setupDefaultLevel();

        startGameLoop();
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

    public void setFPS(int fps) {
        this.gameWindow.setTitle(String.format("%s | %d fps", GAME_TITLE, fps));
    }

    public static GravityRocket getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        GravityRocket.instance = new GravityRocket();
    }

}
