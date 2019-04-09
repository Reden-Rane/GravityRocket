package fr.insa.gravityrocket.logic;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.logic.level.LandingLevel;
import fr.insa.gravityrocket.logic.level.Level;
import fr.insa.gravityrocket.logic.level.ReachingZoneLevel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GravityRocketModel
{

    public static final Level   level1 = createLandingLevelTest();
    private             Level   currentLevel;
    private             boolean paused = true;

    private static LandingLevel createLandingLevelTest() {

        Rectangle preferredView = new Rectangle(-750, -500, 1500 * 2, 1000 * 2);
        Rectangle bounds        = new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3);

        LandingLevel landingLevel = new LandingLevel(preferredView, bounds);

        Image earthTexture  = RenderManager.loadImage("/textures/star/earth.png", 200, 200);
        Image venusTexture  = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        Image moonTexture   = RenderManager.loadImage("/textures/star/moon.png", 40, 40);
        Image uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        Planet    earth  = new Planet(landingLevel, "Terre", earthTexture, 2 * Math.pow(10, 9), 100, 0, 0);
        Satellite moon   = new Satellite(landingLevel, "Lune", moonTexture, earth, 50, Math.PI / 16, 2 * Math.pow(10, 10), 20);
        Planet    venus  = new Planet(landingLevel, "Vénus", venusTexture, 2 * Math.pow(10, 10), 100, 1500, 1000);
        Planet    uranus = new Planet(landingLevel, "Uranus", uranusTexture, 2 * Math.pow(10, 9), 100, 1500, 0);

        earth.setRotationSpeed(Math.PI / 16);
        venus.setRotationSpeed(-Math.PI / 32);
        uranus.setRotation(Math.PI / 8);

/*        for (int i = 0; i < 15; i++) {
            int      volume   = (int) (16 + Math.random() * 16);
            ItemFuel itemFuel = new ItemFuel(landingLevel, volume, volume);
            itemFuel.setPos(1500 - i * 50, 200);
            landingLevel.addEntity(itemFuel);
        }*/

        landingLevel.addEntity(venus);
        landingLevel.addEntity(earth);
        landingLevel.addEntity(moon);
        landingLevel.addEntity(uranus);

        landingLevel.setTargetedPlanet(earth);

        return landingLevel;
    }

    /**
     * Méthode temporaire de test de niveau
     */
    public void setupTestLevel() {
        this.currentLevel = level1;
        FuelTank basicTank    = new FuelTank(100);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this.currentLevel, basicTank, basicReactor);
        rocket.setRotation(-Math.PI / 2.0);
        rocket.setPos(1800, 200);
        this.currentLevel.addEntity(rocket);

        setPaused(false);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;

        if (paused) {
            this.currentLevel.stopAllSounds();
        }
    }

    private ReachingZoneLevel createReachingZoneLevelTest() {
        Rectangle preferredView = new Rectangle(-750, -500, 1500 * 2, 1000 * 2);
        Rectangle bounds        = new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3);

        Shape zone = new Rectangle2D.Double(0, 0, 500, 500);
        return new ReachingZoneLevel(preferredView, bounds, zone);
    }

    public void update(double dt) {
        if (getCurrentLevel() != null && !paused) {
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
