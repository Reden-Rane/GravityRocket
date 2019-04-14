package fr.insa.gravityrocket.graphics.interfaces;

import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.controller.MouseHandler;
import fr.insa.gravityrocket.graphics.GamePanel;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{

    private final HomePanel           homePanel;
    private final RulesPanel          rulesPanel;
    private final LevelSelectionPanel levelSelectionPanel;
    private final GamePanel           gamePanel;

    public MainWindow(RenderManager renderManager, int width, int height, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        super("Gravity Rocket - Accueil");

        this.homePanel = new HomePanel(this);
        this.rulesPanel = new RulesPanel(this);
        this.levelSelectionPanel = new LevelSelectionPanel(this);
        this.gamePanel = new GamePanel(this, renderManager, keyboardHandler, mouseHandler);

        this.homePanel.setPreferredSize(new Dimension(width, height));
        this.rulesPanel.setPreferredSize(new Dimension(width, height));
        this.levelSelectionPanel.setPreferredSize(new Dimension(width, height));
        this.gamePanel.setPreferredSize(new Dimension(width, height));

        this.setContentPane(this.homePanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void openHome() {
        this.openPanel(this.homePanel);
    }

    private void openPanel(JPanel panel) {
        this.setContentPane(panel);
        this.revalidate();
        this.repaint();
    }

    public void openRules() {
        this.openPanel(this.rulesPanel);
    }

    public void openLevelSelection() {
        this.openPanel(this.levelSelectionPanel);
    }

    public void openLevel() {
        this.openPanel(this.gamePanel);
        this.gamePanel.requestFocus();
    }

}
