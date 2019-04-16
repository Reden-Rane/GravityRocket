package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.alien.OrbitingAlien;
import fr.insa.gravityrocket.logic.entity.alien.WanderingAlien;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level8 extends ReachingZoneLevel
{

    private final Image sunTexture;
    private final Image earthTexture;
    private final Image neptunTexture;
    private final Image moonTexture;
    private final Image mercuryTexture;
    private final Image venusTexture;

    private final Planet    sun;
    private final Satellite earth;
    private final Satellite neptun;
    private final Satellite earthMoon;
    private final Satellite mercuryMoon;
    private final Satellite mercury;
    private final Planet    venus;

    public Level8() {
        super("Constellation du Rhinocéros", GravityRocket.getInstance().getSoundHandler().musicPlayers[7], RenderManager.loadImage("/textures/background_7.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 160, 160);
        this.sunTexture = RenderManager.loadImage("/textures/star/sun.png", 400, 400);
        this.neptunTexture = RenderManager.loadImage("/textures/star/neptun.png", 400, 400);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 40, 40);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 300, 300);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);

        this.sun = new Planet(this, "Soleil", sunTexture, 2 * Math.pow(10, 7), 200);
        this.sun.setRotationSpeed(Math.PI / 10);

        this.earth = new Satellite(this, "Terre", earthTexture, 7 * Math.pow(10, 7), 80, sun, 200, Math.PI / 12);
        this.earth.setRotationSpeed(Math.PI / 16);

        this.neptun = new Satellite(this, "Neptune", neptunTexture, 7 * Math.pow(10, 9), 200, sun, 700, Math.PI / 20);
        this.neptun.setRotationSpeed(Math.PI / 8);

        this.earthMoon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 7), 20, earth, 80, Math.PI / 4);

        this.mercury = new Satellite(this, "Mercure", mercuryTexture, 7 * Math.pow(10, 9), 150, sun, 700, Math.PI / 20);
        this.mercury.setRotationSpeed(Math.PI / 20);

        this.mercuryMoon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 7), 40, mercury, 80, Math.PI / 8);

        this.venus = new Planet(this, "Vénus", venusTexture, 5 * Math.pow(10, 8), 150, 1400, 700);
        this.venus.setRotationSpeed(-Math.PI / 16);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon polygon = new Polygon();

        int x = 750;
        int y = 500;

        polygon.addPoint(x + 1225, y + 600);
        polygon.addPoint(x + 1600, y + 600);
        polygon.addPoint(x + 1600, y + 840);
        polygon.addPoint(x + 1150, y + 840);
        polygon.addPoint(x + 1100, y + 700);

        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        earth.setOrbitalAngle(Math.PI);
        earthMoon.setOrbitalAngle(Math.PI);
        mercury.setOrbitalAngle(Math.PI * 2 * 0.8);
        neptun.setOrbitalAngle(0);

        addFuel(60, 5, 1135, 1215);

        Alien alien1 = new OrbitingAlien(this, 800, venus, 100, 0.5);
        Alien alien2 = new WanderingAlien(this, 1000, 200, 1250);
        Alien alien3 = new WanderingAlien(this, 800, 700, 1350);
        Alien alien4 = new WanderingAlien(this, 800, 1700, 0);
        Alien alien5 = new WanderingAlien(this, 900, 1820, 680);

        addEntity(alien1);
        addEntity(alien2);
        addEntity(alien3);
        addEntity(alien4);
        addEntity(alien5);

        int x = 2000;
        int y = -700;

        addAsteroid(100, EnumAsteroidVariant.ASTEROID_1, x + 410, y + 430, -Math.PI / 32);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_2, x + 70, y + 590, -Math.PI / 16);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_1, x - 80, y + 830, -Math.PI / 28);
        addAsteroid(200, EnumAsteroidVariant.ASTEROID_2, x - 210, y + 1020, -Math.PI / 18);
        addAsteroid(60, EnumAsteroidVariant.ASTEROID_1, x - 100, y + 720, -Math.PI / 22);
        addAsteroid(80, EnumAsteroidVariant.ASTEROID_2, x - 320, y + 1170, -Math.PI / 10);
        addAsteroid(65, EnumAsteroidVariant.ASTEROID_2, x + 220, y + 500, -Math.PI / 14);
        addAsteroid(80, EnumAsteroidVariant.ASTEROID_1, 1240, 1090, -Math.PI / 12);
        addAsteroid(65, EnumAsteroidVariant.ASTEROID_1, 1030, 1340, -Math.PI / 26);
        addAsteroid(100, EnumAsteroidVariant.ASTEROID_2, 1600, 1240, -Math.PI / 30);

        addEntity(sun);
        addEntity(earth);
        addEntity(neptun);
        addEntity(earthMoon);
        addEntity(mercury);
        addEntity(mercuryMoon);
        addEntity(venus);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(50);
        Reactor basicReactor = new Reactor(5, 800_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}
