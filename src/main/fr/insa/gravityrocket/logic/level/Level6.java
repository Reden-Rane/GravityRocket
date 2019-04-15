package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.Polygon2D;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level6 extends ReachingZoneLevel
{

    private final Image  earthTexture;
    private final Image  venusTexture;
    private final Image  uranusTexture;
    private final Planet earth;
    private final Planet venus;
    private final Planet uranus;


    public Level6() {
        super(RenderManager.loadImage("/textures/background_1.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.venusTexture = RenderManager.loadImage("/textures/star/venus.png", 200, 200);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 200, 200);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.venus = new Planet(this, "VÃ©nus", venusTexture, 2 * Math.pow(10, 10), 120, 1850, 1000);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 2 * Math.pow(10, 10), 150, 750, 400);

        this.earth.setRotationSpeed(Math.PI / 16);
        this.venus.setRotationSpeed(-Math.PI / 32);
        this.uranus.setRotationSpeed(Math.PI / 8);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon2D polygon = new Polygon2D();

        polygon.addPoint(-382.0f / 2 + 1000, -482.0f / 2 + 1050);
        polygon.addPoint(-92.0f / 2 + 1000, -384.0f / 2 + 1050);
        polygon.addPoint(152.0f / 2 + 1000, -244.0f / 2 + 1050);
        polygon.addPoint(290.0f / 2 + 1000, -172.0f / 2 + 1050);
        polygon.addPoint(384.0f / 2 + 1000, -202.0f / 2 + 1050);
        polygon.addPoint(646.0f / 2 + 1000, -408.0f / 2 + 1050);
        polygon.addPoint(1130.0f / 2 + 1000, 158.0f / 2 + 1050);
        polygon.addPoint(742.0f / 2 + 1000, 90.0f / 2 + 1050);
        polygon.addPoint(392.0f / 2 + 1000, 42.0f / 2 + 1050);
        polygon.addPoint(186.0f / 2 + 1000, 52.0f / 2 + 1050);
        polygon.addPoint(20.0f / 2 + 1000, -20.0f / 2 + 1050);
        polygon.addPoint(-936.0f / 2 + 1000, -200.0f / 2 + 1050);


        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        ItemFuel itemFuel = new ItemFuel(this, 50, 10);
        itemFuel.setPos(1020, 50);
        addEntity(itemFuel);

        ItemFuel itemFuel2 = new ItemFuel(this, 50, 10);
        itemFuel2.setPos(450, 750);
        addEntity(itemFuel2);

        addEntity(earth);
        addEntity(uranus);
        addEntity(venus);

        FuelTank basicTank    = new FuelTank(40);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }


}