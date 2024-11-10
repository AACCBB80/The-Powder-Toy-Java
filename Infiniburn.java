package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Infiniburn extends Element {
    private static final Random random = new Random();
    @Override
    public void update(FallingSandGame game, int x, int y) {
        
        if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            // loop through and set air fire
            if (!(random.nextInt(2) == 0)) {
                int temp1 = random.nextInt(3) - 1;
                int temp2 = random.nextInt(3) - 1;
                if (!(temp1 == 0 | temp2 == 0)) {
                    if (game.isEmpty(x+temp1, y+temp2)) {
                        if (game.getHeatAt(x,y) > 200) {
                game.setElementAt(x+temp1, y+temp2, new Plasma());
                } else {
                game.setElementAt(x+temp1, y+temp2, new Fire());
                }
                    }
                        
                }

            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(32, 200, 32);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}