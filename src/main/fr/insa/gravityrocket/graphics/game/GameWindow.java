package fr.insa.gravityrocket.graphics.game;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.controller.MouseHandler;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{

    /**
     * Le canvas, composant dans lequel on fait le rendu du niveau
     */
    private final GameCanvas gameCanvas;

    public GameWindow(RenderManager renderManager, int width, int height, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        super("Gravity Rocket - Jeu");
        this.gameCanvas = new GameCanvas(renderManager);
        this.gameCanvas.setPreferredSize(new Dimension(width, height));
        this.gameCanvas.setIgnoreRepaint(true);
        this.add(this.gameCanvas);

        this.gameCanvas.addKeyListener(keyboardHandler);
        this.gameCanvas.addMouseListener(mouseHandler);
        this.gameCanvas.setFocusable(true);
        this.gameCanvas.requestFocus();

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void render() {
        this.gameCanvas.render(GravityRocket.getInstance().getGravityRocketModel().getCurrentLevel());
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (!visible) {
            GravityRocket.getInstance().getGravityRocketModel().setPaused(true);
        }
    }

}
