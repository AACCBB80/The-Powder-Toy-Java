package thePowderToyJava;

import java.awt.*;
import java.util.Random;
import java.awt.Color;

public class Dust extends Element {
    double airRes = 1;
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        airRes = airRes + 0.5;
        if (game.isEmpty(x, y + (int)Math.floor(airRes % 2))) {
            game.swapElements(x, y, x, y + 1);
        } else if (game.isEmpty(x-1, y + 1) || game.isEmpty(x+1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                Random random = new Random();
                rndInt = random.nextInt(3) - 1;
            }

            game.swapElements(x, y, x+rndInt, y + (int)Math.floor(airRes % 2));
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(253, 255, 219);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}