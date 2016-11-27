import javax.swing.*;

/**
 * Created by aerof on 27.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}
