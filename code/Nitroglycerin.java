package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Nitroglycerin extends Element {
    private static final Random random = new Random();
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        if (game.isEmpty(x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        }
        int rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(3) - 1;
        }

        if (game.isEmpty(x+rndInt, y + 0)) {
            game.swapElements(x, y, x+rndInt, y + 0);
        }
        rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(5) - 3;
        }
        if (game.isEmpty(x+rndInt, y + 1)) {
            game.swapElements(x, y, x+rndInt, y + 1);
        }
        

        if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                |
                game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            // loop through and set air fire
            if ((random.nextInt(3) == 0)) {
                int temp1 = random.nextInt(7) - 3;
                int temp2 = random.nextInt(7) - 3;
                    game.setElementAt(x+temp1, y+temp2, new Nitroglycerin());
                game.addPressureAt(x+temp1, y+temp2, 20000);


            }
            if (game.getHeatAt(x,y) > 700) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
            game.addPressureAt(x, y, 20000);

        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(32, 200, 32);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}