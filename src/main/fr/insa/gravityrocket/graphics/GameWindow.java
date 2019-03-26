package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.logic.input.MouseHandler;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{

    /**
     * Le canvas, composant dans lequel on fait le rendu du niveau
     */
    private final GameCanvas gameCanvas;

    public GameWindow(RenderManager renderManager, String title, int width, int height, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        super(title);
        this.gameCanvas = new GameCanvas(renderManager);
        this.gameCanvas.setPreferredSize(new Dimension(width, height));
        this.gameCanvas.setIgnoreRepaint(true);
        this.add(this.gameCanvas);

        this.gameCanvas.addKeyListener(keyboardHandler);
        this.gameCanvas.addMouseListener(mouseHandler);
        this.gameCanvas.setFocusable(true);
        this.gameCanvas.requestFocus();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void render() {
        this.gameCanvas.render(GravityRocket.getInstance().getGameModel().getCurrentLevel());
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

}
