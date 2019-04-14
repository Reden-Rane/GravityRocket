package fr.insa.gravityrocket;

import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.controller.MouseHandler;
import fr.insa.gravityrocket.graphics.GravityRocketView;
import fr.insa.gravityrocket.logic.GravityRocketModel;
import fr.insa.gravityrocket.logic.SoundHandler;
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

    private static final int UPDATES_PER_SECOND = 60;

    private static GravityRocket instance;

    private final GravityRocketView  gravityRocketView;
    private final GravityRocketModel gravityRocketModel;

    private final KeyboardHandler keyboardHandler;
    private final MouseHandler    mouseHandler;

    private final MediaPlayer musicPlayer;

    private GravityRocket() {
        GravityRocket.instance = this;

        this.keyboardHandler = new KeyboardHandler();
        this.mouseHandler = new MouseHandler();

        this.gravityRocketModel = new GravityRocketModel();
        this.gravityRocketView = new GravityRocketView(this.gravityRocketModel, this.keyboardHandler, this.mouseHandler);

        this.musicPlayer = SoundHandler.createPlayer("/sounds/music/music01.wav", true);
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

        int interval = 1000 / UPDATES_PER_SECOND;

        Timer timer = new Timer(interval, actionEvent -> {
            getGravityRocketModel().update(interval / 1000.0);
            getGravityRocketView().render();
        });

        timer.start();
    }

    public GravityRocketModel getGravityRocketModel() {
        return gravityRocketModel;
    }

    public GravityRocketView getGravityRocketView() {
        return gravityRocketView;
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
     * Méthode utile à la construction du contexte JavaFX pour utiliser les MediaPlayer, bien plus fluides pour jouer
     * des sons que sous javax
     */
    @Override
    public void start(Stage stage) throws Exception {}

}
