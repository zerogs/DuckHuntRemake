import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game{

    /* constants */
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final String RESOURCES_PATH = "./resources/";

    List<Duck> ducks = new LinkedList<>();

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

    public void duckWereKilled(Duck duck){
        ducks.remove(duck);
        gui.removeDuck(duck);
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
                game.gui.addDuck(duck);
            }

            //game.ducks.forEach(Duck::move);
            Iterator it = game.ducks.iterator();
            while (it.hasNext()){
                Duck duck = (Duck) it.next();
                duck.move();
            }

            game.gui.repaintAll();
        }
    }

}
