package fr.insa.gravityrocket.logic.entity.rocket;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.EnumGameOverType;
import fr.insa.gravityrocket.logic.Level;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.IDestroyable;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.sound.SoundHelper;
import javafx.scene.media.MediaPlayer;

import java.awt.event.KeyEvent;

public class Rocket extends Entity implements IDestroyable
{

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

    public Rocket(Level level, FuelTank tank, Reactor boosterReactor) {
        this(level, tank, boosterReactor, 0, 0, 0);
    }

    public Rocket(Level level, FuelTank tank, Reactor boosterReactor, double posX, double posY, double rotation) {
        super(level, posX, posY, 15, 36, rotation);
        this.tank = tank;
        this.boosterReactor = boosterReactor;
        this.boosterSoundPlayer = SoundHelper.createPlayer("/sounds/rocket_booster.wav", true);
        this.life = 10;
    }

    @Override
    public void update(double dt) {
        updateInputs();
        updateBooster();
        updateTank(dt);
        updateSounds();
        super.update(dt);
    }

    @Override
    public void onCollisionWith(Entity entity) {
        if (entity instanceof Planet) {

            Planet planet = (Planet) entity;

            this.boosterSoundPlayer.stop();
            getBoosterReactor().setActive(false);
            this.leftThrusterActivated = false;
            this.rightThrusterActivated = false;

            if (canLandOnPlanet(planet)) {

                if (getLevel().getTargetedPlanet() == planet) {
                    getLevel().setGameOver(true, EnumGameOverType.SUCCESS);
                } else {
                    getBoosterReactor().setActive(false);
                    getLevel().setGameOver(true, EnumGameOverType.WRONG_PLANET);
                }


            } else {
                crashRocket();
                getLevel().setGameOver(true, EnumGameOverType.CRASH);
            }
        }
    }

    private boolean canLandOnPlanet(Planet planet) {
        double xPlanetNormal = this.getXPos() - planet.getXPos();
        double yPlanetNormal = this.getYPos() - planet.getYPos();
        double normalLength  = Math.sqrt(xPlanetNormal * xPlanetNormal + yPlanetNormal * yPlanetNormal);
        xPlanetNormal /= normalLength;
        yPlanetNormal /= normalLength;

        double xRocketDirection = Math.sin(getRotation());
        double yRocketDirection = -Math.cos(getRotation());

        double angle          = Math.acos(xPlanetNormal * xRocketDirection + yPlanetNormal * yRocketDirection);
        double speedMagnitude = Math.sqrt(getXSpeed() * getXSpeed() + getYSpeed() * getYSpeed());
        return angle < Math.toRadians(20) && speedMagnitude < 100;
    }

    private void crashRocket() {
        this.life = 0;
        this.boosterSoundPlayer.stop();
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

    @Override
    public double getMass() {
        return 10000 + getTank().getMass() + getBoosterReactor().getMass();
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
            accelerate(forcePropulsionX / getMass(), forcePropulsionY / getMass());
        } else if (this.leftThrusterActivated) {
            setRotationSpeed(Math.PI * 0.8);
        } else if (this.rightThrusterActivated) {
            setRotationSpeed(-Math.PI * 0.8);
        } else {
            setRotationSpeed(0);
        }
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