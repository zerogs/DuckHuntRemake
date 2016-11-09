import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Duck extends JComponent {

    int x;
    int y;
    boolean alive;
    boolean way;
    GUI gui;
    Game game;

    /* Singleton */

    private static class DuckHolder {
        private final static Duck instance = new Duck(Game.getInstance(), GUI.getInstance());
    }

    public static Duck getInstance(){
        return DuckHolder.instance;
    }

    Duck(Game game, GUI gui){

        super();
        this.game = game;
        this.gui = gui;

        Random random = ThreadLocalRandom.current();

        x = random.nextBoolean() ? -(GUI.DUCK_WIDTH + 1) : GUI.GAME_WIDTH + 1;
        y = random.nextInt((GUI.GAME_HEIGHT / 2) - GUI.DUCK_HEIGHT);
        way = random.nextBoolean();
        alive = true;

        enableInputMethods(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        System.out.println("duck was created @ " + Integer.toString(x) + "; " + Integer.toString(y));
    }

    private void dispose() {
        if (alive) {
            alive = false;
            game.ducks.remove(this);
            this.repaint();
        }
    }
}
