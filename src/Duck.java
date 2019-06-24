package src;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Duck{

    /* Duck parameters */
    int x;
    int y;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int TOP_LEFT = 2;
    private static final int TOP_RIGHT = 3;
    int direction;
    private boolean alive = true;
    public DuckSprite ducksprite;
    private boolean justKilled = false;
    private static final int INCREDIBLE_SPEED = 30;
    private static final int FAST_SPEED = 20;
    private static final int MEDIUM_SPEED = 15;
    private static final int LOW_SPEED = 10;
    private int currentSpeed = LOW_SPEED;
    public int dyingTime = 10;

    /* Other constants */
    public static final int DUCK_WIDTH = 110;
    public static final int DUCK_HEIGHT = 110;
    private final int DUCK_RADIUS_SQUARE = 55 * 55 ;
    public static final int DUCK_CENTER = 55;
    private static Random random = ThreadLocalRandom.current();
    Game game;


    Duck(Game game){
        super();
        this.game = game;
        Game.playSound(Game.DUCK_FLY_S);
        direction = random.nextInt(2);
        y = random.nextInt((int) (Game.GAME_HEIGHT * 0.75));
        x = direction == RIGHT ? -DUCK_WIDTH : Game.GAME_WIDTH;
        ducksprite = new DuckSprite();

        if(this.game.tier == game.EASY){
            this.currentSpeed = LOW_SPEED;
        }
        else if(this.game.tier == game.MEDIUM){
            this.currentSpeed = MEDIUM_SPEED;
        }
        else if(this.game.tier == game.HARD){
            this.currentSpeed = FAST_SPEED;
        }
        else if(this.game.tier == game.NIGHTMARE){
            this.currentSpeed = INCREDIBLE_SPEED;
        }


    }

    public void move(){
        if(this.isAlive()) {
            if (Math.random() > 0.97) { // turn around
                if ((direction == RIGHT && x > 3 * Game.GAME_WIDTH / 4) ||
                        (direction == LEFT && x < (Game.GAME_WIDTH / 4))) {
                    direction = random.nextInt(2) + 2; // cause top left or top right is 2 or 3 as value
                }
            }

            switch (direction) {
                case RIGHT:
                    x += currentSpeed;
                    break;
                case LEFT:
                    x -= currentSpeed;
                    break;
                case TOP_RIGHT:
                    x += currentSpeed;
                    y -= currentSpeed;
                    break;
                case TOP_LEFT:
                    x -= currentSpeed;
                    y -= currentSpeed;
            }

            if (((direction == RIGHT || direction == TOP_RIGHT) && x > Game.GAME_WIDTH) ||
                    ((direction == LEFT || direction == TOP_LEFT) && x + DUCK_WIDTH < 0)){
                this.setAlive(false);
                game.score -= 100;
                game.gui.updateScore();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void clicked(int xShot, int yShot){
        if(this.isAlive()) {
            if (((Math.pow((xShot - (x + DUCK_CENTER)), 2)) + (Math.pow((yShot - (y + DUCK_CENTER)), 2))) < DUCK_RADIUS_SQUARE) {
                this.setAlive(false);
                this.justKilled = true;
                game.score += 100;
                game.checkScore();
                game.gui.updateScore();
            }
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public BufferedImage getImage(){
        return ducksprite.getImage(this.direction, this.isAlive());

    }
}