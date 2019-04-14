package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Planet;
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
        super(RenderManager.loadImage("/textures/background_0.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3));
        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 100, 100);
        this.neptunTexture = RenderManager.loadImage("/textures/star/neptun.png", 750, 750);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 50, -500, -50);
        this.neptun = new Planet(this, "Neptune", neptunTexture, 2 * Math.pow(10, 8), 375, 1500, 750);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.neptun.setRotationSpeed(-Math.PI / 32);

        resetLevel();
    }

    @Override
    public void resetLevel() {
        super.resetLevel();
        addEntity(neptun);
        addEntity(earth);
        setTargetedPlanet(neptun);

        ItemFuel itemFuel = new ItemFuel(this, 40, 10);
        itemFuel.setPos(400, 300);
        addEntity(itemFuel);

        FuelTank basicTank    = new FuelTank(40);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);

        rocket.attachToPlanet(earth);
    }

}
