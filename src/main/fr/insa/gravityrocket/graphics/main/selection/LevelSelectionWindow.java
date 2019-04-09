package fr.insa.gravityrocket.graphics.main.selection;

import javax.swing.*;

public class LevelSelectionWindow extends JFrame
{

    private final LevelSelectionPanel selectionPanel;

    public LevelSelectionWindow() {
        super("Gravity Rocket - Sélection du niveau");

        this.selectionPanel = new LevelSelectionPanel();
        this.add(selectionPanel);

        this.pack();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

}
