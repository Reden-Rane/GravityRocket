package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.Level;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class LevelCanvas extends Canvas
{

    private static final Color SPACE_COLOR = new Color(14, 21, 33);

    public void render(Level level) {

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
        } else {
            Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
            g2d.clearRect(0, 0, getWidth(), getHeight());

            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            g2d.setColor(SPACE_COLOR);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

            if (level != null) {
                level.render(g2d);
            }

            g2d.dispose();
            bs.show();
        }
    }

}
