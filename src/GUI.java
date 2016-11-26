import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Admin on 19.11.2016.
 */
public class GUI {

    JFrame window;
    Duck duck;
    JPanel panel;
    private static BufferedImage skyImage = Game.initImage("sky.jpg", Game.GAME_WIDTH - 1, Game.GAME_HEIGHT - 1);

    GUI(){
        window = new JFrame();
        duck = new Duck();
        panel = new GamePanel();

        window.setSize(1280, 720);
        window.setTitle("Ducks destroyed - 0");
        panel.add(duck);
        window.getContentPane().add(panel);
        window.setVisible(true);
    }

    class GamePanel extends JPanel{
        GamePanel(){
            super();
            this.setLayout(new FlowLayout());
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(skyImage, null, 0 , 0);
        }
    }

}