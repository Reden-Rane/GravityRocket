package fr.insa.gravityrocket.graphics.renderer;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.graphics.GravityRocketView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RenderManager
{

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    public static final Font BEBAS_NEUE_FONT = loadFont("/fonts/BebasNeue-Regular.ttf", Font.TRUETYPE_FONT);

    private final GravityRocketView           gravityRocketView;
    private final Map<Class<?>, IRenderer<?>> rendererMap = new HashMap<>();

    public RenderManager(GravityRocketView gravityRocketView) {
        this.gravityRocketView = gravityRocketView;
    }

    public <T> void render(T obj, Graphics2D g2d) {
        this.render(obj, 0, 0, 0, 0, 0, g2d);
    }

    /**
     * Fait le rendu de l'objet à l'écran
     *
     * @param obj      L'objet à dessiner
     * @param x        La position en x de l'objet sur l'écran
     * @param y        La position en y de l'objet sur l'écran
     * @param pivotX   L'abscisse du pivot de l'objet (relatif à l'objet)
     * @param pivotY   L'ordonnée du pivot de l'objet (relatif à l'objet)
     * @param rotation La rotation de l'objet en radians
     * @param g2d      L'instance de Graphics2D permettant le rendu
     * @param <T>      Le type de l'objet
     */
    public <T> void render(T obj, double x, double y, double pivotX, double pivotY, double rotation, Graphics2D g2d) {
        if (obj != null) {
            IRenderer<T> renderer = getRenderer(obj);

            if (renderer != null) {
                AffineTransform prevTransform = g2d.getTransform();
                g2d.translate(x, y);
                g2d.translate(pivotX, pivotY);
                g2d.rotate(rotation);
                g2d.translate(-pivotX, -pivotY);
                renderer.render(obj, g2d);
                g2d.setTransform(prevTransform);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> IRenderer<T> getRenderer(T obj) {

        Class<?>     objType = obj.getClass();
        IRenderer<?> renderer;

        do {
            renderer = rendererMap.get(objType);
        } while (renderer == null && (objType = objType.getSuperclass()) != null);

        return (IRenderer<T>) renderer;
    }

    public <T> void render(T obj, double x, double y, Graphics2D g2d) {
        this.render(obj, x, y, 0, 0, 0, g2d);
    }

    public <T> void render(T obj, double x, double y, double rotation, Graphics2D g2d) {
        this.render(obj, x, y, 0, 0, rotation, g2d);
    }

    public <T> void registerRenderer(Class<T> objType, IRenderer<T> renderer) {
        this.rendererMap.put(objType, renderer);
    }

    public static Image loadImage(String filePath, int width, int height) {
        try {
            return ImageIO.read(GravityRocket.class.getResource(filePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Font loadFont(String filePath, int fontType) {
        try {
            return Font.createFont(fontType, RenderManager.class.getResourceAsStream(filePath));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return DEFAULT_FONT;
    }

    public int getScreenWidth() {
        return this.gravityRocketView.getCanvasWidth();
    }

    public int getScreenHeight() {
        return this.gravityRocketView.getCanvasHeight();
    }

}
