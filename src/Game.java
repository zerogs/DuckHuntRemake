import java.util.LinkedList;
import java.util.List;

public class Game implements Runnable {

    /* fields */
    public GUI gui;
    private int points;
    public List<Duck> ducks = new LinkedList<>();
    private boolean state = true; // true means now playing, false means game over

    Game(){
        points = 0;
        gui = GUI.getInstance();
        Thread th = new Thread(this);
        th.start();
    }

    /* Singleton */
    private static Game instance;

    public static Game getInstance(){
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    @Override
    public void run(){
        while(state){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.repaint();
            if (Math.random() > 0.95) {
                ducks.add(new Duck());
            }

            for(Duck duck : ducks){
                if(duck.way){
                    duck.x += 10;
                }
                else{
                    duck.x -= 10;
                }

            }
        }
    }
}