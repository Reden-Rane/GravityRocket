package fr.insa.gravityrocket.graphics.interfaces;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.GravityRocketModel;
import fr.insa.gravityrocket.logic.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectionPanel extends JPanel implements ActionListener
{

    private final MainWindow mainWindow;

    private final Image earthImage;
    private final Image background;
    private final int   EARTH_WIDTH  = 587 / 2;
    private final int   EARTH_HEIGHT = 591 / 2;

    private final List<LevelMarkerButton> levelMarkerButtonList = new ArrayList<>();

    public LevelSelectionPanel(MainWindow mainWindow) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1500, 1000));
        addLevelMarkers();

        this.mainWindow = mainWindow;

        this.background = RenderManager.loadImage("/textures/background_0.png", 1920, 1080);
        this.earthImage = RenderManager.loadImage("/textures/star/earth_2.png", EARTH_WIDTH, EARTH_HEIGHT);

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                super.componentResized(componentEvent);
                updateStarsPositions();
            }
        });

        updateStarsPositions();
    }

    private void addLevelMarkers() {
        addLevelMarker(1, GravityRocketModel.LEVEL_1, -200, -300);
        addLevelMarker(2, GravityRocketModel.LEVEL_2, -350, -150);
        addLevelMarker(3, GravityRocketModel.LEVEL_3, -350, 150);
        addLevelMarker(4, GravityRocketModel.LEVEL_4, -200, 300);

        addLevelMarker(8, GravityRocketModel.LEVEL_1, 180, -300);
        addLevelMarker(7, GravityRocketModel.LEVEL_1, 330, -150);
        addLevelMarker(6, GravityRocketModel.LEVEL_1, 330, 150);
        addLevelMarker(5, GravityRocketModel.LEVEL_1, 180, 300);
    }

    private void updateStarsPositions() {
        for (LevelMarkerButton markerButton : levelMarkerButtonList) {
            int x = getWidth() / 2 + markerButton.getRelativeX();
            int y = getHeight() / 2 + markerButton.getRelativeY();
            markerButton.setLocation(x, y);
        }
    }

    private void addLevelMarker(int levelNumber, Level level, int relX, int relY) {
        LevelMarkerButton levelMarkerButton = new LevelMarkerButton(levelNumber, level, relX, relY);
        levelMarkerButton.addActionListener(this);
        this.levelMarkerButtonList.add(levelMarkerButton);
        this.add(levelMarkerButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        RenderManager.renderFittedImage(g, background, getWidth(), getHeight());

        int x = (getWidth() - EARTH_WIDTH) / 2;
        int y = (getHeight() - EARTH_HEIGHT) / 2;
        g.drawImage(this.earthImage, x, y, EARTH_WIDTH, EARTH_HEIGHT, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof LevelMarkerButton) {
            GravityRocket.getInstance().getGravityRocketModel().startLevel(((LevelMarkerButton) e.getSource()).getLevel());
            this.mainWindow.openLevel();
        }
    }

}
