import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Duck extends JComponent{
    int x;
    int y;
    boolean way;
    private final boolean RIGHT = true;
    private boolean alive = true;
    private final int DUCK_WIDTH = 100;
    private final int DUCK_HEIGHT = 100;
    private BufferedImage duckImage = Game.initImage("duck.png", DUCK_WIDTH, DUCK_HEIGHT);
    Game game;

    Duck(Game game){
        super();

        this.game = game;

        Random random = ThreadLocalRandom.current();
        way = random.nextBoolean();
        y = random.nextInt((int) (Game.GAME_HEIGHT * 0.75));
        x = way == RIGHT ? -DUCK_WIDTH : Game.GAME_WIDTH;


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(alive)
                    dispose(true);
            }
        });

        setBounds(x, y, DUCK_WIDTH, DUCK_HEIGHT);

    }

    public void dispose(boolean killed) {
        setAlive(false);
        this.repaint();

        game.duckWereKilled(this);
        // TODO SEND KILLED FLAG TO PLAYER'S SCORE

        println("We have a " + Integer.toString(game.ducks.size()) + " ducks in ducks list now");
    }

    public void move(){
        x += way == RIGHT ? 10 : -10;
        setBounds(x, y, DUCK_WIDTH, DUCK_HEIGHT);

        if ((way == RIGHT && x > Game.GAME_WIDTH) || (way != RIGHT && x + DUCK_WIDTH < 0)) {
            dispose(true);
        }
//        if (way == RIGHT) {
//            println("Вылетели за правую границу");
//            if (x > Game.GAME_WIDTH){
//                println("Вылетели за правую границу");
//                dispose(true);
//            }
//        } else {
//            if (x + DUCK_WIDTH < 0) {
//                println("Вылетели за левую границу");
//                dispose(true);
//            }
//        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(this.isAlive()) {
            graphics2D.drawImage(duckImage, 0, 0, this);
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

    public static void println(Object o){
        System.out.println(o);
    }
}