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

    Duck(){
        super();
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
            Game.getInstance().ducks.remove(this);
            this.repaint();
        }
    }
}
