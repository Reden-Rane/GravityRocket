package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.Satellite;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Reactor;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public class Level8 extends ReachingZoneLevel
{

    private final Image sunTexture;
    private final Image earthTexture;
    private final Image neptunTexture;
    private final Image moonTexture;
    private final Image mercuryTexture;

    private final Planet    sun;
    private final Satellite earth;
    private final Satellite neptun;
    private final Satellite earthMoon;
    private final Satellite mercuryMoon;
    private final Satellite mercury;

    public Level8() {
        super("Constellation du Rhinocéros", GravityRocket.getInstance().getSoundHandler().musicPlayers[7], RenderManager.loadImage("/textures/background_7.jpg", 1920, 1080), new Rectangle(-750, -500, 1500 * 2, 1000 * 2), new Rectangle(-1500, -1000, 1500 * 3, 1000 * 3), createZoneShape());

        this.earthTexture = RenderManager.loadImage("/textures/star/earth.png", 160, 160);
        this.sunTexture = RenderManager.loadImage("/textures/star/sun.png", 400, 400);
        this.neptunTexture = RenderManager.loadImage("/textures/star/neptun.png", 400, 400);
        this.moonTexture = RenderManager.loadImage("/textures/star/moon.png", 40, 40);
        this.mercuryTexture = RenderManager.loadImage("/textures/star/mercury.png", 300, 300);

        this.sun = new Planet(this, "Soleil", sunTexture, 2 * Math.pow(10, 7), 200);
        this.sun.setRotationSpeed(Math.PI / 10);

        this.earth = new Satellite(this, "Terre", earthTexture, 7 * Math.pow(10, 7), 80, sun, 200, Math.PI / 12);
        this.earth.setRotationSpeed(Math.PI / 16);

        this.neptun = new Satellite(this, "Neptune", neptunTexture, 7 * Math.pow(10, 9), 200, sun, 700, Math.PI / 20);
        this.neptun.setRotationSpeed(Math.PI / 8);

        this.earthMoon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 7), 20, earth, 80, Math.PI / 4);

        this.mercury = new Satellite(this, "Mercure", mercuryTexture, 7 * Math.pow(10, 9), 150, sun, 700, Math.PI / 20);
        this.mercury.setRotationSpeed(Math.PI / 20);

        this.mercuryMoon = new Satellite(this, "Lune", moonTexture, 2 * Math.pow(10, 7), 40, mercury, 80, Math.PI / 8);

        resetLevel();
    }

    private static Shape createZoneShape() {
        Polygon polygon = new Polygon();

        polygon.addPoint(1225, 600);
        polygon.addPoint(1600, 600);
        polygon.addPoint(1600, 840);
        polygon.addPoint(1150, 840);
        polygon.addPoint(1100, 700);

        return polygon;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();

        earth.setOrbitalAngle(Math.PI);
        earthMoon.setOrbitalAngle(Math.PI);
        mercury.setOrbitalAngle(Math.PI * 2 * 0.8);
        neptun.setOrbitalAngle(0);

        addEntity(sun);
        addEntity(earth);
        addEntity(neptun);
        addEntity(earthMoon);
        addEntity(mercury);
        addEntity(mercuryMoon);

        FuelTank basicTank    = new FuelTank(40);
        Reactor  basicReactor = new Reactor(5, 800_000);
        Rocket   rocket       = new Rocket(this, basicTank, basicReactor);

        addEntity(rocket);
        rocket.attachToPlanet(earth);
    }


}
