package fr.insa.gravityrocket;

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
        Rocket   rocket       = new Rocket(basicTank, basicReactor);

        rocket.setRotation(Math.PI / 2.0);
        rocket.setXPos(400);
        rocket.setYPos(400);

        Image earthTexture = ImageHelper.loadImage("/textures/star/earth.png", 100, 100);
        Image venusTexture = ImageHelper.loadImage("/textures/star/venus.png", 100, 100);
        Image moonTexture  = ImageHelper.loadImage("/textures/star/moon.png", 20, 20);

        Planet    earth = new Planet(earthTexture, 2 * Math.pow(10, 9), 100, 0, 0);
        Satellite moon  = new Satellite(moonTexture, earth, 10, Math.PI / 16, 2 * Math.pow(10, 10), 20);
        Planet    venus = new Planet(venusTexture, 2 * Math.pow(10, 10), 100, 1500, 1000);

        earth.setRotationSpeed(Math.PI / 16);
        venus.setRotationSpeed(-Math.PI / 32);

        this.currentLevel.addEntity(rocket);
        this.currentLevel.addEntity(venus);
        this.currentLevel.addEntity(earth);
        this.currentLevel.addEntity(moon);
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
