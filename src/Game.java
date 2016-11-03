import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Game implements Runnable{
    /* constants */
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final String RESOURCES_PATH = "./resources/";
    private static final int DUCK_WIDTH = 100;
    private static final int DUCK_HEIGHT = 100;
    private static BufferedImage duckImage = initImage("duck.png", DUCK_WIDTH, DUCK_HEIGHT);
    private static BufferedImage skyImage = initImage("sky.jpg", GAME_WIDTH-1, GAME_HEIGHT-1);

    /* fields */
    private GameWindow window;
    private int points;
    private List<Duck> ducks = new LinkedList<>();
    private boolean state = true; // true means now playing, false means game over

    /* singleton */
    private static Game game;

    private Game() {
        points = 0;
        window = new GameWindow();
        Thread th = new Thread(this);
        th.start();
    }

    private static void start() {
        game = new Game();
    }

    @Override
    public void run(){
        while (state) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            window.repaint();
            if (Math.random() > 0.95) {
                ducks.add(new Duck());
            }
        }
    }

    /*
        GUI class =================================================================================================
     */

    private class GameWindow extends JFrame {
        GameWindow() {
            initGUI();
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
            GamePanel(){
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
                for (Duck duck: ducks) {
                    graphics2D.drawImage(duckImage, null, duck.x, duck.y);
                }
            }
        }
    }

    /*
        Duck class =================================================================================================
     */

    private class Duck extends JComponent{
        int x;
        int y;
        boolean alive;
        boolean way;

        Duck(){
            super();
            Random random = ThreadLocalRandom.current();

            x = random.nextBoolean() ? -(DUCK_WIDTH + 1) : GAME_WIDTH + 1;
            y = random.nextInt((GAME_HEIGHT / 2) - DUCK_HEIGHT);
            way = random.nextBoolean();
            alive = true;

            enableInputMethods(true);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                }
            });
            System.out.println("dcuk was created @ " + Integer.toString(x) + "; " + Integer.toString(y));
        }

        private void dispose() {
            if (alive) {
                alive = false;
                game.ducks.remove(this);
                this.repaint();
            }
        } 

        public Dimension getPreferredSize() { return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);}
        public Dimension getMinimumSize() { return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);}
        public Dimension getMaximumSize() { return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);}
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

    public static void main(String[] args) {
        start();
    }
}
