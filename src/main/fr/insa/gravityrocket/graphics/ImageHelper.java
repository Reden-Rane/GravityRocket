package fr.insa.gravityrocket.graphics;

import fr.insa.gravityrocket.GravityRocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageHelper
{

    public static Image loadImage(String filePath, int width, int height) {
        try {
            return ImageIO.read(GravityRocket.class.getResource(filePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
