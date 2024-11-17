package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Gravel extends Element {
    public Gravel() {
        weight = 10;
    }

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        } else if (game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1) ||
                   game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                Random random = new Random();
                rndInt = random.nextInt(3) - 1;
            }
            if (game.isEmpty(x + rndInt, y + 1) || game.canPush(x, y, x + rndInt, y + 1)) {
                game.swapElements(x, y, x + rndInt, y + 1);
            }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(92, 92, 92));
        g.fillRect(x, y, width, height);
    }
}