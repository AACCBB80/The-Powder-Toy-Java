package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class LN2 extends Element {
    private static final Random random = new Random();
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        
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

        if (game.getHeatAt(x,y) > 0) {
            game.setElementAt(x,y, null);
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(133, 228, 255));
        g.fillRect(x, y, width, height);
    }
}