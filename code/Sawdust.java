package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Sawdust extends Element {
    double airRes = 1;
    public Sawdust() {
        weight = 2;
    }
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        airRes = airRes + 1;
        if (game.isEmpty(x, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                Random random = new Random();
                rndInt = random.nextInt(3) - 1;
            }
            game.swapElements(x, y, x+rndInt, y+(int)Math.floor(airRes % 4 % 3%2));
        } else if (game.isEmpty(x-1, y + 1) || game.isEmpty(x+1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                Random random = new Random();
                rndInt = random.nextInt(3) - 1;
            }

            game.swapElements(x, y, x+rndInt, y + (int)Math.floor(airRes % 4 % 3%2));
        }
        Random random = new Random();
    if ((random.nextInt(60) == 1)) {
        	if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                    |game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                game.setElementAt(x, y, new Fire());
        	}}
            }
            

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(255, 255, 189);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}