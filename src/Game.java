package src;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Game{

    /* constants */
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final String RESOURCES_PATH = "./resources/";
    static final String DUCK_FLY_S = "duckFly";
    static final String GAME_OVER_S = "gameOver";
    static final String START_GAME_S = "startGame";
    static final String NEW_HIGHSCORE = "newHighscore";
    static final String SHOT_SOUND = "shotSound";
    static final int MENU = 0;
    static final int IN_GAME = 1;
    static final int GAME_OVER = 2;
    static final int EASY = 0;
    static final int MEDIUM = 1;
    static final int HARD = 2;
    static final int NIGHTMARE = 3;
    boolean first = true;

    int[] highestScores = new int[4];


    int state = MENU;
    int tier = EASY;

    List<Duck> ducks = new LinkedList<>();
    int score = 0;
    GUI gui;


    Game() {
        initHighestScores();
        gui = new GUI(this);
        start();
    }

    static synchronized void playSound(final String url){
        new Thread(() -> {
            try{
                Clip clip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(
                        new File(RESOURCES_PATH + "/sounds/" + url + ".wav"));
                clip.open(ais);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
                e.printStackTrace();
            }
        }).start();
    }

    void start(){
        double birthChance;// = 0.98;
        switch (tier) {
            case MEDIUM:
                birthChance = 0.92;
                break;
            case HARD:
                birthChance = 0.89;
                break;
            case NIGHTMARE:
                birthChance = 0.85;
                break;
            default:
                birthChance = 0.95;
                break;
        }
        while (true) {
            while (state == IN_GAME) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                checkScore();

                if (score < -1000){
                    gameOver();
                }

                if (Math.random() > birthChance) {
                    Duck duck = new Duck(this);
                    ducks.add(duck);
                }
                for (Duck duck : ducks) {
                    if (duck.isAlive()) {
                        duck.move();
                    }
                }
                gui.repaintAll();
            }
            gui.repaintAll();
        }
    }

    void checkScore(){
        if(score > highestScores[tier]){
            highestScores[tier] = score;
            if(this.first){
                playSound(NEW_HIGHSCORE);
                this.first = false;
            }
        }
    }

    void killDucks(){
        ducks.stream().filter(Duck::isAlive).forEach(duck -> {
            duck.setAlive(false);
            duck.dyingTime = 0;
        });
    }

    void gameOver(){
        state = Game.GAME_OVER;
        playSound(GAME_OVER_S);
        killDucks();
        ducks.clear();
    }

    static void throwError(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    static BufferedImage initImage(String imageName, int width, int height) {
        Image duck = null;
        try {
            duck = ImageIO.read(new File(RESOURCES_PATH + imageName));
        } catch (IOException e) {
            throwError("Ошибка ресурсов");
        }

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(duck, 0, 0, width, height, null);
        g.dispose();
        return scaledImage;
    }

    int getScore(){
        return this.score;
    }

    String getTierName(){
        switch (tier){
            case EASY:
                return "Easy";
            case MEDIUM:
                return "Medium";
            case HARD:
                return "Hard";
            case NIGHTMARE:
                return "Nightmare";
            default:
                return "";
        }
    }

    void nextTier(){
        tier = ++tier % (NIGHTMARE + 1);
    }

    void initHighestScores(){
        for(int i = 0; i < highestScores.length; i++){
            highestScores[i] = 0;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RESOURCES_PATH + "data"))) {
            for(int i = 0; i < highestScores.length; i++){
                highestScores[i] = Integer.parseInt(br.readLine());
            }
        } catch (IOException ioe){}
    }

    void saveScores(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESOURCES_PATH + "data"))) {
            for(int i = 0; i < highestScores.length; i++){
                //highestScores[i] = Integer.parseInt(br.readLine());
                bw.write(Integer.toString(highestScores[i]) + '\n');
            }
        } catch (IOException ioe){}
    }

    public static void main(String[] args) {
        new Game();
    }
}
