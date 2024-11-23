package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Salt extends Element {
    public Salt() {
        weight = 5;
    }
    private Color customColor = new Color(217, 217, 217);

    @Override
    public void update(FallingSandGame game, int x, int y) {
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

for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Water) {
                    game.setElementAt(x,y,null);
                    game.setElementAt(x + dx, y + dy, new Salt_Water());
                    break;
                }
            }
        }


    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}