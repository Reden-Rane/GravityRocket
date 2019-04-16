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

public class Level1 extends LandingLevel
{

    private final Image earthTexture;
    private final Image neptunTexture;

    private final Planet earth;
    private final Planet neptun;

    public Level1() {
        super(GravityRocket.getInstance().getSoundHandler().musicPlayers[0], RenderManager.loadImage("/textures/background_0.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 100, 100);
        this.neptunTexture = RenderManager.loadImage("/textures/star/neptun.png", 750, 750);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 80, -400, -50);
        this.neptun = new Planet(this, "Neptune", neptunTexture, 2 * Math.pow(10, 8), 375, 1500, 750);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.neptun.setRotationSpeed(-Math.PI / 32);

        this.setTargetedPlanetHaloSize(60);

        resetLevel();
    }

    @Override
    public void resetLevel() {
        super.resetLevel();
        addEntity(neptun);
        addEntity(earth);
        setTargetedPlanet(neptun);

        ItemFuel itemFuel  = new ItemFuel(this, 60, 10);
        ItemFuel itemFuel2 = new ItemFuel(this, 60, 10);

        itemFuel.setPos(50, 280);
        itemFuel2.setPos(650, 500);

        Alien alien1 = new WanderingAlien(this, 0, 900, -90);
        Alien alien2 = new WanderingAlien(this, 0, -100, 800);

        Asteroid asteroid1 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_1);
        Asteroid asteroid2 = new Asteroid(this, 70, EnumAsteroidVariant.ASTEROID_2);
        Asteroid asteroid3 = new Asteroid(this, 90, EnumAsteroidVariant.ASTEROID_2);
        Asteroid asteroid4 = new Asteroid(this, 130, EnumAsteroidVariant.ASTEROID_1);

        asteroid1.setPos(290, -10);
        asteroid2.setPos(750, 220);
        asteroid3.setPos(-260, 380);
        asteroid4.setPos(390, 840);

        asteroid1.setRotationSpeed(-Math.PI / 32);
        asteroid2.setRotationSpeed(-Math.PI / 16);
        asteroid3.setRotationSpeed(-Math.PI / 28);
        asteroid4.setRotationSpeed(-Math.PI / 24);

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(30);
        Reactor basicReactor = new Reactor(5, 1_000_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(itemFuel);
        addEntity(itemFuel2);

        addEntity(alien1);
        addEntity(alien2);

        addEntity(asteroid1);
        addEntity(asteroid2);
        addEntity(asteroid3);
        addEntity(asteroid4);


        addEntity(rocket);

        rocket.attachToPlanet(earth);
    }

}