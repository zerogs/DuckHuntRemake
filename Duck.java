import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Duck{
    int x;
    int y;
    boolean way;
    private final boolean RIGHT = true;
    private boolean alive = true;
    public static final int DUCK_WIDTH = 110;
    public static final int DUCK_HEIGHT = 110;
    private final int DUCK_RADIUS_SQUARE = 55 * 55 ;
    public static final int DUCK_CENTER = 55;
    private DuckSprite ducksprite;
    public boolean isShotDown;
    Game game;

    Duck(Game game){
        super();
        this.game = game;
        Random random = ThreadLocalRandom.current();
        way = random.nextBoolean();
        y = random.nextInt((int) (Game.GAME_HEIGHT * 0.75));
        x = way == RIGHT ? -DUCK_WIDTH : Game.GAME_WIDTH;
        ducksprite = new DuckSprite();

    }

    public void move(){
        x += way == RIGHT ? 10 : -10;

        if ((way == RIGHT && x > Game.GAME_WIDTH) || (way != RIGHT && x + DUCK_WIDTH < 0)) {
            this.setAlive(false);
            game.score -= 100;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void duckGetShot(int xShot, int yShot){
        if(((Math.pow((xShot - (x + DUCK_CENTER)), 2)) + (Math.pow((yShot - (y + DUCK_CENTER)), 2))) < DUCK_RADIUS_SQUARE){
            this.setAlive(false);
            game.score += 100;
            //println("Duck center at " + Integer.toString(this.getX())+ " " + Integer.toString(this.getY()));
        }

    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public static void println(Object o){
        System.out.println(o);
    }

    public BufferedImage getImage(){
        Game.Direction dir;
        if(way)
            dir = Game.Direction.Right;
        else
            dir = Game.Direction.Left;

        return ducksprite.getImage(dir);

    }
}