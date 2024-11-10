package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Wood extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (!(random.nextInt(60) == 1)) {
        return;
        }

        if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
        	if (game.getHeatAt(x,y) > 700) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
        }

        if (game.getPressureAt(x, y) > 15) {
            game.setElementAt(x, y, new Sawdust());
        }

        //if (game.getHeatAt(x, y) > 150) {
        //    game.setElementAt(x, y, new Fire());
       // }
           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
    	Color customColor = new Color(171, 125, 0);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}