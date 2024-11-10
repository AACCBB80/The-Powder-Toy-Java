package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Water_Vapor extends Element {

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();
        if (!(random.nextInt(2) == 1)) {
            return;
        }

        //System.out.println(255.0 * (life / 100.0) + " " + life / 100.0);

if (game.getHeatAt(x,y) < 199) {
            game.setElementAt(x,y, new Water());
        }
        switch (random.nextInt(5)) { // 5 means 0-4
        case 0:
            if (game.isEmpty(x - 1, y - 1) || game.getElementAt(x - 1, y - 1) instanceof Water) {
                game.swapElements(x, y, x - 1, y - 1);
            }
            break;
        case 1:
            if (game.isEmpty(x, y - 1) || game.getElementAt(x, y - 1) instanceof Water) {
                game.swapElements(x, y, x, y - 1);
                break;
            }
        case 2:
            if (game.isEmpty(x + 1, y - 1) || game.getElementAt(x + 1, y - 1) instanceof Water) {
                game.swapElements(x, y, x + 1, y - 1);
                break;
            }
        case 3:
            if (game.isEmpty(x - 1, y) || game.getElementAt(x - 1, y) instanceof Water) {
                game.swapElements(x, y, x - 1, y);
                break;
            }
        case 4:
            if (game.isEmpty(x + 1, y) || game.getElementAt(x + 1, y) instanceof Water) {
                game.swapElements(x, y, x + 1, y);
                break;
            }
        }


        
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(161, 185, 212));
        g.fillRect(x, y, width, height);
    }
}