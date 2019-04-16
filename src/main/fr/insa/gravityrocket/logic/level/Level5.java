package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level5 extends ReachingZoneLevel
{

    private final Image  earthTexture;
    private final Image  mercuryTexture;
    private final Image  uranusTexture;
    private final Planet earth;
    private final Planet uranus;
    private final Planet mercury;

    public Level5() {
        super("Constellation du Verseau", GravityRocket.getInstance().getSoundHandler().musicPlayers[4], RenderManager.loadImage("/textures/background_4.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, 1800, 1000);
        this.earth.setRotationSpeed(Math.PI / 16);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 250, 250);
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 7 * Math.pow(10, 9), 125, 1100, 800);
        this.mercury.setRotationSpeed(Math.PI / 8);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 400, 400);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 5 * Math.pow(10, 9), 200, 0, 400);
        this.uranus.setRotationSpeed(Math.PI / 20);
        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon verseau = new Polygon();

        verseau.addPoint(-900 + 200, -150);
        verseau.addPoint(-900 + 421, -150);
        verseau.addPoint(-900 + 421, -150 + 623);
        verseau.addPoint(-900 + 300, -150 + 623);
        verseau.addPoint(-900 + 50, -150 + 500);
        verseau.addPoint(-900, -150 + 300);
        verseau.addPoint(-900 + 100, -150 + 50);

        return verseau;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(earth);
        addEntity(mercury);
        addEntity(uranus);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(40);
        Reactor basicReactor = new Reactor(5, 1_000_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addFuel(40, 5, 100, 100);
        addFuel(40, 7, 1100, 1000);

        addAsteroid(200, EnumAsteroidVariant.ASTEROID_1, 1150, 530, -Math.PI / 28);
        addAsteroid(120, EnumAsteroidVariant.ASTEROID_2, 1600, 100, -Math.PI / 10);
        addAsteroid(175, EnumAsteroidVariant.ASTEROID_2, 1600, -200, -Math.PI / 15);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_1, -300, 600, -Math.PI / 18);
        addAsteroid(240, EnumAsteroidVariant.ASTEROID_2, -700, 700, -Math.PI / 15);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_2, -900, 1000, -Math.PI / 10);
        addAsteroid(60, EnumAsteroidVariant.ASTEROID_1, 1450, 300, -Math.PI / 2);
        addAsteroid(60, EnumAsteroidVariant.ASTEROID_1, 1350, 450, -Math.PI / 2);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}