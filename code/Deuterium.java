package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Deuterium extends Element {
    private static final Random random = new Random();
    public Deuterium() {
        weight = 6;
    }
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        if (game.isEmpty(x, y + 1) || game.canPush(x,y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        }
        int rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(3) - 1;
        }

        if (game.isEmpty(x+rndInt, y + 0) || game.canPush(x,y,x+rndInt, y + 0) ) {
            game.swapElements(x, y, x+rndInt, y + 0);
        }
        rndInt = 0;
        while (rndInt == 0) {
            Random random = new Random();
            rndInt = random.nextInt(5) - 3;
        }
        if (game.isEmpty(x+rndInt, y + 1) || game.canPush(x,y,x+rndInt, y + 1) ) {
            game.swapElements(x, y, x+rndInt, y + 1);
        }
        

        if (game.getElementAt(x+1, y - 1) instanceof Neutron | game.getElementAt(x+0, y - 1) instanceof Neutron | game.getElementAt(x-1, y - 1) instanceof Neutron | game.getElementAt(x+1, y ) instanceof Neutron |game.getElementAt(x+0, y ) instanceof Neutron | game.getElementAt(x-1, y ) instanceof Neutron | game.getElementAt(x+1, y + 1) instanceof Neutron | game.getElementAt(x+0, y + 1) instanceof Neutron | game.getElementAt(x-1, y + 1) instanceof Neutron) {
            if (!(random.nextInt(5) == 0)) {
                int temp1 = random.nextInt(7) - 3;
                int temp2 = random.nextInt(7) - 3;
                if (game.isEmpty(x+temp1, y+temp2)) {
                game.setElementAt(x+temp1, y+temp2, new Neutron());}
                game.addPressureAt(x+temp1, y+temp2, 80000);
                game.addHeatAt(x+temp1, y+temp2, 500);

            } else {
                game.setElementAt(x, y, new Neutron());
                game.addPressureAt(x, y, 80000);
                game.addHeatAt(x, y, 10);
            }
        }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(80, 67, 125);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}