import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Admin on 19.11.2016.
 */
public class Duck extends JComponent{

    int x;
    int y;
    private boolean alive = true;
    boolean way;
    public static final int DUCK_WIDTH = 100;
    public static final int DUCK_HEIGHT = 100;
    public static BufferedImage duckImage = Game.initImage("duck.png", DUCK_WIDTH, DUCK_HEIGHT);

    Duck(){
        super();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Duck was clicked");
                dispose();
            }
        });
    }

    public void dispose() {
        if (isAlive()) {
            setAlive(false);
            this.repaint();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    /* Graphic methods */
    @Override
    protected void paintComponent(Graphics g){

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(this.isAlive()) {
            graphics2D.drawImage(duckImage, 0, 0, this);
            System.out.println("Duck was drawn");
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(DUCK_WIDTH, DUCK_HEIGHT);
    }
}