package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Asteroid;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.alien.WanderingAlien;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
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

        ItemFuel itemFuel  = new ItemFuel(this, 60, 4);
        ItemFuel itemFuel2 = new ItemFuel(this, 60, 4);

        itemFuel.setPos(260, -50);
        itemFuel2.setPos(1140, 350);

        Alien alien = new WanderingAlien(this, 900, 1400, 0);

        Asteroid asteroid1 = new Asteroid(this, 100, EnumAsteroidVariant.ASTEROID_1);
        Asteroid asteroid2 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_2);
        Asteroid asteroid3 = new Asteroid(this, 50, EnumAsteroidVariant.ASTEROID_1);
        Asteroid asteroid4 = new Asteroid(this, 200, EnumAsteroidVariant.ASTEROID_2);
        Asteroid asteroid5 = new Asteroid(this, 60, EnumAsteroidVariant.ASTEROID_1);
        Asteroid asteroid6 = new Asteroid(this, 80, EnumAsteroidVariant.ASTEROID_1);
        Asteroid asteroid7 = new Asteroid(this, 65, EnumAsteroidVariant.ASTEROID_2);

        asteroid1.setPos(410, 430);
        asteroid2.setPos(70, 590);
        asteroid3.setPos(-80, 830);
        asteroid4.setPos(-210, 1020);
        asteroid5.setPos(-100, 720);
        asteroid6.setPos(-320, 1170);
        asteroid7.setPos(220, 500);

        asteroid1.setRotationSpeed(-Math.PI / 32);
        asteroid2.setRotationSpeed(-Math.PI / 16);
        asteroid3.setRotationSpeed(-Math.PI / 28);
        asteroid4.setRotationSpeed(-Math.PI / 24);
        asteroid5.setRotationSpeed(-Math.PI / 22);
        asteroid5.setRotationSpeed(-Math.PI / 34);
        asteroid6.setRotationSpeed(-Math.PI / 8);
        asteroid7.setRotationSpeed(-Math.PI / 30);

        addEntity(itemFuel);
        addEntity(itemFuel2);

        addEntity(alien);

        addEntity(asteroid1);
        addEntity(asteroid2);
        addEntity(asteroid3);
        addEntity(asteroid4);
        addEntity(asteroid5);
        addEntity(asteroid6);
        addEntity(asteroid7);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }


}