import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 09.11.2016.
 */
public class GUI extends JFrame {
    /* constants */
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private static final String RESOURCES_PATH = "./resources/";
    public static final int DUCK_WIDTH = 100;
    public static final int DUCK_HEIGHT = 100;

    /* fields */
    private static BufferedImage duckImage = initImage("duck.png", DUCK_WIDTH, DUCK_HEIGHT);
    private static BufferedImage skyImage = initImage("sky.jpg", GAME_WIDTH - 1, GAME_HEIGHT - 1);

    private GUI() {
        initGUI();
    }

    /* Singleton */

    private static GUI instance;

    public static GUI getInstance(){
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    void initGUI() {
        this.setTitle("Ducks destroyed - 0");
        this.pack();
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(1, 1));
        this.add(new GamePanel());
        this.setVisible(true);
    }

    class GamePanel extends JPanel {
        GamePanel() {
            super();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(skyImage, null,  0, 0);
            System.out.println("we are painting the skies");
        }

        @Override
        protected void paintChildren(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (Duck duck: Game.getInstance().ducks) {
                graphics2D.drawImage(duckImage, null, duck.x, duck.y);
            }
        }
    }

    private static void throwError(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE); // TODO LINK OBJECT
        System.exit(1);
    }

    private static BufferedImage initImage(String imageName, int width, int height) {
        Image duck = null;
        try {
            duck = ImageIO.read(new File(RESOURCES_PATH + imageName));
        } catch (IOException e) {
            throwError("Ошибка ресурсов");
        }

        BufferedImage scaledImage = new BufferedImage(DUCK_WIDTH, DUCK_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(duck, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }

}
