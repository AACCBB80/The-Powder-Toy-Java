package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Uranium extends Element {
    private Color customColor = new Color(17, 102, 0);
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

        if (random.nextInt(120) == 1) {
            game.addHeatAt(x,y,20);
            switch (random.nextInt(8)) {
            case 0:
                if (game.isEmpty(x + 1, y - 1)) {
                    game.setElementAt(x - 1, y - 1, new Neutron());
                }
                break;
            case 1:
            if (game.isEmpty(x + 0, y - 1)) {
                game.setElementAt(x + 0, y - 1, new Neutron());
            }
                break;
            
            case 2:
            if (game.isEmpty(x + 1, y - 1)) {
                game.setElementAt(x + 1, y - 1, new Neutron());
            }
            break;
            

            case 3:
            if (game.isEmpty(x - 1, y)) {
                game.setElementAt(x - 1, y + 0, new Neutron());
            }break;
            
            case 4:
            if (game.isEmpty(x + 0, y)) {
                game.setElementAt(x + 0, y + 0, new Neutron());
            }break;
            
            case 5:
            if (game.isEmpty(x + 1, y)) {
                game.setElementAt(x + 1, y + 0, new Neutron());
            }break;
            

            case 6:
            if (game.isEmpty(x - 1, y + 1)) {
                game.setElementAt(x - 1, y + 1, new Neutron());
            }break;
            
            case 7:
            if (game.isEmpty(x + 0, y + 1)) {
                game.setElementAt(x + 0, y + 1, new Neutron());
            }break;
            
            case 8:
            if (game.isEmpty(x + 1, y + 1)) {
                game.setElementAt(x + 1, y + 1, new Neutron());
            }break;
            }
        }

    }


    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}