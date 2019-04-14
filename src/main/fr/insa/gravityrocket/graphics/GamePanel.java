package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.controller.MouseHandler;
import fr.insa.gravityrocket.graphics.interfaces.MainWindow;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener
{

    private final MainWindow mainWindow;

    private final RenderManager renderManager;

    public GamePanel(MainWindow mainWindow, RenderManager renderManager, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        this.mainWindow = mainWindow;
        this.renderManager = renderManager;
        this.setIgnoreRepaint(true);

        this.addKeyListener(this);
        this.addKeyListener(keyboardHandler);
        this.addMouseListener(mouseHandler);

        this.setFocusable(true);
        this.setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Level level = GravityRocket.getInstance().getGravityRocketModel().getCurrentLevel();

        Graphics2D g2d = (Graphics2D) g;
        //On efface l'écran
        g2d.clearRect(0, 0, getWidth(), getHeight());

        setupRenderHints(g2d);
        this.renderManager.render(level, g2d);
    }

    private void setupRenderHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //On quitte le niveau
            GravityRocket.getInstance().getGravityRocketModel().getCurrentLevel().stopAllSounds();
            this.mainWindow.openLevelSelection();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_R) {
            //On redémarre le niveau
            GravityRocket.getInstance().getGravityRocketModel().getCurrentLevel().resetLevel();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

}
