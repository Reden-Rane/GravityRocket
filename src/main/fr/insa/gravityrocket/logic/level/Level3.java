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

public class Level3 extends LandingLevel
{

    private final Image earthTexture;
    private final Image mercuryTexture;
    private final Image sunTexture;
    private final Image jupiterTexture;

    private final Planet earth;
    private final Planet mercury;
    private final Planet sun;
    private final Planet jupiter;

    public Level3() {
        super(GravityRocket.getInstance().getSoundHandler().musicPlayers[2], RenderManager.loadImage("/textures/background_2.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 200, 200);
        this.sunTexture = RenderManager.loadImage("/textures/star/sun.png", 500, 500);
        this.jupiterTexture = RenderManager.loadImage("/textures/star/jupiter.png", 250, 250);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 2 * Math.pow(10, 7), 100, 1700, 850);
        this.sun = new Planet(this, "Soleil", sunTexture, 4 * Math.pow(10, 9), 250, 1400, -100);
        this.jupiter = new Planet(this, "Jupiter", jupiterTexture, 2 * Math.pow(10, 10), 125, 200, 900);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.mercury.setRotationSpeed(-Math.PI / 32);
        this.sun.setRotationSpeed(Math.PI / 25);
        this.jupiter.setRotationSpeed(-Math.PI / 25);

        this.setTargetedPlanetHaloSize(32);

        resetLevel();
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(mercury);
        addEntity(earth);
        addEntity(sun);
        addEntity(jupiter);

        setTargetedPlanet(mercury);

        addFuel(30, 30, 200, 1100);
        addFuel(40, 40, 200, 1200);
        addFuel(50, 50, 200, 1300);

        addFuel(40, 20, 800, 1000);
        addFuel(40, 20, 1400, 800);
        addFuel(50, 50, 1800, 500);
        addFuel(50, 50, 1600, 300);

        Alien alien1 = new WanderingAlien(this, 800, 900, -90);
        Alien alien2 = new WanderingAlien(this, 500, 1200, 500);

        addEntity(alien1);
        addEntity(alien2);

        addAsteroid(50, EnumAsteroidVariant.ASTEROID_0, -800, 1100, -Math.PI / 10);
        addAsteroid(80, EnumAsteroidVariant.ASTEROID_2, -500, 1000, -Math.PI / 10);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_1, -300, 1200, -Math.PI / 28);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_0, 1000, 510, -Math.PI / 28);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_2, 1100, 300, Math.PI / 20);
        addAsteroid(150, EnumAsteroidVariant.ASTEROID_1, 800, 700, -Math.PI / 25);
        addAsteroid(100, EnumAsteroidVariant.ASTEROID_2, 600, 800, Math.PI / 25);
        addAsteroid(50, EnumAsteroidVariant.ASTEROID_0, 650, 700, -Math.PI / 10);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(40);
        Reactor basicReactor = new Reactor(5, 800_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

    private void addAsteroid(int radius, EnumAsteroidVariant variant, int x, int y, double v) {
        Asteroid asteroid = new Asteroid(this, radius, variant);
        asteroid.setPos(x, y);
        asteroid.setRotationSpeed(v);
        addEntity(asteroid);
    }

    private void addFuel(int size, int volume, int x, int y) {
        ItemFuel itemFuel = new ItemFuel(this, size, volume);
        itemFuel.setPos(x, y);
        addEntity(itemFuel);
    }

}
