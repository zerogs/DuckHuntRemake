package src;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class GUI {
    private JFrame window;
    private Font font;
    private static BufferedImage backgroundImage = Game.initImage("background.png",
            Game.GAME_WIDTH - 1, Game.GAME_HEIGHT - 1);
    private static BufferedImage logoImage = Game.initImage("Duck_Hunt_Logo.png",400, 250);
    private Game game;
    private JLabel scoreLabel, tierLabel, startLabel, menuLabel, logoLabel;
    private GamePanel panel;
    private JPanel menuPanel, gamePanel;


    GUI(Game game){
        this.game = game;
        window = new JFrame();
        panel = new GamePanel();

        try {
            BufferedImage image = ImageIO.read(new File(Game.RESOURCES_PATH + "aim.png"));
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    image,
                    new Point(15, 15), "");
            panel.setCursor(cursor);
        } catch (IOException e) {
            Game.throwError("Отсутствует спрайт прицела");
        }

        try {
            FileInputStream fis = new FileInputStream(new File(Game.RESOURCES_PATH + "PressStart2P.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, fis).deriveFont(30f);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, 50);
        }

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (game.state == Game.IN_GAME)
                    Game.playSound(Game.SHOT_SOUND);
                    for (Duck duck : game.ducks)
                        duck.clicked(e.getX(), e.getY());
            }
        });

        // Labels' settings
        ImageIcon icon = new ImageIcon(logoImage);
        logoLabel = new JLabel();
        logoLabel.setIcon(icon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        startLabel = new JLabel("Start game");
        startLabel.setFont(font);
        startLabel.setForeground(Color.black);

        tierLabel = new JLabel(game.getTierName());
        tierLabel.setFont(font);
        tierLabel.setForeground(Color.black);

        scoreLabel = new JLabel();
        scoreLabel.setForeground(Color.black);
        updateScore();
        scoreLabel.setFont(font);

        menuLabel = new JLabel("Menu");
        menuLabel.setFont(font);
        menuLabel.setForeground(Color.black);


        // Listeners
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                runGame();
                startLabel.setForeground(Color.black);
                Game.playSound(Game.START_GAME_S);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startLabel.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startLabel.setForeground(Color.black);
            }
        });

        tierLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                game.nextTier();
                game.checkScore();
                updateScore();
                tierLabel.setText(game.getTierName());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                tierLabel.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tierLabel.setForeground(Color.black);
            }
        });

        menuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                showMenu();
                game.first = true;
                menuLabel.setForeground(Color.black);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuLabel.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuLabel.setForeground(Color.black);
            }
        });

        // Layouts
        menuPanel = new JPanel(new GridLayout(1, 2));
        menuPanel.setOpaque(false);

        JPanel exPanel = new JPanel(new GridLayout(3, 1));
        exPanel.setOpaque(false);
        menuPanel.add(logoLabel);
        exPanel.add(startLabel);
        exPanel.add(tierLabel);
        menuPanel.add(exPanel);

        panel.setLayout(new GridBagLayout());
        panel.add(menuPanel);

        gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gamePanel.add(scoreLabel, gbc);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gamePanel.add(menuLabel, gbc);

        window.setTitle("Duck Hunt");
        window.setSize(1280, 750);
        window.setResizable(false);
        window.setLocationRelativeTo(null); // center the window
        window.getContentPane().add(panel);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.saveScores();
                System.exit(0);
            }
        });
        window.setVisible(true);
    }

    void repaintAll(){
        window.repaint();
    }

    void updateScore(){
        scoreLabel.setText("Score: " + Integer.toString(game.getScore()) +
                "(Highest: " + game.highestScores[game.tier] + ")");
    }

    void runGame(){
        panel.remove(menuPanel);
        game.state = Game.IN_GAME;
        panel.setLayout(new GridLayout(1, 1));
        panel.add(gamePanel);
        panel.revalidate();
    }

    void showMenu(){
        game.score = 0;
        updateScore();
        panel.remove(gamePanel);
        game.state = Game.MENU;
        panel.setLayout(new GridBagLayout());
        panel.add(menuPanel);
        panel.revalidate();
        game.killDucks();
        game.saveScores();
    }


    private class GamePanel extends JPanel{
        GamePanel(){
            super();
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.drawImage(backgroundImage, null, 0 , 0);
            graphics2D.setFont(font);

            if (game.state == Game.IN_GAME) {
                for (Duck duck : game.ducks) {
                    if (duck.isAlive())
                        graphics2D.drawImage(duck.getImage(), duck.getX(), duck.getY(),
                                Duck.DUCK_WIDTH, Duck.DUCK_HEIGHT, this);
                    else if (duck.dyingTime-- > 0) {
                        graphics2D.drawImage(duck.ducksprite.getImage(duck.direction, false),
                                duck.getX(), duck.getY(), Duck.DUCK_WIDTH, Duck.DUCK_HEIGHT, this);
                    }
                }
            } else if(game.state == Game.GAME_OVER){
                String gameOver = "Game Over";
                FontMetrics fm = this.getFontMetrics(font);
                int x = (this.getWidth() - fm.stringWidth(gameOver)) / 2;
                int y = ((this.getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                graphics2D.setColor(Color.black);
                graphics2D.drawString(gameOver, x, y);
            }
        }
    }
}

