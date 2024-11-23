package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Dirt extends Element {
    public Dirt() {
        weight = 3;
    }
    private Color customColor = new Color(143, 108, 13);

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (game.isEmpty(x+1, y-1) || game.isEmpty(x, y-1) || game.isEmpty(x-1, y-1) ) {
        customColor = new Color(83, 181, 85);
        } else {
customColor = new Color(143, 108, 13);
        }
        if (!game.isRunning()) return;

        Random random = new Random();

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        } else if (game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1) || 
                   game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                rndInt = random.nextInt(3) - 1;
            }
            if (game.isEmpty(x + rndInt, y + 1) || game.canPush(x, y, x + rndInt, y + 1)) {
                game.swapElements(x, y, x + rndInt, y + 1);
            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}