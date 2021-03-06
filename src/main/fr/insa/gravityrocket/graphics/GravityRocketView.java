package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.controller.KeyboardHandler;
import fr.insa.gravityrocket.controller.MouseHandler;
import fr.insa.gravityrocket.graphics.interfaces.MainWindow;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.graphics.renderer.collision.CircularCollisionBoxRenderer;
import fr.insa.gravityrocket.graphics.renderer.collision.RectangularCollisionBoxRenderer;
import fr.insa.gravityrocket.graphics.renderer.entity.*;
import fr.insa.gravityrocket.graphics.renderer.level.*;
import fr.insa.gravityrocket.logic.GravityRocketModel;
import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;
import fr.insa.gravityrocket.logic.collision.RectangularCollisionBox;
import fr.insa.gravityrocket.logic.entity.Asteroid;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
import fr.insa.gravityrocket.logic.entity.particle.Explosion;
import fr.insa.gravityrocket.logic.entity.particle.Laser;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.logic.level.*;

import javax.swing.*;

/**
 * Gestionnaire de la partie graphique du jeu
 */
public class GravityRocketView
{

    /**
     * La référence à la partie logique du jeu qui doit être dessinée
     */
    private final GravityRocketModel gravityRocketModel;

    /**
     * Le gestionnaire de rendu des niveaux
     */
    private final RenderManager renderManager;

    /**
     * La fenêtre principale de l'IHM
     */
    private final MainWindow mainWindow;

    public GravityRocketView(GravityRocketModel gravityRocketModel, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        this.gravityRocketModel = gravityRocketModel;
        this.renderManager = new RenderManager(this);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.mainWindow = new MainWindow(this.renderManager, 1500, 1000, keyboardHandler, mouseHandler);
        this.registerRenderers();
    }

    /**
     * On met les IRenderer associé à un type de classes précises dans un registre (table associative) Quand un objet
     * doit être rendu, la table associative renvoie ainsi le bon renderer et appellera la méthode render()
     */
    private void registerRenderers() {
        this.renderManager.registerRenderer(LandingLevel.class, new LandingLevelRenderer());
        this.renderManager.registerRenderer(ReachingZoneLevel.class, new ReachingZoneLevelRenderer<>());
        this.renderManager.registerRenderer(Level5.class, new Level5Renderer());
        this.renderManager.registerRenderer(Level6.class, new Level6Renderer());
        this.renderManager.registerRenderer(Level7.class, new Level7Renderer());
        this.renderManager.registerRenderer(Level8.class, new Level8Renderer());

        this.renderManager.registerRenderer(Planet.class, new PlanetRenderer());
        this.renderManager.registerRenderer(Rocket.class, new RocketRenderer());
        this.renderManager.registerRenderer(Asteroid.class, new AsteroidRenderer());
        this.renderManager.registerRenderer(Alien.class, new AlienRenderer());

        this.renderManager.registerRenderer(RectangularCollisionBox.class, new RectangularCollisionBoxRenderer());
        this.renderManager.registerRenderer(CircularCollisionBox.class, new CircularCollisionBoxRenderer());

        this.renderManager.registerRenderer(ItemFuel.class, new ItemFuelRenderer());

        this.renderManager.registerRenderer(Explosion.class, new ExplosionRenderer());
        this.renderManager.registerRenderer(Laser.class, new LaserRenderer());
    }

    public void render() {
        this.mainWindow.repaint();
    }

    public GravityRocketModel getGravityRocketModel() {
        return gravityRocketModel;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public int getScreenWidth() {
        return this.mainWindow.getContentPane().getWidth();
    }

    public int getScreenHeight() {
        return this.mainWindow.getContentPane().getHeight();
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

}
