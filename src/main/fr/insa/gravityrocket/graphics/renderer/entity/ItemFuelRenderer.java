package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;

import java.awt.*;

public class ItemFuelRenderer implements IRenderer<ItemFuel>
{

    @Override
    public void render(ItemFuel fuel, Graphics2D g2d) {
        GradientPaint p = new GradientPaint(0, 0, new Color(0, 223, 95), 0, (int) fuel.getHeight(), new Color(51, 146, 44));
        g2d.setPaint(p);
        g2d.fillOval(0, 0, (int) fuel.getWidth(), (int) fuel.getHeight());
    }

}
