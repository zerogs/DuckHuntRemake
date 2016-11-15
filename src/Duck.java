import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Duck extends JComponent {

    int x;
    int y;
    private boolean alive;
    boolean way;

    Duck(){
        super();
        Random random = ThreadLocalRandom.current();

        x = random.nextBoolean() ? -(GUI.DUCK_WIDTH + 1) : GUI.GAME_WIDTH + 1;
        y = random.nextInt((GUI.GAME_HEIGHT / 2) - GUI.DUCK_HEIGHT);
        way = random.nextBoolean();
        setAlive(true);


        enableInputMethods(true);
       addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse was Clicked");
                dispose();
            }
        });
    }

    private void dispose() {
        if (isAlive()) {
            setAlive(false);
            Game.getInstance().ducks.remove(this);
            this.repaint();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}



