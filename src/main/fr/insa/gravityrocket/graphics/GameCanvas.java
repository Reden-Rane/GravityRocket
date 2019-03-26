package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.Level;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas
{

    private final RenderManager renderManager;

    public GameCanvas(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public void render(Level level) {

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
        } else {
            Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
            //On efface l'écran
            g2d.clearRect(0, 0, getWidth(), getHeight());

            setupRenderHints(g2d);
            this.renderManager.render(level, g2d);

            g2d.dispose();
            bs.show();
        }
    }

    private void setupRenderHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

}
