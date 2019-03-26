package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.logic.entity.Planet;

import java.awt.*;

public class PlanetRenderer implements IRenderer<Planet>
{

    @Override
    public void render(Planet planet, Graphics2D g2d) {
        g2d.drawImage(planet.getTexture(), 0, 0, (int) planet.getWidth(), (int) planet.getHeight(), null);
    }

}
