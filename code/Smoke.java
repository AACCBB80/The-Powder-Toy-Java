package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Smoke extends Element {
    private int life;

    public Smoke() {
        this.life = 50;
    }

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();
        if (!(random.nextInt(2) == 1)) {
            return;
        }

        //System.out.println(255.0 * (life / 100.0) + " " + life / 100.0);
        if (life == 0) {
            game.setElementAt(x, y, null);
        } else {
            life--;
        }

        // Check if there's an element above the clone
        switch (random.nextInt(5)) {
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
            if (game.isEmpty(x + 1, y)) {
                game.swapElements(x, y, x + 1, y);
                break;
            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(48, 48, 48));
        g.fillRect(x, y, width, height);
    }
}