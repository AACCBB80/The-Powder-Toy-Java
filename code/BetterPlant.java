package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class BetterPlant extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (!(random.nextInt(60) == 1)) {
        return;
        }

        if (game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
        	if (game.getHeatAt(x,y) > 700) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
        }

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Carbon_Dioxide) {
                    game.setElementAt(x+dx,y+dy,new Oxygen());
                }
            }
        }



        //if (game.getHeatAt(x, y) > 150) {
        //    game.setElementAt(x, y, new Fire());
       // }
           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
    	Color customColor = new Color(245, 130, 229);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}