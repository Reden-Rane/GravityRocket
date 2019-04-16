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

public class Level6 extends ReachingZoneLevel
{

    private final Image earthTexture;
    private final Image venusTexture;
    private final Image uranusTexture;
    private final Image mercuryTexture;
    private final Image jupiterTexture;

    private final Planet earth;
    private final Planet venus;
    private final Planet uranus;
    private final Planet mercury;
    private final Planet jupiter;


    public Level6() {
        super("Constellation du Taureau", GravityRocket.getInstance().getSoundHandler().musicPlayers[5], RenderManager.loadImage("/textures/background_5.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 250, 250);
        this.jupiterTexture = RenderManager.loadImage("/textures/star/jupiter.png", 250, 250);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.venus = new Planet(this, "Vénus", venusTexture, 2 * Math.pow(10, 10), 150, 1850, 1000);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 2 * Math.pow(10, 10), 150, 700, 250);
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 2 * Math.pow(10, 10), 155, 40, 870);
        this.jupiter = new Planet(this, "Jupiter", jupiterTexture, 2 * Math.pow(10, 10), 140, 0, 85);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.venus.setRotationSpeed(-Math.PI / 32);
        this.uranus.setRotationSpeed(Math.PI / 8);
        this.mercury.setRotationSpeed(Math.PI / 8);
        this.jupiter.setRotationSpeed(-Math.PI / 25);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon polygon = new Polygon();

        polygon.addPoint(900 + 160, 900);
        polygon.addPoint(900 + 478 - 160, 900);
        polygon.addPoint(900 + 478 - 10, 900 + 543 / 4);
        polygon.addPoint(900 + 478, 900 + 543 / 2);
        polygon.addPoint(900 + 478 - 160, 900 + 543);
        polygon.addPoint(900 + 160, 900 + 543);
        polygon.addPoint(900, 900 + 543 / 2);
        polygon.addPoint(910, 900 + 543 / 4);

        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addFuel(50, 10, 1100, 90);
        addFuel(50, 10, 700, 650);
        addFuel(50, 10, 50, 350);

        addEntity(earth);
        addEntity(uranus);
        addEntity(venus);
        addEntity(mercury);
        addEntity(jupiter);

        Alien alien1 = new WanderingAlien(this, 900, 1200, 700);
        Alien alien2 = new WanderingAlien(this, 900, 500, 600);
        Alien alien3 = new WanderingAlien(this, 900, -300, 1000);

        addEntity(alien1);
        addEntity(alien2);
        addEntity(alien3);

        addAsteroid(50, EnumAsteroidVariant.ASTEROID_1, 1000, 510, -Math.PI / 28);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_2, 1100, 300, Math.PI / 20);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_1, 800, 700, -Math.PI / 25);
        addAsteroid(100, EnumAsteroidVariant.ASTEROID_2, 600, 800, Math.PI / 25);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_2, 10, -100, -Math.PI / 10);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_1, 250, 40, -Math.PI / 25);
        addAsteroid(100, EnumAsteroidVariant.ASTEROID_2, 50, 600, Math.PI / 25);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_1, -100, 250, -Math.PI / 10);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(40);
        Reactor basicReactor = new Reactor(5, 1_000_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }


}