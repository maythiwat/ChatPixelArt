package info.demza.chatpixelart;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

public class PixelArtUtils {

    public static BufferedImage base64Image(String raw) throws PixelArtException {
        String[] split = raw.split(",");
        if (split.length == 2) {
            raw = split[1];
        }

        BufferedImage image = null;
        byte[] imageByte;

        try {
            imageByte = Base64.getDecoder().decode(raw);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            throw new PixelArtException("Incorrect base64 format");
        }

        return image;
    }

    public static BaseComponent[] bufferToComponent(BufferedImage image) throws PixelArtException {
        try {
            ComponentBuilder cb = new ComponentBuilder();

            int w = image.getWidth();
            int h = image.getHeight();

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    String hexColor = rgbToHex(new Color(image.getRGB(j, i)));
                    cb.append("█").color(ChatColor.of(hexColor));
                }
                if (i != (h - 1)) {
                    cb.append("\n");
                }
            }

            return cb.create();
        } catch (Exception e) {
            throw new PixelArtException("Invalid image");
        }
    }

    public static String rgbToHex(Color color) {
        return rgbToHex(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static String rgbToHex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }

}
