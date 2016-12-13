import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GUI {
    JFrame window;
    JPanel panel;
    private Font scoreFont;
    private static BufferedImage backgroundImage = Game.initImage("background.png", Game.GAME_WIDTH - 1, Game.GAME_HEIGHT - 1);
    Game game;

    GUI(Game game){
        this.game = game;
        window = new JFrame();
        panel = new GamePanel();
        scoreFont = new Font("Arial", 10, 36);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("mouse clicked into " + Integer.toString(x) + " " + Integer.toString(y));
                for(Duck duck : game.ducks){
                    duck.duckGetShot(x, y);
                }
            }
        });
        window.setTitle("Duck Hunt");
        window.setSize(1280, 750);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.getContentPane().add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void repaintAll(){
        window.repaint();
    }

    class GamePanel extends JPanel{
        GamePanel(){
            super();
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(backgroundImage, null, 0 , 0);

            int score = game.getScore();
            graphics2D.setFont(scoreFont);
            graphics2D.drawString("Score: " + score, 0, 30);

            for(Duck duck : Game.ducks){
                if(duck.isAlive())
                    graphics2D.drawImage(duck.getImage(),duck.getX(), duck.getY(),Duck.DUCK_WIDTH, Duck.DUCK_HEIGHT, this);
            }
        }
    }

}
