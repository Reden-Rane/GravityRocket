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

public class Level4 extends LandingLevel
{

    private final Image earthTexture;
    private final Image venusTexture;
    private final Image moonTexture;
    private final Image uranusTexture;

    private final Planet    earth;
    private final Satellite moon;
    private final Planet    venus;
    private final Planet    uranus;

    public Level4() {
        super(GravityRocket.getInstance().getSoundHandler().musicPlayers[3], RenderManager.loadImage("/textures/background_3.jpg", 1920, 1080), new Rectangle(-900, -650, 1650 * 2, 1150 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 200, 200);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 40, 40);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        this.earth = new Planet(this, "Terre", earthTexture, 1 * Math.pow(10, 9), 150, 2000, 1200);
        this.moon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 10), 20, earth, 75, Math.PI / 16);
        this.venus = new Planet(this, "Vénus", venusTexture, 5 * Math.pow(10, 8), 350, 700, 500);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 0 * Math.pow(10, 9), 100, -700, -200);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.venus.setRotationSpeed(-Math.PI / 16);
        this.uranus.setRotationSpeed(Math.PI / 8);

        this.setTargetedPlanetHaloSize(20);

        resetLevel();
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(earth);
        addEntity(moon);
        addEntity(venus);
        addEntity(uranus);

        setTargetedPlanet(moon);

        addFuel(60, 10, -200, 200);
        addFuel(60, 10, 1700, 200);
        addFuel(60, 10, -200, 800);
        addFuel(60, 10, -200, 800);
        addFuel(60, 10, 1700, 800);

        Alien alien1 = new OrbitingAlien(this, 700, venus, 100, 0.5);
        Alien alien2 = new WanderingAlien(this, 1000, -400, 1000);
        Alien alien3 = new WanderingAlien(this, 1000, 1900, 0);

        addEntity(alien1);
        addEntity(alien2);
        addEntity(alien3);

        addAsteroid(95, EnumAsteroidVariant.ASTEROID_1, 1300, 400, -Math.PI / 16);
        addAsteroid(105, EnumAsteroidVariant.ASTEROID_2, 100, 600, -Math.PI / 18);

        FuelTank basicTank = new FuelTank(50);
        basicTank.setFuelVolume(50);
        Reactor basicReactor = new Reactor(4, 900_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(uranus);
    }

}