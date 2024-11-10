package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Broken_Glass extends Element {
    private Color customColor = new Color(77, 77, 77);
    @Override


    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();

        if (game.isEmpty(x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        } else if (game.isEmpty(x-1, y + 1) || game.isEmpty(x+1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                rndInt = random.nextInt(3) - 1;
            }
            game.swapElements(x, y, x+rndInt, y + 1);
        }

        if (game.getHeatAt(x, y) > 1000) {
            game.setElementAt(x,y,new Glass());
        } else {
            customColor = new Color(77, 77, 77);
        }

    }


    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}