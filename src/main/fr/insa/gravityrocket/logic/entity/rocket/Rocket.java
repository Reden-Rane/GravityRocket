package fr.insa.gravityrocket.logic.entity.rocket;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.particle.Explosion;
import fr.insa.gravityrocket.logic.entity.particle.Laser;
import fr.insa.gravityrocket.logic.level.Level;

import java.awt.event.KeyEvent;

public class Rocket extends Entity
{

    /**
     * Le réservoir contenant le carburant de la fusée
     */
    private FuelTank tank;
    /**
     * Le booster permet d'accélérer la fusée
     */
    private Reactor  boosterReactor;

    /**
     * La planète sur laquelle la fusée est posée, peut être null
     */
    private Planet attachedPlanet;
    private double attachedPlanetInitialAngle;
    private double attachedAngle;

    private boolean leftThrusterActivated;
    private boolean rightThrusterActivated;

    public Rocket(Level level, FuelTank tank, Reactor boosterReactor) {
        this(level, tank, boosterReactor, 0, 0, 0);
    }

    public Rocket(Level level, FuelTank tank, Reactor boosterReactor, double posX, double posY, double rotation) {
        super(level, posX, posY, 15, 36, rotation);
        this.tank = tank;
        this.boosterReactor = boosterReactor;
    }

    @Override
    public void update(double dt) {
        if (!getLevel().isGameOver()) {
            updateInputs();
            updateBooster();
            updateTank(dt);
            updateSounds();
            super.update(dt);
        }

        if (isAttached()) {
            updateAttachedPos();
        }
    }

    private void updateInputs() {

        KeyboardHandler keyboardHandler = GravityRocket.getInstance().getKeyboardHandler();

        if (isAttached()) {

            getBoosterReactor().setActive(false);
            this.rightThrusterActivated = false;
            this.leftThrusterActivated = false;
            this.setXAcceleration(0);
            this.setYAcceleration(0);
            this.setXSpeed(0);
            this.setYSpeed(0);

            if (keyboardHandler.isKeyPressed(KeyEvent.VK_UP)) {
                detach();
            }

        } else {
            if (keyboardHandler.isKeyPressed(KeyEvent.VK_UP) && !getTank().isEmpty()) {
                getBoosterReactor().setActive(true);
            } else {
                getBoosterReactor().setActive(false);
            }

            this.rightThrusterActivated = !getTank().isEmpty() && (keyboardHandler.isKeyPressed(KeyEvent.VK_LEFT) || keyboardHandler.isKeyPressed(KeyEvent.VK_DOWN));
            this.leftThrusterActivated = !getTank().isEmpty() && (keyboardHandler.isKeyPressed(KeyEvent.VK_RIGHT) || keyboardHandler.isKeyPressed(KeyEvent.VK_DOWN));
        }
    }

    private void updateBooster() {

        if (getBoosterReactor().isActive()) {
            double forcePropulsionX = getBoosterReactor().getPropulsionForce() * Math.sin(getRotation());
            double forcePropulsionY = -getBoosterReactor().getPropulsionForce() * Math.cos(getRotation());
            accelerate(forcePropulsionX / getMass(), forcePropulsionY / getMass());
        }

        if (this.leftThrusterActivated && this.rightThrusterActivated) {
            double forcePropulsionX = -getBoosterReactor().getPropulsionForce() * Math.sin(getRotation());
            double forcePropulsionY = getBoosterReactor().getPropulsionForce() * Math.cos(getRotation());
            setRotationSpeed(0);
            accelerate(forcePropulsionX / getMass(), forcePropulsionY / getMass());
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
            getSoundHandler().boosterSoundPlayer.play();
        } else {
            getSoundHandler().boosterSoundPlayer.stop();
        }
    }

    public boolean isAttached() {
        return this.attachedPlanet != null;
    }

    private void updateAttachedPos() {
        double angle    = attachedAngle + (attachedPlanet.getRotation() - attachedPlanetInitialAngle);
        double x        = attachedPlanet.getXPos() + (attachedPlanet.getRadius() + getHeight() * 0.52) * Math.cos(angle);
        double y        = attachedPlanet.getYPos() + (attachedPlanet.getRadius() + getHeight() * 0.52) * Math.sin(angle);
        double rotation = angle - Math.PI * 1.5;
        setPos(x, y);
        setRotation(rotation);
    }

    @Override
    public void onCollisionWith(Entity entity) {

        if (entity instanceof Laser) {
            explode();
            getLevel().setLevelState(EnumLevelState.DEAD);
        }

    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return entity instanceof Planet;
    }

    @Override
    public double getMass() {
        return 10000 + getTank().getMass() + getBoosterReactor().getMass();
    }

    public void explode() {
        getSoundHandler().explosionSoundPlayer.play();
        this.stopAllEngines();
        this.requestRemove();

        Explosion explosion = new Explosion(getLevel(), 50, 50);
        explosion.setPos(getXPos(), getYPos());
        getLevel().addEntity(explosion);
    }

    public double getAngleWithPlanet(Planet planet) {
        double xPlanetNormal = getXPos() - planet.getXPos();
        double yPlanetNormal = getYPos() - planet.getYPos();
        double normalLength  = Math.sqrt(xPlanetNormal * xPlanetNormal + yPlanetNormal * yPlanetNormal);
        xPlanetNormal /= normalLength;
        yPlanetNormal /= normalLength;
        double xRocketDirection = Math.sin(getRotation());
        double yRocketDirection = -Math.cos(getRotation());
        return Math.abs(Math.acos(xPlanetNormal * xRocketDirection + yPlanetNormal * yRocketDirection));
    }

    public void detach() {
        double angle = attachedAngle + (attachedPlanet.getRotation() - attachedPlanetInitialAngle);
        double x     = attachedPlanet.getXPos() + (attachedPlanet.getRadius() + getHeight() * 0.6) * Math.cos(angle);
        double y     = attachedPlanet.getYPos() + (attachedPlanet.getRadius() + getHeight() * 0.6) * Math.sin(angle);
        setPos(x, y);
        this.attachedPlanet = null;
    }

    public void attachToPlanet(Planet planet) {
        this.attachedPlanet = planet;
        this.attachedPlanetInitialAngle = planet.getRotation();
        this.attachedAngle = Math.atan2(getYPos() - planet.getYPos(), getXPos() - planet.getXPos());
        updateAttachedPos();
    }

    public void stopAllEngines() {
        getBoosterReactor().setActive(false);
        this.leftThrusterActivated = false;
        this.rightThrusterActivated = false;
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

    public boolean isLeftThrusterActivated() {
        return leftThrusterActivated;
    }

    public boolean isRightThrusterActivated() {
        return rightThrusterActivated;
    }

}