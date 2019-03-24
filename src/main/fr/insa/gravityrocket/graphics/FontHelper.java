package fr.insa.gravityrocket.graphics;

import java.awt.*;
import java.io.IOException;

public class FontHelper
{

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    public static final Font BEBAS_NEUE_FONT = loadFont("/fonts/BebasNeue-Regular.ttf", Font.TRUETYPE_FONT);

    public static Font loadFont(String filePath, int fontType) {
        try {
            return Font.createFont(fontType, FontHelper.class.getResourceAsStream(filePath));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return DEFAULT_FONT;
    }

}
