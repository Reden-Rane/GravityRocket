package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;

import java.awt.*;

public class ItemFuelRenderer implements IRenderer<ItemFuel>
{

    private final Image fuelImage;

    public ItemFuelRenderer() {
        this.fuelImage = RenderManager.loadImage("/textures/entity/fuel.png", 43, 50);
    }

    @Override
    public void render(ItemFuel fuel, Graphics2D g2d) {
        g2d.drawImage(fuelImage, 0, 0, (int) (fuel.getWidth() * 0.86), (int) fuel.getHeight(), null);
    }

}
