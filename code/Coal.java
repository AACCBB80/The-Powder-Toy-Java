package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Coal extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (!(random.nextInt(60) == 1)) {
        return;
        }

        if (game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire | 	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
        ) {
            if ((random.nextInt(5) == 1)) {
            	if (game.getHeatAt(x,y) > 700) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
            } else {
            	// loop through and set air fire
                //int temp1 = random.nextInt(3)-1;
               // int temp2 = random.nextInt(3)-1;
                //if (game.isEmpty(temp1, temp2)) {

                if (game.getHeatAt(x,y) > 700) {
                game.setNeighbors(x,y,new Plasma(), true);
                } else {
                game.setNeighbors(x,y,new Fire(), true);
                }

                    //game.setElementAt(temp1, temp2, new Fire());

                //}
            }
        	
        	
                
        }

        if (game.getPressureAt(x, y) > 15) {
            game.setElementAt(x, y, new Broken_Coal());
        }
           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
    	Color customColor = new Color(32,32,38);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}