package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
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


        //il faut rajouter des aliens et des astéroides
        //https://www.google.fr/search?q=asteroid+flat&rlz=1C1GCEV_enFR844FR844&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjI2MPNsMPhAhUPz4UKHclOAboQ_AUIDigB&biw=1680&bih=907#imgrc=w5ckgqOiZjYjgM:
        //https://www.vectorstock.com/royalty-free-vector/flat-icon-design-collection-flying-saucer-and-vector-16364095

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 80, 80);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 200, 200);
        this.sunTexture = RenderManager.loadImage("/textures/star/sun.png", 500, 500);
        this.jupiterTexture = RenderManager.loadImage("/textures/star/jupiter.png", 250, 250);

        this.earth = new Planet(this, "Terre", earthTexture, 2 * Math.pow(10, 7), 40, -600, -200);
        this.mercury = new Planet(this, "Mercure", mercuryTexture, 2 * Math.pow(10, 7), 100, 1700, 850);
        this.sun = new Planet(this, "Soleil", sunTexture, 2 * Math.pow(10, 9), 250, 1400, -100);
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

        ItemFuel itemFuel = new ItemFuel(this, 40, 10);
        itemFuel.setPos(400, 300);
        addEntity(itemFuel);

        ItemFuel itemFuel2 = new ItemFuel(this, 40, 20);
        itemFuel2.setPos(900, 500);
        addEntity(itemFuel2);

        ItemFuel itemFuel3 = new ItemFuel(this, 40, 20);
        itemFuel.setPos(1900, 800);
        addEntity(itemFuel3);

        FuelTank basicTank    = new FuelTank(40);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }

}
