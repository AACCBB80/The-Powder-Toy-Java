package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Gas extends Element {

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();
        if (!(random.nextInt(2) == 1)) {
            return;
        }

        //System.out.println(255.0 * (life / 100.0) + " " + life / 100.0);
        if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            // loop through and set air fire
            if ((random.nextInt(4) == 0)) {
                for (int tmp=0; tmp<3; tmp++) {
                    int temp1 = random.nextInt(7) - 3;
                    int temp2 = random.nextInt(7) - 3;
                    game.setElementAt(x+temp1, y+temp2, new Gas());
                    game.addPressureAt(x+temp1, y+temp2, 15000);
                }

            }
            if (game.getHeatAt(x,y) > 200) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
            game.addPressureAt(x, y, 15000);
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
        g.setColor(new Color(62, 99, 58));
        g.fillRect(x, y, width, height);
    }
}