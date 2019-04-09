package fr.insa.gravityrocket.graphics.main.selection;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.GravityRocketModel;

import javax.swing.*;
import java.awt.*;

public class LevelSelectionPanel extends JPanel
{

    private final Image earthImage;
    private final int   EARTH_WIDTH  = 587 / 2;
    private final int   EARTH_HEIGHT = 591 / 2;

    public LevelSelectionPanel() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(null);
        addLevelMarkers();

        this.earthImage = RenderManager.loadImage("/textures/star/earth_2.png", EARTH_WIDTH, EARTH_HEIGHT);
    }

    private void addLevelMarkers() {
        LevelMarkerButton levelMarkerButton1 = new LevelMarkerButton(GravityRocketModel.level1);
        levelMarkerButton1.setBounds(0, 0, 50, 50);
        this.add(levelMarkerButton1);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int x = (getWidth() - EARTH_WIDTH) / 2;
        int y = (getHeight() - EARTH_HEIGHT) / 2;
        graphics.drawImage(this.earthImage, x, y, EARTH_WIDTH, EARTH_HEIGHT, null);
    }

}
