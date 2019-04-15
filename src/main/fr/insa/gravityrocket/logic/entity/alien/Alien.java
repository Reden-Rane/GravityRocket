package fr.insa.gravityrocket.logic.entity.alien;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.particle.Laser;
import fr.insa.gravityrocket.logic.level.Level;

public abstract class Alien extends Entity
{

    private final double shootingDistance;
    private       double shootingCooldown;

    private boolean wasPlayerInRange;
    private boolean playerInRange;

    public Alien(Level level, double shootingDistance) {
        this(level, shootingDistance, 0, 0, 0);
    }

    public Alien(Level level, double shootingDistance, double xPos, double yPos, double rotation) {
        super(level, xPos, yPos, 50, 50, rotation);
        this.shootingDistance = shootingDistance;
    }

    @Override
    public void update(double dt) {
        this.wasPlayerInRange = this.playerInRange;
        super.update(dt);

        if (distanceTo(getLevel().getRocket()) <= this.shootingDistance) {
            this.playerInRange = true;

            if (this.shootingCooldown == 0) {
                GravityRocket.getInstance().getSoundHandler().shootingSoundPlayer.stop();
                GravityRocket.getInstance().getSoundHandler().shootingSoundPlayer.play();

                double dx  = getLevel().getRocket().getXPos() - getXPos();
                double dy  = getLevel().getRocket().getYPos() - getYPos();
                double len = Math.sqrt(dx * dx + dy * dy);

                double angle      = Math.atan2(dy, dx) + Math.PI / 2;
                double laserSpeed = 800;

                Laser laser = new Laser(getLevel(), 17, 33, 3000);
                laser.setRotation(angle);
                laser.setPos(getXPos(), getYPos());
                laser.setSpeed(laserSpeed * dx / len, laserSpeed * dy / len);
                getLevel().addEntity(laser);

                resetShootingCooldown();
            }
        }

        if (!this.wasPlayerInRange && this.playerInRange) {
            GravityRocket.getInstance().getSoundHandler().alienSpeechSoundPlayer.stop();
            GravityRocket.getInstance().getSoundHandler().alienSpeechSoundPlayer.play();
        }

        this.shootingCooldown = Math.max(0, this.shootingCooldown - dt);
    }

    private void resetShootingCooldown() {
        this.shootingCooldown = 3;
    }

    @Override
    public double getMass() {
        return 5000;
    }

}