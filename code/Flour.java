package thePowderToyJava;

import java.awt.*;
import java.util.Random;
import java.awt.Color;

public class Flour extends Element {
    double airRes = 1;
    public Flour() {
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

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Water) {
                    game.setElementAt(x,y,null);
                    game.setElementAt(x + dx, y + dy, new Batter());
                    break;
                } else if (neighbor instanceof Milk) {
                    game.setElementAt(x,y,null);
                    game.setElementAt(x + dx, y + dy, new Cake_Batter());
                    break;
                }
            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(253, 255, 219);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}