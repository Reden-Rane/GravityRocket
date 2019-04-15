package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.Polygon2D;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level5 extends ReachingZoneLevel
{

    private final Image  earthTexture;
    private final Planet earth;

    public Level5() {
        super(RenderManager.loadImage("/textures/background_1.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.earth.setRotationSpeed(Math.PI / 16);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon2D polygon = new Polygon2D();

        polygon.addPoint(50.0f, 50.0f);
        polygon.addPoint(150.0f, 150.0f);
        polygon.addPoint(150.0f, 200.0f);
        polygon.addPoint(25.0f, 250.0f);

        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(earth);

        FuelTank basicTank    = new FuelTank(40);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}
