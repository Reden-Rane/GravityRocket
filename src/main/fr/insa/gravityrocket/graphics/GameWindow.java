package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.logic.input.MouseHandler;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{

    /**
     * Le canvas, composant dans lequel on fait le rendu du niveau
     */
    private final LevelCanvas levelCanvas;

    public GameWindow(String title, int width, int height, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.levelCanvas = new LevelCanvas();
        this.levelCanvas.setPreferredSize(new Dimension(width, height));
        this.levelCanvas.setIgnoreRepaint(true);
        this.add(this.levelCanvas);

        this.levelCanvas.addKeyListener(keyboardHandler);
        this.levelCanvas.addMouseListener(mouseHandler);
        this.levelCanvas.setFocusable(true);
        this.levelCanvas.requestFocus();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void render() {
        this.levelCanvas.render(GravityRocket.getInstance().getGameModel().getCurrentLevel());
    }

    public LevelCanvas getLevelCanvas() {
        return levelCanvas;
    }

}
