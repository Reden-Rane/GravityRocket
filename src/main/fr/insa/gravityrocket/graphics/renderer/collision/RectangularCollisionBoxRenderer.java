package fr.insa.gravityrocket.graphics.renderer.collision;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.logic.collision.RectangularCollisionBox;

import java.awt.*;

public class RectangularCollisionBoxRenderer implements IRenderer<RectangularCollisionBox>
{

    @Override
    public void render(RectangularCollisionBox r, Graphics2D g2d) {
        double[][] points = r.getPoints();
        int[]      xArr   = new int[4];
        int[]      yArr   = new int[4];

        for (int i = 0; i < points.length; i++) {
            xArr[i] = (int) points[i][0];
            yArr[i] = (int) points[i][1];
        }

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.RED);
        g2d.drawPolygon(xArr, yArr, 4);
    }

}
