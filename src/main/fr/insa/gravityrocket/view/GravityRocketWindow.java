package fr.insa.gravityrocket.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GravityRocketWindow extends JFrame implements KeyListener
{
    private final LevelPanel levelPanel;

    public GravityRocketWindow()
    {
        super("GravityRocket");
        this.setSize(new Dimension(1500, 1000));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.levelPanel = new LevelPanel();
        this.setContentPane(levelPanel);

        this.addKeyListener(this);

        this.setVisible(true);
    }

    public LevelPanel getLevelPanel()
    {
        return levelPanel;
    }


    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        getLevelPanel().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        getLevelPanel().keyReleased(e);
    }
}
