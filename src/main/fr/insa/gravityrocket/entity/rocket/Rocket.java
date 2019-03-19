package fr.insa.gravityrocket.entity.rocket;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.ImageHelper;
import fr.insa.gravityrocket.entity.Entity;
import fr.insa.gravityrocket.entity.IDestroyable;
import fr.insa.gravityrocket.input.KeyboardHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Rocket extends Entity implements IDestroyable
{

    private final Image rocketImage;
    private final Image flameImage;

    private int      life;
    private FuelTank tank;
    private Reactor  reactor;

    public Rocket() {
        this(0, 0, 0);
    }

    public Rocket(FuelTank tank, Reactor reactor) {
        this(0, 0, 0);
        this.tank = tank;
        this.reactor = reactor;
    }

    public Rocket(double posX, double posY, double rotation) {
        super(posX, posY, 30, 72, rotation);
        this.rocketImage = ImageHelper.loadImage("/rocket.png", (int) (getWidth()), (int) (getHeight()));
        this.flameImage = ImageHelper.loadImage("/flame.png", (int) (getWidth() * 2 / 3.0), (int) (getHeight() * 2 / 3.0));
        this.life = 10;
    }

    @Override
    public void update(double dt) {

        KeyboardHandler keyboardHandler = GravityRocket.getInstance().getKeyboardHandler();

        //Active le réacteur si le joueur appuie sur espace
        getReactor().setActive(keyboardHandler.isKeyPressed(KeyEvent.VK_SPACE));

        if (keyboardHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
            setRotationSpeed(-Math.PI * 2 / 11.0);
        } else if (keyboardHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setRotationSpeed(Math.PI * 2 / 11.0);
        } else {
            setRotationSpeed(0);
        }

        super.update(dt);
    }

    @Override
    public void render(Graphics2D g2d) {
        if (getReactor().isActive() && !getTank().isEmpty()) {
            g2d.drawImage(flameImage, (int) (getWidth() * 0.17), (int) (getHeight() * 0.88), flameImage.getWidth(null), flameImage.getHeight(null), null);
        }

        g2d.drawImage(rocketImage, 0, 0, (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public boolean isDestroyed() {
        return life <= 0;
    }

    public FuelTank getTank() {
        return tank;
    }

    public void setTank(FuelTank tank) {
        this.tank = tank;
    }

    public Reactor getReactor() {
        return reactor;
    }

    public void setReactor(Reactor reactor) {
        this.reactor = reactor;
    }

    @Override
    public double getMass() {
        return 10000 + getTank().getMass() + getReactor().getMass();
    }

}