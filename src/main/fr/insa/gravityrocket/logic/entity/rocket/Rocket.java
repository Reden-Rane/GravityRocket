package fr.insa.gravityrocket.logic.entity.rocket;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.ImageHelper;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.IDestroyable;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.sound.SoundHelper;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Rocket extends Entity implements IDestroyable
{

    private final Image rocketImage;
    private final Image flameImage;
    private final Image gasImage;

    private final MediaPlayer boosterSoundPlayer;

    /**
     * Les points de vie de la fusée
     */
    private int life;

    /**
     * Le réservoir contenant le carburant de la fusée
     */
    private FuelTank tank;
    /**
     * Le booster permet d'accélérer la fusée
     */
    private Reactor  boosterReactor;

    private boolean leftThrusterActivated;
    private boolean rightThrusterActivated;

    public Rocket() {
        this(0, 0, 0);
    }

    public Rocket(double posX, double posY, double rotation) {
        super(posX, posY, 15, 36, rotation);

        //On charge les ressources liées à la fusée (textures, sons)
        this.rocketImage = ImageHelper.loadImage("/textures/entity/rocket/rocket.png", (int) (getWidth()), (int) (getHeight()));
        this.flameImage = ImageHelper.loadImage("/textures/entity/rocket/flame.png", (int) (getWidth() * 2 / 3.0), (int) (getHeight() * 2 / 3.0));
        this.gasImage = ImageHelper.loadImage("/textures/entity/rocket/gas.png", (int) (getWidth() * 2 / 5.0), (int) (getHeight() * 2 / 5.0));
        this.boosterSoundPlayer = SoundHelper.createPlayer("/sounds/rocket_booster.wav", true);

        this.life = 10;
    }

    public Rocket(FuelTank tank, Reactor boosterReactor) {
        this(0, 0, 0);
        this.tank = tank;
        this.boosterReactor = boosterReactor;
    }

    @Override
    public void update(double dt) {
        updateInputs();
        updateBooster();
        updateTank(dt);
        updateSounds();
        super.update(dt);
    }

    private void updateInputs() {

        KeyboardHandler keyboardHandler = GravityRocket.getInstance().getKeyboardHandler();

        if (keyboardHandler.isKeyPressed(KeyEvent.VK_UP) && !getTank().isEmpty()) {
            getBoosterReactor().setActive(true);
        } else {
            getBoosterReactor().setActive(false);
        }

        this.rightThrusterActivated = !getTank().isEmpty() && (keyboardHandler.isKeyPressed(KeyEvent.VK_LEFT) || keyboardHandler.isKeyPressed(KeyEvent.VK_DOWN));
        this.leftThrusterActivated = !getTank().isEmpty() && (keyboardHandler.isKeyPressed(KeyEvent.VK_RIGHT) || keyboardHandler.isKeyPressed(KeyEvent.VK_DOWN));
    }

    private void updateBooster() {

        if (getBoosterReactor().isActive()) {
            double forcePropulsionX = getBoosterReactor().getPropulsionForce() * Math.sin(getRotation());
            double forcePropulsionY = -getBoosterReactor().getPropulsionForce() * Math.cos(getRotation());
            setXAcceleration(getXAcceleration() + forcePropulsionX / getMass());
            setYAcceleration(getYAcceleration() + forcePropulsionY / getMass());
        }

        if (this.leftThrusterActivated && this.rightThrusterActivated) {
            double forcePropulsionX = -getBoosterReactor().getPropulsionForce() * Math.sin(getRotation());
            double forcePropulsionY = getBoosterReactor().getPropulsionForce() * Math.cos(getRotation());
            setRotationSpeed(0);
            setXAcceleration(getXAcceleration() + forcePropulsionX / getMass());
            setYAcceleration(getYAcceleration() + forcePropulsionY / getMass());
        } else if (this.leftThrusterActivated) {
            setRotationSpeed(Math.PI * 0.8);
        } else if (this.rightThrusterActivated) {
            setRotationSpeed(-Math.PI * 0.8);
        } else {
            setRotationSpeed(0);
        }
    }

    private void updateTank(double dt) {

        if (getBoosterReactor().isActive()) {
            getTank().removeFuel(getBoosterReactor().getConsumption() * dt);
        }

        if (this.leftThrusterActivated) {
            getTank().removeFuel(getBoosterReactor().getConsumption() * 0.5 * dt);
        }

        if (this.rightThrusterActivated) {
            getTank().removeFuel(getBoosterReactor().getConsumption() * 0.5 * dt);
        }

    }

    private void updateSounds() {
        if (getBoosterReactor().isActive()) {
            this.boosterSoundPlayer.play();
        } else {
            this.boosterSoundPlayer.stop();
        }
    }

    public FuelTank getTank() {
        return tank;
    }

    public void setTank(FuelTank tank) {
        this.tank = tank;
    }

    public Reactor getBoosterReactor() {
        return boosterReactor;
    }

    public void setBoosterReactor(Reactor boosterReactor) {
        this.boosterReactor = boosterReactor;
    }

    @Override
    public void render(Graphics2D g2d) {

        renderGasThrusters(g2d);

        if (getBoosterReactor().isActive()) {
            g2d.drawImage(flameImage, (int) (getWidth() * 0.17), (int) (getHeight() * 0.88), flameImage.getWidth(null), flameImage.getHeight(null), null);
        }

        g2d.drawImage(rocketImage, 0, 0, (int) getWidth(), (int) getHeight(), null);
    }

    private void renderGasThrusters(Graphics2D g2d) {

        if (this.rightThrusterActivated) {
            renderGas(g2d, getWidth() * 0.9, getHeight() * 0.25, -Math.PI * 3 / 4.0);
        }

        if (this.leftThrusterActivated) {
            renderGas(g2d, getWidth() * 0.4, getHeight() * 0.1, Math.PI * 3 / 4.0);
        }

    }

    private void renderGas(Graphics2D g2d, double x, double y, double rotation) {

        double scale = 0.8 + 0.2 * (Math.cos(System.currentTimeMillis()) + 2) / 2;

        AffineTransform prevTransform = g2d.getTransform();
        AffineTransform transform     = new AffineTransform();

        transform.translate(x, y);
        transform.rotate(rotation);
        transform.scale(scale, scale);

        g2d.transform(transform);
        g2d.drawImage(gasImage, 0, 0, gasImage.getWidth(null), gasImage.getHeight(null), null);
        g2d.setTransform(prevTransform);
    }

    @Override
    public double getMass() {
        return 10000 + getTank().getMass() + getBoosterReactor().getMass();
    }

    @Override
    public boolean isDestroyed() {
        return life <= 0;
    }

}