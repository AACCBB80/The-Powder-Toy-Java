package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Ice extends Element {
    private Color customColor = new Color(201, 243, 255);
    private static final Random random = new Random();

    @Override
    public void update(FallingSandGame game, int x, int y) {
        Random random = new Random();
        /*if (game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            if ((random.nextInt(50) == 1)) {
                game.setElementAt(x, y, new Gravel());
            }
        }*/


        if (game.getHeatAt(x, y) > 31) {
            game.setElementAt(x,y,new Water());
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}