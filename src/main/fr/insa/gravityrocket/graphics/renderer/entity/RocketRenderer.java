package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.ImageHelper;
import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RocketRenderer implements IRenderer<Rocket>
{

    private final Image rocketImage;
    private final Image flameImage;
    private final Image gasImage;

    public RocketRenderer() {
        this.rocketImage = ImageHelper.loadImage("/textures/entity/rocket/rocket.png", 15, 36);
        this.flameImage = ImageHelper.loadImage("/textures/entity/rocket/flame.png", 10, 24);
        this.gasImage = ImageHelper.loadImage("/textures/entity/rocket/gas.png", 6, 14);
    }

    @Override
    public void render(Rocket rocket, Graphics2D g2d) {

        renderGasThrusters(rocket, g2d);

        if (rocket.getBoosterReactor().isActive()) {
            g2d.drawImage(flameImage, (int) (rocket.getWidth() * 0.17), (int) (rocket.getHeight() * 0.88), (int) (rocket.getWidth() * 2 / 3.0), (int) (rocket.getHeight() * 2 / 3.0), null);
        }

        g2d.drawImage(rocketImage, 0, 0, (int) rocket.getWidth(), (int) rocket.getHeight(), null);
    }

    private void renderGasThrusters(Rocket rocket, Graphics2D g2d) {

        if (rocket.isRightThrusterActivated()) {
            renderGas(g2d, rocket.getWidth() * 0.9, rocket.getHeight() * 0.25, -Math.PI * 3 / 4.0, rocket);
        }

        if (rocket.isLeftThrusterActivated()) {
            renderGas(g2d, rocket.getWidth() * 0.4, rocket.getHeight() * 0.1, Math.PI * 3 / 4.0, rocket);
        }

    }

    private void renderGas(Graphics2D g2d, double x, double y, double rotation, Rocket rocket) {

        double scale = 0.8 + 0.2 * (Math.cos(System.currentTimeMillis()) + 2) / 2;

        AffineTransform prevTransform = g2d.getTransform();
        AffineTransform transform     = new AffineTransform();

        transform.translate(x, y);
        transform.rotate(rotation);
        transform.scale(scale, scale);

        g2d.transform(transform);
        g2d.drawImage(gasImage, 0, 0, (int) (rocket.getWidth() * 2 / 5.0), (int) (rocket.getHeight() * 2 / 5.0), null);
        g2d.setTransform(prevTransform);
    }

}
