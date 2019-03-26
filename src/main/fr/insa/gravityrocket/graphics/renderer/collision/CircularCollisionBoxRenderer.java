package fr.insa.gravityrocket.graphics.renderer.collision;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;

import java.awt.*;

public class CircularCollisionBoxRenderer implements IRenderer<CircularCollisionBox>
{

    @Override
    public void render(CircularCollisionBox c, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GREEN);
        g2d.drawOval((int) (c.getCenterX() - c.getRadius()), (int) (c.getCenterY() - c.getRadius()), (int) (c.getRadius() * 2), (int) (c.getRadius() * 2));
    }

}
