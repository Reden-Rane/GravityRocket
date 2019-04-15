package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
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
        super(RenderManager.loadImage("/textures/background_3.png", 1920, 1080), new Rectangle(-900, -650, 1650 * 2, 1150 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 200, 200);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 60, 60);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        this.earth = new Planet(this, "Terre", earthTexture, 1 * Math.pow(10, 9), 150, 2000, 1200);
        this.moon = new Satellite(this, "Lune", moonTexture, earth, 75, Math.PI / 16, 2 * Math.pow(10, 10), 20);
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

        ItemFuel itemFuel1 = new ItemFuel(this, 50, 10);
        ItemFuel itemFuel2 = new ItemFuel(this, 50, 10);
        ItemFuel itemFuel3 = new ItemFuel(this, 50, 10);
        ItemFuel itemFuel4 = new ItemFuel(this, 50, 10);

        itemFuel1.setPos(-200, 200);
        itemFuel2.setPos(1700, 200);
        itemFuel3.setPos(-200, 800);
        itemFuel4.setPos(1700, 800);

        addEntity(itemFuel1);
        addEntity(itemFuel2);
        addEntity(itemFuel3);
        addEntity(itemFuel4);


        FuelTank basicTank    = new FuelTank(100);
        Reactor  basicReactor = new Reactor(4, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(uranus);
    }

}
