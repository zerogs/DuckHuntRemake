package src;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DuckSprite {
    private int counter = 0;

    private BufferedImage spriteSheet;
    private BufferedImage[] topLeft;
    private BufferedImage[] left;
    private BufferedImage[] right;
    private BufferedImage[] topRight;
    private BufferedImage killed;

    public DuckSprite(){
        initializeStructures();
        loadResources();
    }

    private void loadResources() {
        String[] colors = {"red", "green", "blue"};
        try {
            spriteSheet = ImageIO.read(new File(Game.RESOURCES_PATH + colors[(int) (Math.random() * 3)] + "DuckSpriteSheet.png"));
            topLeft[0] = spriteSheet.getSubimage(89, 0, 32, 32);
            topLeft[1] = spriteSheet.getSubimage(134, 33, 32, 32);
            topLeft[2] = spriteSheet.getSubimage(199, 2, 32, 32);
            topRight[0] = spriteSheet.getSubimage(126, 1, 32, 32);
            topRight[1] = spriteSheet.getSubimage(95, 36, 32, 32);
            topRight[2] = spriteSheet.getSubimage(164, 1, 32, 32);
            left[0] = spriteSheet.getSubimage(363, 35, 32, 32);
            left[1] = spriteSheet.getSubimage(403, 35, 32, 32);
            left[2] = spriteSheet.getSubimage(443, 35, 32, 32);
            right[0] = spriteSheet.getSubimage(278, 0, 32, 32);
            right[1] = spriteSheet.getSubimage(321, 0, 32, 32);
            right[2] = spriteSheet.getSubimage(359, 0, 32, 32);
            killed = spriteSheet.getSubimage(4, 3 , 32, 32);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    private void initializeStructures() {

        topLeft = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];
        topRight = new BufferedImage[3];
        killed = new BufferedImage(32, 32 , 1);

    }

    public BufferedImage getImage(int direction, boolean Alive) {
        counter += 3;
        if (Alive) {
            switch (direction) {
                case 0:
                    return left[(counter / 10) % 3];
                case 1:
                    return right[(counter / 10) % 3];
                case 2:
                    return topLeft[(counter / 10) % 3];
                case 3:
                    return topRight[(counter / 10) % 3];
            }
            return null;
        }
        else{
            return killed;
        }
    }
}