package fr.insa.gravityrocket.graphics.main.rules;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.main.TransparentButton;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RulesPanel extends JPanel implements ActionListener
{

    private final Image background;

    private final RulesWindow rulesWindow;

    private final JLabel      titleLabel;
    private final JScrollPane scrollPane;

    private final TransparentButton closeButton;

    public RulesPanel(RulesWindow rulesWindow) {
        this.rulesWindow = rulesWindow;
        this.background = RenderManager.loadImage("/textures/background_rules.jpg", 1000, 707);

        this.setLayout(null);

        JTextPane rulesTextArea = new JTextPane();
        rulesTextArea.setBackground(new Color(0, 0, 0, 0));
        rulesTextArea.setText(loadText());
        rulesTextArea.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(25.0f));
        rulesTextArea.setForeground(Color.WHITE);
        rulesTextArea.setOpaque(false);
        rulesTextArea.setBorder(null);

        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
        rulesTextArea.setParagraphAttributes(attribs, false);

        this.scrollPane = new JScrollPane(rulesTextArea);
        this.scrollPane.setOpaque(false);
        this.scrollPane.getViewport().setOpaque(true);
        this.scrollPane.getViewport().setBackground(new Color(0x222342));
        this.scrollPane.setBounds(50, 200, 900, 350);
        this.scrollPane.setBorder(null);

        this.titleLabel = new JLabel("R\u00e8gles Du Jeu ");
        this.titleLabel.setBackground(new Color(170, 40, 255, 55));
        this.titleLabel.setBounds(240, 40, 450, 100);
        this.titleLabel.setForeground(Color.WHITE);
        this.titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.titleLabel.setBorder(null);
        this.titleLabel.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(60.0f));
        this.titleLabel.setOpaque(true);

        this.closeButton = new TransparentButton("Fermer");
        this.closeButton.setBounds(390, 592, 220, 50);
        this.closeButton.addActionListener(this);

        this.add(titleLabel);
        this.add(scrollPane);
        this.add(closeButton);
    }

    private String loadText() {

        StringBuilder text = new StringBuilder();

        try {
            InputStream       stream = new FileInputStream(GravityRocket.class.getResource("/rules.txt").getFile());
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader    buff   = new BufferedReader(reader);
            String            line;
            while ((line = buff.readLine()) != null) {
                text.append(line).append('\n');
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return text.toString();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double ratio = background.getHeight(null) / (double) background.getWidth(null);

        int width  = getWidth();
        int height = (int) (width * ratio);

        g.setColor(new Color(31, 7, 54));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(background, (getWidth() - width) / 2, (getHeight() - height) / 2, width, height, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.closeButton) {
            this.rulesWindow.setVisible(false);
        }
    }

}
