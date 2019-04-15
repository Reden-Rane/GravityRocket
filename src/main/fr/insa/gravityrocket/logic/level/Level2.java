package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
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
        super(RenderManager.loadImage("/textures/background_1.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 200, 200);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 40, 40);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 6), 120, -200, -200);
        this.moon = new Satellite(this, "Lune", moonTexture, earth, 100, Math.PI / 16, 2 * Math.pow(10, 6), 30);
        this.venus = new Planet(this, "Vénus", venusTexture, 2 * Math.pow(10, 6), 120, 1850, 1000);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 2 * Math.pow(10, 10), 150, 750, 400);

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

        ItemFuel itemFuel = new ItemFuel(this, 50, 10);
        itemFuel.setPos(1020, 50);
        addEntity(itemFuel);

        ItemFuel itemFuel2 = new ItemFuel(this, 50, 10);
        itemFuel2.setPos(450, 750);
        addEntity(itemFuel2);

        FuelTank basicTank    = new FuelTank(150);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);

        rocket.attachToPlanet(earth);
    }

}
