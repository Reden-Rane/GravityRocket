package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Asteroid;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
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
        super(RenderManager.loadImage("/textures/background_2.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

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

        Asteroid asteroid = new Asteroid(this, 50, EnumAsteroidVariant.ASTEROID_0);
        asteroid.setPos(1000, 510);
        asteroid.setRotationSpeed(-Math.PI / 28);
        addEntity(asteroid);

        Asteroid asteroid1 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_2);
        asteroid1.setPos(1100, 300);
        asteroid1.setRotationSpeed(Math.PI / 20);
        addEntity(asteroid1);

        Asteroid asteroid2 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_1);
        asteroid2.setPos(800, 700);
        asteroid2.setRotationSpeed(-Math.PI / 25);
        addEntity(asteroid2);

        Asteroid asteroid3 = new Asteroid(this, 100, EnumAsteroidVariant.ASTEROID_2);
        asteroid3.setPos(600, 800);
        asteroid3.setRotationSpeed(Math.PI / 25);
        addEntity(asteroid3);

        Asteroid asteroid4 = new Asteroid(this, 50, EnumAsteroidVariant.ASTEROID_0);
        asteroid4.setPos(650, 700);
        asteroid4.setRotationSpeed(-Math.PI / 10);
        addEntity(asteroid4);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(40);
        Reactor basicReactor = new Reactor(5, 800_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

    private void addFuel(int size, int volume, int x, int y) {
        ItemFuel itemFuel = new ItemFuel(this, size, volume);
        itemFuel.setPos(x, y);
        addEntity(itemFuel);
    }

}
