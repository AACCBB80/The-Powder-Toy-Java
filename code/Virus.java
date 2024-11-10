package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Virus extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (!(random.nextInt(3) == 1)) {
        return;
        }

        switch (random.nextInt(8)) {
            case 0:
                if (!game.isEmpty(x - 1, y - 1)) {
                    game.setElementAt(x - 1, y - 1, new Virus());
                }
                break;
            case 1:
            if (!game.isEmpty(x + 0, y - 1)) {
                game.setElementAt(x + 0, y - 1, new Virus());
                break;
            }
            case 2:
            if (!game.isEmpty(x + 1, y - 1)) {
                game.setElementAt(x + 1, y - 1, new Virus());
                break;
            }

            case 3:
            if (!game.isEmpty(x - 1, y)) {
                game.setElementAt(x - 1, y + 0, new Virus());
                break;
            }
            case 4:
            if (!game.isEmpty(x + 0, y)) {
                game.setElementAt(x + 0, y + 0, new Virus());
                break;
            }
            case 5:
            if (!game.isEmpty(x + 1, y)) {
                game.setElementAt(x + 1, y + 0, new Virus());
                break;
            }

            case 6:
            if (!game.isEmpty(x - 1, y + 1)) {
                game.setElementAt(x - 1, y + 1, new Virus());
                break;
            }
            case 7:
            if (!game.isEmpty(x + 0, y + 1)) {
                game.setElementAt(x + 0, y + 1, new Virus());
                break;
            }
            case 8:
            if (!game.isEmpty(x + 1, y + 1)) {
                game.setElementAt(x + 1, y + 1, new Virus());
                break;
            }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.PINK);
        g.fillRect(x, y, width, height);
    }
}