package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Goop extends Element {
    private static final Random random = new Random();
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;
        if (!(random.nextInt(2) == 1)) {
            return;
        }
        
        if (game.isEmpty(x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        }
        int rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(3) - 1;
        }

        if (game.isEmpty(x+rndInt, y + 0)) {
            game.swapElements(x, y, x+rndInt, y + 0);
        }
        rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(5) - 3;
        }
        if (game.isEmpty(x+rndInt, y + 1)) {
            game.swapElements(x, y, x+rndInt, y + 1);
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(153, 64, 255));
        g.fillRect(x, y, width, height);
    }
}