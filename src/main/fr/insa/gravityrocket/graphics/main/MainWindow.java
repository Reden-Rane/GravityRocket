package fr.insa.gravityrocket.graphics.main;

import fr.insa.gravityrocket.graphics.main.rules.RulesWindow;
import fr.insa.gravityrocket.graphics.main.selection.LevelSelectionWindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{

    private final MainPanel            mainPanel;
    private final RulesWindow          rulesWindow;
    private final LevelSelectionWindow levelSelectionWindow;

    public MainWindow(int width, int height) {
        super("Gravity Rocket - Accueil");

        this.mainPanel = new MainPanel(this);
        this.mainPanel.setPreferredSize(new Dimension(width, height));

        this.rulesWindow = new RulesWindow();

        this.levelSelectionWindow = new LevelSelectionWindow();

        this.setContentPane(this.mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void openRulesWindow() {
        this.rulesWindow.setVisible(true);
    }

    public void openLevelSelectionWindow() {
        this.levelSelectionWindow.setVisible(true);
    }


}
