package fr.insa.gravityrocket.graphics.renderer;

import fr.insa.gravityrocket.GravityRocket;

import java.awt.*;

public interface IRenderer<T>
{

    void render(T obj, Graphics2D g2d);

    default RenderManager getRenderManager() {
        return GravityRocket.getInstance().getGravityRocketView().getRenderManager();
    }

}
