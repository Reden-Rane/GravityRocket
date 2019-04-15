package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.Polygon2D;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level5 extends ReachingZoneLevel
{

    private final Image  earthTexture;
    private final Image  mercuryTexture;
    private final Image  uranusTexture;
    private final Planet earth;
    private final Planet uranus;
    private final Planet mercury;

    public Level5() {
        super(RenderManager.loadImage("/textures/background_1.png", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, 1800, 1000);
        this.earth.setRotationSpeed(Math.PI / 16);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 250, 250);
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 2 * Math.pow(10, 9), 125, 1100, 800);
        this.mercury.setRotationSpeed(Math.PI / 8);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 400, 400);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 2 * Math.pow(10, 9), 200, 0, 400);
        this.uranus.setRotationSpeed(Math.PI / 20);
        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon2D verseau = new Polygon2D();

        verseau.addPoint(-500f, -300.0f);
        verseau.addPoint(-600f, -222f);
        verseau.addPoint(-700f, -170f);
        verseau.addPoint(-685f, -101f);
        verseau.addPoint(-720f, -90f);
        verseau.addPoint(-738f, -68f);
        verseau.addPoint(-659f, 61f);
        verseau.addPoint(-644f, -17f);
        verseau.addPoint(-586f, -26f);
        verseau.addPoint(-500f, 52f);
        return verseau;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        addEntity(earth);
        addEntity(mercury);
        addEntity(uranus);


        FuelTank basicTank    = new FuelTank(30);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        ItemFuel itemFuel = new ItemFuel(this, 40, 5);
        itemFuel.setPos(100, 100);
        addEntity(itemFuel);

        ItemFuel itemFuel2 = new ItemFuel(this, 40, 10);
        itemFuel2.setPos(1100, 1000);
        addEntity(itemFuel2);


        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}