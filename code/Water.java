package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Water extends Element {
    private static final Random random = new Random();
    public Water() {
        weight = 4;
    }
    @Override
    public void update(FallingSandGame game, int x, int y) {
        // crappy water

        if (game.getHeatAt(x,y) > 200) {
            game.setElementAt(x,y, new Water_Vapor());
        }

        if (game.getHeatAt(x,y) < 30) {
            game.setElementAt(x,y, new Ice());
        }

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        }
        int rndInt = 0;
        while (rndInt == 0) {
            rndInt = random.nextInt(3) - 1;
        }

        if (game.isEmpty(x+rndInt, y)) {
            game.swapElements(x, y, x+rndInt, y);
        }
        rndInt = 0;
        while (rndInt == 0) {
            rndInt = random.nextInt(5) - 3;
        }
        if (game.isEmpty(x+rndInt, y + 1) || game.canPush(x, y, x+rndInt, y + 1)) {
            game.swapElements(x, y, x+rndInt, y + 1);
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}