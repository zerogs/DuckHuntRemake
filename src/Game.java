import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 19.11.2016.
 */
public class Game {

    /* constants */
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final String RESOURCES_PATH = "./resources/";


    public static void main(String[] args) {
        new GUI();
    }

    private static void throwError(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE); // TODO LINK OBJECT
        System.exit(1);
    }

    public static BufferedImage initImage(String imageName, int width, int height) {
        Image duck = null;
        try {
            duck = ImageIO.read(new File(RESOURCES_PATH + imageName));
        } catch (IOException e) {
            throwError("Ошибка ресурсов");
        }

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(duck, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }
}
