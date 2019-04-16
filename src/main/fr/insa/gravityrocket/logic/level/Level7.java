package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.alien.WanderingAlien;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level7 extends ReachingZoneLevel
{

    private final Image earthTexture;
    private final Image marsTexture;
    private final Image jupiterTexture;

    private final Planet earth;
    private final Planet mars;
    private final Planet jupiter;

    public Level7() {
        super("Constellation du Bonbon", GravityRocket.getInstance().getSoundHandler().musicPlayers[6], RenderManager.loadImage("/textures/background_6.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.marsTexture = RenderManager.loadImage("/textures/star/mars.png", 120, 120);
        this.jupiterTexture = RenderManager.loadImage("/textures/star/jupiter.png", 300, 300);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.mars = new Planet(this, "Vénus", marsTexture, 7 * Math.pow(10, 9), 120, 670, 300);
        this.jupiter = new Planet(this, "Uranus", jupiterTexture, 1 * Math.pow(10, 9), 300, 1800, 500);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.mars.setRotationSpeed(Math.PI / 8);
        this.jupiter.setRotationSpeed(Math.PI / 32);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon polygon = new Polygon();
        int     x       = 900;
        int     y       = 900;

        polygon.addPoint(x, y);
        polygon.addPoint(x += 60, y += 50);
        polygon.addPoint(x += 20, y -= 25);
        polygon.addPoint(x += 20, y -= 10);
        polygon.addPoint(x += 20, y -= 5);
        polygon.addPoint(x += 20, y += 5);
        polygon.addPoint(x += 20, y += 10);
        polygon.addPoint(x += 20, y += 25);
        polygon.addPoint(x += 60, y -= 50);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x -= 10, y += 10);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x += 10, y += 10);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x -= 10, y += 10);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x += 10, y += 10);
        polygon.addPoint(x, y += 20);
        polygon.addPoint(x -= 60, y -= 50);
        polygon.addPoint(x -= 20, y += 25);
        polygon.addPoint(x -= 20, y += 10);
        polygon.addPoint(x -= 20, y += 5);
        polygon.addPoint(x -= 20, y -= 5);
        polygon.addPoint(x -= 20, y -= 10);
        polygon.addPoint(x -= 20, y -= 25);
        polygon.addPoint(x -= 60, y += 50);
        polygon.addPoint(x, y -= 20);
        polygon.addPoint(x += 10, y -= 10);
        polygon.addPoint(x, y -= 20);
        polygon.addPoint(x -= 10, y -= 10);
        polygon.addPoint(x, y -= 20);
        polygon.addPoint(x, y -= 20);
        polygon.addPoint(x += 10, y -= 10);
        polygon.addPoint(x, y -= 20);
        polygon.addPoint(x -= 10, y -= 10);

        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(earth);
        addEntity(mars);
        addEntity(jupiter);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(20);
        Reactor basicReactor = new Reactor(5, 800_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addFuel(60, 4, 260, -50);
        addFuel(60, 4, 1140, 350);

        Alien alien  = new WanderingAlien(this, 900, 1400, 0);
        Alien alien2 = new WanderingAlien(this, 900, 310, 620);

        addEntity(alien);
        addEntity(alien2);

        addAsteroid(100, EnumAsteroidVariant.ASTEROID_1, 410, 430, -Math.PI / 32);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_2, 70, 590, -Math.PI / 16);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_2, -80, 830, -Math.PI / 28);
        addAsteroid(200, EnumAsteroidVariant.ASTEROID_2, -210, 1020, -Math.PI / 18);
        addAsteroid(60, EnumAsteroidVariant.ASTEROID_1, -100, 720, -Math.PI / 22);
        addAsteroid(80, EnumAsteroidVariant.ASTEROID_1, -320, 1170, -Math.PI / 10);
        addAsteroid(65, EnumAsteroidVariant.ASTEROID_2, 220, 500, -Math.PI / 14);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }


}