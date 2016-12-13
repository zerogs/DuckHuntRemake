import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Game{

    /* constants */
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final String RESOURCES_PATH = "./resources/";

    public enum Direction{
        Left, Right, TopLeft, TopRight
    }

    static List<Duck> ducks = new LinkedList<>();
    int score = 0;
    GUI gui;


    Game() {
        gui = new GUI(this);
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

    int getScore(){
        return this.score;
    }

    public static void main(String[] args) {
        Game game = new Game();
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if(Math.random() > 0.97) {
                Duck duck = new Duck(game);
                game.ducks.add(duck);
            }
            for(Duck duck : ducks){
                if(duck.isAlive()) {
                    duck.move();
                }
            }
            game.gui.repaintAll();
        }
    }

}
