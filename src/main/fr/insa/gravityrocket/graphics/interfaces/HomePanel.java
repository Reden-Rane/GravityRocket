package fr.insa.gravityrocket.graphics.interfaces;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public class HomePanel extends JPanel implements ActionListener
{

    private final Image background;

    private final MainWindow mainWindow;

    private final TransparentButton playButton;
    private final TransparentButton rulesButton;
    private final TransparentButton exitButton;

    public HomePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.background = RenderManager.loadImage("/textures/background_main.png", 1016, 618);

        this.playButton = new TransparentButton("Jouer");
        this.playButton.setPreferredSize(new Dimension(500, 50));
        this.playButton.addActionListener(this);

        this.rulesButton = new TransparentButton("Règles");
        this.rulesButton.setPreferredSize(new Dimension(500, 50));
        this.rulesButton.addActionListener(this);

        this.exitButton = new TransparentButton("Quitter");
        this.exitButton.setPreferredSize(new Dimension(500, 50));
        this.exitButton.addActionListener(this);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1 / 3.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets.top = 250;
        this.add(this.playButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1 / 3.0;
        gbc.insets.top = 50;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(this.rulesButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1 / 3.0;
        gbc.insets.bottom = 150;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(this.exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        RenderManager.renderFittedImage(g, background, getWidth(), getHeight());
        renderTitle(g);
    }

    private void renderTitle(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        String title = "Gravity Rocket";

        Font        font       = RenderManager.BEBAS_NEUE_FONT.deriveFont(100.0f);
        Rectangle2D textBounds = font.getStringBounds(title, g2d.getFontRenderContext());

        int renderX = (int) ((getWidth() - textBounds.getWidth()) / 2);
        int renderY = 150;

        g.setFont(font);

        GlyphVector glyphVector = font.createGlyphVector(g2d.getFontRenderContext(), title);
        Shape       outline     = glyphVector.getOutline();

        g2d.setStroke(new BasicStroke(5.0f));
        g2d.setColor(new Color(112, 112, 112));
        g2d.translate(renderX, renderY);
        g2d.draw(outline);
        g2d.translate(-renderX, -renderY);

        Color startColor2 = new Color(0xFF812B);
        Color endColor2   = new Color(0xFED96D);

        GradientPaint gradient2 = new GradientPaint(renderX, renderY, startColor2, renderX, renderY + (int) textBounds.getHeight(), endColor2, true);
        g2d.setPaint(gradient2);
        g.drawString(title, renderX, renderY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.playButton) {
            this.mainWindow.openLevelSelection();
        } else if (e.getSource() == this.rulesButton) {
            this.mainWindow.openRules();
        } else if (e.getSource() == this.exitButton) {
            System.exit(0);
        }
    }

}
