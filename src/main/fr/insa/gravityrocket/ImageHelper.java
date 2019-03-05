package fr.insa.gravityrocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageHelper
{

    public static Image loadImage(String filePath)
    {
        try
        {
            return ImageIO.read(GravityRocket.class.getResource(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
