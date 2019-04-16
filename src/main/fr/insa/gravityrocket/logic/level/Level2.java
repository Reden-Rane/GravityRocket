package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.alien.WanderingAlien;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level2 extends LandingLevel
{

    private final Image earthTexture;
    private final Image venusTexture;
    private final Image moonTexture;
    private final Image uranusTexture;

    private final Planet    earth;
    private final Satellite moon;
    private final Planet    venus;
    private final Planet    uranus;

    public Level2() {
        super(GravityRocket.getInstance().getSoundHandler().musicPlayers[1], RenderManager.loadImage("/textures/background_1.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 200, 200);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 60, 60);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 6), 120, -200, -200);
        this.moon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 6), 30, earth, 100, Math.PI / 16);
        this.venus = new Planet(this, "Vénus", venusTexture, 2 * Math.pow(10, 6), 120, 1850, 1000);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 1 * Math.pow(10, 10), 150, 750, 400);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.venus.setRotationSpeed(-Math.PI / 32);
        this.uranus.setRotationSpeed(Math.PI / 8);

        this.setTargetedPlanetHaloSize(32);

        resetLevel();
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(venus);
        addEntity(earth);
        addEntity(moon);
        addEntity(uranus);

        setTargetedPlanet(venus);

        addFuel(60, 10, 1020, 50);
        addFuel(60, 10, 450, 750);

        Alien alien1 = new WanderingAlien(this, 600, 1550, 420);
        Alien alien2 = new WanderingAlien(this, 700, 40, 800);

        addEntity(alien1);
        addEntity(alien2);

        addAsteroid(120, EnumAsteroidVariant.ASTEROID_2, 1610, 320, -Math.PI / 22);
        addAsteroid(90, EnumAsteroidVariant.ASTEROID_2, -30, 690, -Math.PI / 14);
        addAsteroid(45, EnumAsteroidVariant.ASTEROID_1, 850, 930, -Math.PI / 32);
        addAsteroid(55, EnumAsteroidVariant.ASTEROID_1, 410, 10, -Math.PI / 12);

        FuelTank basicTank = new FuelTank(80);
        basicTank.setFuelVolume(80);
        Reactor basicReactor = new Reactor(5, 800_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);
        addEntity(rocket);

        rocket.attachToPlanet(earth);
    }

}