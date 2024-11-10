package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Neutron extends Element {
    private int life;

    public Neutron() {
        this.life = 300;
    }

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();
        //if (!(random.nextInt(2) == 1)) {
         //   return;
        //}

        //System.out.println(255.0 * (life / 100.0) + " " + life / 100.0);
        if (life == 0) {
            game.setElementAt(x, y, null);
            game.addHeatAt(x,y,70);
        } else {
            life--;
        }

        switch (random.nextInt(8)) {
        case 0:
            if (game.isEmpty(x - 1, y - 1)) {
                game.swapElements(x, y, x - 1, y - 1);
            }
            break;
        case 1:
            if (game.isEmpty(x, y - 1)) {
                game.swapElements(x, y, x, y - 1);
                break;
            }
        case 2:
            if (game.isEmpty(x + 1, y - 1)) {
                game.swapElements(x, y, x + 1, y - 1);
                break;
            }
        case 3:
            if (game.isEmpty(x - 1, y)) {
                game.swapElements(x, y, x - 1, y);
                break;
            }
        case 4:
            if (game.isEmpty(x, y)) {
                game.swapElements(x, y, x, y);
                break;
            }
        case 5:
            if (game.isEmpty(x + 1, y)) {
                game.swapElements(x, y, x + 1, y);
                break;
            }
        case 6:
            if (game.isEmpty(x - 1, y+1)) {
                game.swapElements(x, y, x - 1, y+1);
                break;
            }
        case 7:
            if (game.isEmpty(x, y+1)) {
                game.swapElements(x, y, x, y+1);
                break;
            }
        case 8:
            if (game.isEmpty(x + 1, y+1)) {
                game.swapElements(x, y, x + 1, y+1);
                break;
            }
        }
        
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(161, 255, 238));
        g.fillRect(x, y, width, height);
    }
}