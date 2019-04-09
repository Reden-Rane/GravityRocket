package fr.insa.gravityrocket.graphics.main.rules;

import javax.swing.*;
import java.awt.*;

public class RulesWindow extends JFrame
{

    private final RulesPanel rulesPanel;

    public RulesWindow() {
        super("Gravity Rocket - Accueil");
        this.setSize(new Dimension(1000, 707));

        this.rulesPanel = new RulesPanel(this);
        this.setContentPane(this.rulesPanel);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

}
