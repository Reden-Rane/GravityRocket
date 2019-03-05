package fr.insa.gravityrocket.view;

import fr.insa.gravityrocket.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelPanel extends JPanel
{
    private static final Color SPACE_COLOR = new Color(14, 21, 33);

    private Level level;

    public LevelPanel()
    {
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setColor(SPACE_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (getLevel() != null)
        {
            getLevel().render((Graphics2D) g);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        getLevel().getRocket().keyPressed(e);
    }

    public void keyReleased(KeyEvent e)
    {
        getLevel().getRocket().keyReleased(e);
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

}
