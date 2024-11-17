package thePowderToyJava;

import java.awt.*;
import java.util.Random;
import java.awt.Color;

public class Dust extends Element {
    double airRes = 1;
    public Dust() {
        weight = 2;
    }
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        airRes = airRes + 0.5;
        Element elementBelow = game.getElementAt(x, y + 1);
        if ((game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) &&
                (elementBelow == null || elementBelow.getWeight() != -1)) {
            game.swapElements(x, y, x, y + 1);
        } else if ((game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1) ||
                game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) &&
                (elementBelow == null || elementBelow.getWeight() != -1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                Random random = new Random();
                rndInt = random.nextInt(3) - 1;
            }
            game.swapElements(x, y, x + rndInt, y + 1);
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(253, 255, 219);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}