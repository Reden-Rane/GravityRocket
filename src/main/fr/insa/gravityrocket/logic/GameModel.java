package fr.insa.gravityrocket.logic;

import fr.insa.gravityrocket.graphics.ImageHelper;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class GameModel
{

    private Level currentLevel;

    /**
     * Méthode temporaire de test de niveau
     */
    public void setupDefaultLevel() {
        Rectangle preferredView = new Rectangle(-750, -500, 1500 * 2, 1000 * 2);
        Rectangle bounds        = new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3);

        this.currentLevel = new Level(preferredView, bounds);

        FuelTank basicTank    = new FuelTank(100);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this.currentLevel, basicTank, basicReactor);
        rocket.setRotation(-Math.PI / 2.0);
        rocket.setXPos(1800);
        rocket.setYPos(200);

        Image earthTexture  = ImageHelper.loadImage("/textures/star/earth.png", 200, 200);
        Image venusTexture  = ImageHelper.loadImage("/textures/star/venus.png", 200, 200);
        Image moonTexture   = ImageHelper.loadImage("/textures/star/moon.png", 40, 40);
        Image uranusTexture = ImageHelper.loadImage("/textures/star/uranus.png", 200, 200);

        Planet    earth  = new Planet(this.currentLevel, "Terre", earthTexture, 2 * Math.pow(10, 9), 100, 0, 0);
        Satellite moon   = new Satellite(this.currentLevel, "Lune", moonTexture, earth, 50, Math.PI / 16, 2 * Math.pow(10, 10), 20);
        Planet    venus  = new Planet(this.currentLevel, "Vénus", venusTexture, 2 * Math.pow(10, 10), 100, 1500, 1000);
        Planet    uranus = new Planet(this.currentLevel, "Uranus", uranusTexture, 2 * Math.pow(10, 9), 100, 1500, 0);

        earth.setRotationSpeed(Math.PI / 16);
        venus.setRotationSpeed(-Math.PI / 32);
        uranus.setRotation(Math.PI / 8);

        this.currentLevel.addEntity(rocket);
        this.currentLevel.addEntity(venus);
        this.currentLevel.addEntity(earth);
        this.currentLevel.addEntity(moon);
        this.currentLevel.addEntity(uranus);

        this.currentLevel.setTargetedPlanet(earth);
    }

    public void update(double dt) {
        if (getCurrentLevel() != null) {
            getCurrentLevel().update(dt);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

}
