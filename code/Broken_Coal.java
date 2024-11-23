package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Broken_Coal extends Element {

    public Broken_Coal() {
        weight = 0;
    }

    private Color customColor = new Color(100, 100, 106);

    @Override


    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        } else if ((game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1)) || (game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1))) {
            int rndInt = 0;
            while (rndInt == 0) {
                rndInt = random.nextInt(3) - 1;
            }
            game.swapElements(x, y, x+rndInt, y + 1);
       }

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
                int temp1 = random.nextInt(3)-1;
                int temp2 = random.nextInt(3)-1;
                if (game.isEmpty(x+temp1, y+temp2) || game.getElementAt(x+temp1, y+temp2) instanceof Broken_Coal) {
                    if (game.getHeatAt(x,y) > 700) {
                game.setNeighbors(x+temp1, y+temp2,new Plasma(), true);
                } else {
                game.setNeighbors(x+temp1, y+temp2,new Fire(), true);
                }}
                

                //game.setElementAt(temp1, temp2, new Fire());

                //}
            }



        }


    }


    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}