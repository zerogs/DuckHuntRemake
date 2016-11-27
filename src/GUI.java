import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {
    JFrame window;
    JPanel panel;
    private static BufferedImage skyImage = Game.initImage("sky.jpg", Game.GAME_WIDTH - 1, Game.GAME_HEIGHT - 1);
    Game game;

    GUI(Game game){
        this.game = game;
        window = new JFrame();
        panel = new GamePanel();

        window.setSize(1280, 720);
        window.setTitle("Ducks destroyed - 0");


        window.getContentPane().add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void repaintAll(){
        window.repaint();
    }

    public void addDuck(Duck duck){
        panel.add(duck);
    }

    public void removeDuck(Duck duck){
        panel.remove(duck);
    }

    class GamePanel extends JPanel{
        GamePanel(){
            super();
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(skyImage, null, 0 , 0);
        }
    }

}