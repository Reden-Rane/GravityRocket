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
    /**
     * La table associative des types d'objets et de leur renderer
     */
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

    /**
     * Charge une image depuis les ressources
     *
     * @param filePath Le chemin vers l'image
     * @param width    La largeur de l'image
     * @param height   La hauteur de l'image
     *
     * @return L'instance de l'image redimensionnée
     */
    public static Image loadImage(String filePath, int width, int height) {
        try {
            return ImageIO.read(GravityRocket.class.getResource(filePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> void render(T obj, double x, double y, Graphics2D g2d) {
        this.render(obj, x, y, 0, 0, 0, g2d);
    }

    public <T> void render(T obj, double x, double y, double rotation, Graphics2D g2d) {
        this.render(obj, x, y, 0, 0, rotation, g2d);
    }

    /**
     * Charge une police d'écriture depuis les ressources
     *
     * @param filePath Le chemin vers la police
     * @param fontType Le type de police (ttf, otf, ...)
     *
     * @return L'instance de la police d'écriture
     */
    public static Font loadFont(String filePath, int fontType) {
        try {
            return Font.createFont(fontType, RenderManager.class.getResourceAsStream(filePath));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return DEFAULT_FONT;
    }

    /**
     * Applique une échelle à l'image et la dessine pour qu'elle prenne tout l'écran, utile pour dessiner le fond des
     * niveaux
     *
     * @param g      L'instance de Graphics
     * @param img    L'image à dessiner
     * @param width  La largeur de l'espace à remplir
     * @param height La hauteur de l'espace à remplir
     */
    public static void renderFittedImage(Graphics g, Image img, int width, int height) {
        double scaleWidth  = width / (double) img.getWidth(null);
        double scaleHeight = height / (double) img.getHeight(null);
        double scale       = Math.max(scaleHeight, scaleWidth);

        int imgWidth  = (int) (scale * img.getWidth(null));
        int imgHeight = (int) (scale * img.getHeight(null));

        g.drawImage(img, (width - imgWidth) / 2, (height - imgHeight) / 2, imgWidth, imgHeight, null);

    }

    /**
     * @param obj L'objet à dessiner
     * @param <T> Le type générique de l'objet à dessiner
     *
     * @return Le renderer associé au type d'objet
     */
    @SuppressWarnings("unchecked")
    private <T> IRenderer<T> getRenderer(T obj) {

        Class<?>     objType = obj.getClass();
        IRenderer<?> renderer;

        do {
            renderer = rendererMap.get(objType);
        } while (renderer == null && (objType = objType.getSuperclass()) != null);

        return (IRenderer<T>) renderer;
    }

    /**
     * Ajoute un renderer à la table associative
     *
     * @param objType  Le type d'objet associé au renderer
     * @param renderer Le renderer
     * @param <T>      Le type générique de l'objet associé au renderer
     */
    public <T> void registerRenderer(Class<T> objType, IRenderer<T> renderer) {
        this.rendererMap.put(objType, renderer);
    }

    public int getScreenWidth() {
        return this.gravityRocketView.getScreenWidth();
    }

    public int getScreenHeight() {
        return this.gravityRocketView.getScreenHeight();
    }

}
