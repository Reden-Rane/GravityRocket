package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.graphics.renderer.LevelRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.graphics.renderer.collision.CircularCollisionBoxRenderer;
import fr.insa.gravityrocket.graphics.renderer.collision.RectangularCollisionBoxRenderer;
import fr.insa.gravityrocket.graphics.renderer.entity.PlanetRenderer;
import fr.insa.gravityrocket.graphics.renderer.entity.RocketRenderer;
import fr.insa.gravityrocket.logic.GameModel;
import fr.insa.gravityrocket.logic.Level;
import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;
import fr.insa.gravityrocket.logic.collision.RectangularCollisionBox;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.logic.input.KeyboardHandler;
import fr.insa.gravityrocket.logic.input.MouseHandler;

import static fr.insa.gravityrocket.GravityRocket.GAME_TITLE;

public class GameView
{

    public static final int WINDOW_WIDTH  = 1500;
    public static final int WINDOW_HEIGHT = 1000;

    private final GameModel gameModel;

    private final RenderManager renderManager;
    private final GameWindow    gameWindow;

    public GameView(GameModel gameModel, KeyboardHandler keyboardHandler, MouseHandler mouseHandler) {
        this.gameModel = gameModel;
        this.renderManager = new RenderManager();
        this.gameWindow = new GameWindow(this.renderManager, GAME_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, keyboardHandler, mouseHandler);
        this.registerRenderers();
    }

    private void registerRenderers() {
        this.renderManager.registerRenderer(Level.class, new LevelRenderer());
        this.renderManager.registerRenderer(Planet.class, new PlanetRenderer());
        this.renderManager.registerRenderer(Rocket.class, new RocketRenderer());

        this.renderManager.registerRenderer(RectangularCollisionBox.class, new RectangularCollisionBoxRenderer());
        this.renderManager.registerRenderer(CircularCollisionBox.class, new CircularCollisionBoxRenderer());
    }

    public void render() {
        this.gameWindow.render();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

}
