package fr.insa.gravityrocket;

import fr.insa.gravityrocket.entity.Planet;
import fr.insa.gravityrocket.entity.rocket.FuelTank;
import fr.insa.gravityrocket.entity.rocket.Reactor;
import fr.insa.gravityrocket.entity.rocket.Rocket;

public class GameModel
{

    private Level currentLevel;

    /**
     * Méthode temporaire de test de niveau
     */
    public void setupDefaultLevel() {
        this.currentLevel = new Level();
        FuelTank basicTank    = new FuelTank(100);
        Reactor  basicReactor = new Reactor(0.005, 1000);
        Rocket   rocket       = new Rocket(basicTank, basicReactor);
        rocket.setXPos(400);
        rocket.setYPos(400);
        this.currentLevel.addEntity(rocket);

        Planet planet1 = new Planet(200, 250, 250, 0);
        this.currentLevel.addEntity(planet1);

        Planet planet2 = new Planet(350, 1000, 500, 0);
        this.currentLevel.addEntity(planet2);
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
