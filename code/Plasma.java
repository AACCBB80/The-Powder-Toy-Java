package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Plasma extends Element {
    private int life;

    public Plasma() {
        this.life = 100; weight = 0;
    }

    private Color customColor = new Color(187, 138, 255);

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();
        if (!(random.nextInt(2) == 1)) {
            return;
        }
        game.addHeatAt(x, y, 2);
        game.addPressureAt(x, y, 100);
        customColor = new Color((int)Math.min((187.0 * (life / 30.0)), 255),
                (int) (138.0 * (life / 100.0)), (int) (255.0 * (life / 100.0)));
        if (life == 0) {
            game.setElementAt(x, y, null);
        } else {
            life--;
        }

        switch (random.nextInt(5)) {
            case 0:
                if (game.isEmpty(x - 1, y - 1) || game.canPush(x, y, x - 1, y - 1)) {
                    game.swapElements(x, y, x - 1, y - 1);
                }
                break;
            case 1:
                if (game.isEmpty(x, y - 1) || game.canPush(x, y, x, y - 1)) {
                    game.swapElements(x, y, x, y - 1);
                }
                break;
            case 2:
                if (game.isEmpty(x + 1, y - 1) || game.canPush(x, y, x + 1, y - 1)) {
                    game.swapElements(x, y, x + 1, y - 1);
                }
                break;
            case 3:
                if (game.isEmpty(x - 1, y) || game.canPush(x, y, x - 1, y)) {
                    game.swapElements(x, y, x - 1, y);
                }
                break;
            case 4:
                if (game.isEmpty(x + 1, y) || game.canPush(x, y, x + 1, y)) {
                    game.swapElements(x, y, x + 1, y);
                }
                break;
        }
        
        if (random.nextInt(2) == 0) {
            if (game.getElementAt(x - 1, y - 1)instanceof Goop ||
                game.getElementAt(x, y - 1)instanceof Goop ||
                game.getElementAt(x + 1, y - 1)instanceof Goop ||
                game.getElementAt(x - 1, y)instanceof Goop ||
                game.getElementAt(x, y)instanceof Goop ||
                game.getElementAt(x + 1, y)instanceof Goop) {
                game.setElementAt(x, y, null);
            }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}