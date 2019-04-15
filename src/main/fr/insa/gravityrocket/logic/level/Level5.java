package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.Polygon2D;
import fr.insa.gravityrocket.logic.entity.Asteroid;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
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
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 7 * Math.pow(10, 9), 125, 1100, 800);
        this.mercury.setRotationSpeed(Math.PI / 8);
        this.uranusTexture = RenderManager.loadImage("/textures/star/uranus.png", 400, 400);
        this.uranus = new Planet(this, "Uranus", uranusTexture, 5 * Math.pow(10, 9), 200, 0, 400);
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

        FuelTank basicTank = new FuelTank(100);
        basicTank.setFuelVolume(40);
        Reactor basicReactor = new Reactor(5, 1_000_000);
        Rocket  rocket       = new Rocket(this, basicTank, basicReactor);

        ItemFuel itemFuel = new ItemFuel(this, 40, 5);
        itemFuel.setPos(100, 100);
        addEntity(itemFuel);

        ItemFuel itemFuel2 = new ItemFuel(this, 40, 7);
        itemFuel2.setPos(1100, 1000);
        addEntity(itemFuel2);

        Asteroid asteroid = new Asteroid(this, 200, EnumAsteroidVariant.ASTEROID_1);
        asteroid.setPos(1150, 530);
        asteroid.setRotationSpeed(-Math.PI / 28);
        addEntity(asteroid);

        Asteroid asteroid1 = new Asteroid(this, 120, EnumAsteroidVariant.ASTEROID_2);
        asteroid1.setPos(1600, 100);
        asteroid1.setRotationSpeed(Math.PI / 10);
        addEntity(asteroid1);

        Asteroid asteroid2 = new Asteroid(this, 175, EnumAsteroidVariant.ASTEROID_1);
        asteroid2.setPos(1600, -200);
        asteroid2.setRotationSpeed(-Math.PI / 15);
        addEntity(asteroid2);

        Asteroid asteroid3 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_1);
        asteroid3.setPos(-300, 600);
        asteroid3.setRotationSpeed(-Math.PI / 15);
        addEntity(asteroid3);

        Asteroid asteroid4 = new Asteroid(this, 240, EnumAsteroidVariant.ASTEROID_1);
        asteroid4.setPos(-700, 700);
        asteroid4.setRotationSpeed(-Math.PI / 5);
        addEntity(asteroid4);

        Asteroid asteroid5 = new Asteroid(this, 150, EnumAsteroidVariant.ASTEROID_1);
        asteroid5.setPos(-900, 1000);
        asteroid5.setRotationSpeed(-Math.PI / 10);
        addEntity(asteroid5);

        Asteroid asteroid6 = new Asteroid(this, 60, EnumAsteroidVariant.ASTEROID_1);
        asteroid6.setPos(1450, 300);
        asteroid6.setRotationSpeed(-Math.PI / 2);
        addEntity(asteroid6);

        Asteroid asteroid7 = new Asteroid(this, 60, EnumAsteroidVariant.ASTEROID_1);
        asteroid7.setPos(1350, 450);
        asteroid7.setRotationSpeed(-Math.PI / 2);
        addEntity(asteroid7);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}