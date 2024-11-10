package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Stone extends Element {
    private Color customColor = new Color(153,153,153);
    private static final Random random = new Random();

    @Override
    public void update(FallingSandGame game, int x, int y) {
        Random random = new Random();
        /*if (game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            if ((random.nextInt(50) == 1)) {
                game.setElementAt(x, y, new Gravel());
            }
        }*/


        if (game.getHeatAt(x, y) > 500) {
            customColor = new Color(227, 136, 66);
            if ((random.nextInt(4) == 0)) {
                if (game.isEmpty(x, y + 1)) {
                    game.swapElements(x, y, x, y + 1);
                }
                int rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(3) - 1;
                }

                if (game.isEmpty(x + rndInt, y + 0)) {
                    game.swapElements(x, y, x + rndInt, y + 0);
                }
                rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(5) - 3;
                }
                if (game.isEmpty(x + rndInt, y + 1)) {
                    game.swapElements(x, y, x + rndInt, y + 1);
                }
            }
        } else {
            customColor = new Color(153,153,153);
            if (game.getPressureAt(x, y) > 20) {
                game.setElementAt(x, y, new Gravel());
            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}