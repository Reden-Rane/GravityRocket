package fr.insa.gravityrocket.graphics.interfaces;

import fr.insa.gravityrocket.GravityRocket;
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

    private final MainWindow mainWindow;
    private final Image      background;

    private final JLabel      titleLabel;
    private final JScrollPane scrollPane;

    private final TransparentButton closeButton;

    public RulesPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.background = RenderManager.loadImage("/textures/background_rules.jpg", 1000, 707);

        this.setLayout(new GridBagLayout());

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
        this.scrollPane.setBorder(null);

        this.titleLabel = new JLabel("R\u00e8gles Du Jeu ");
        this.titleLabel.setBackground(new Color(170, 40, 255, 55));
        this.titleLabel.setForeground(Color.WHITE);
        this.titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.titleLabel.setBorder(null);
        this.titleLabel.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(60.0f));
        this.titleLabel.setOpaque(true);

        this.closeButton = new TransparentButton("Retour au menu");
        this.closeButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1 / 8.0;
        gbc.insets = new Insets(15, 100, 15, 100);
        this.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 6 / 8.0;
        gbc.insets = new Insets(15, 100, 15, 100);
        this.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1 / 8.0;
        gbc.insets = new Insets(15, 100, 15, 100);
        this.add(closeButton, gbc);
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
            e.printStackTrace();
        }

        return text.toString();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        RenderManager.renderFittedImage(g, background, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.closeButton) {
            this.mainWindow.openHome();
        }
    }

}
