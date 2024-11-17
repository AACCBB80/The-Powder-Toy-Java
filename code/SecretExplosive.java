package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class SecretExplosive extends Element {
    private static final Random random = new Random();

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        }
        int rndInt = 0;
        while (rndInt == 0) {
            rndInt = random.nextInt(3) - 1;
        }

        if (game.isEmpty(x + rndInt, y) || game.canPush(x, y, x + rndInt, y)) {
            game.swapElements(x, y, x + rndInt, y);
        }
        rndInt = 0;
        while (rndInt == 0) {
            rndInt = random.nextInt(5) - 3;
        }
        if (game.isEmpty(x + rndInt, y + 1) || game.canPush(x, y, x + rndInt, y + 1)) {
            game.swapElements(x, y, x + rndInt, y + 1);
        }

        if (game.getElementAt(x + 1, y - 1) instanceof Plasma || 
            game.getElementAt(x, y - 1) instanceof Plasma || 
            game.getElementAt(x - 1, y - 1) instanceof Plasma || 
            game.getElementAt(x + 1, y) instanceof Plasma || 
            game.getElementAt(x, y) instanceof Plasma || 
            game.getElementAt(x - 1, y) instanceof Plasma || 
            game.getElementAt(x + 1, y + 1) instanceof Plasma || 
            game.getElementAt(x, y + 1) instanceof Plasma || 
            game.getElementAt(x - 1, y + 1) instanceof Plasma ||
            game.getElementAt(x + 1, y - 1) instanceof Fire || 
            game.getElementAt(x, y - 1) instanceof Fire || 
            game.getElementAt(x - 1, y - 1) instanceof Fire || 
            game.getElementAt(x + 1, y) instanceof Fire || 
            game.getElementAt(x, y) instanceof Fire || 
            game.getElementAt(x - 1, y) instanceof Fire || 
            game.getElementAt(x + 1, y + 1) instanceof Fire || 
            game.getElementAt(x, y + 1) instanceof Fire || 
            game.getElementAt(x - 1, y + 1) instanceof Fire) {

            if (random.nextInt(2) == 0) {
                int temp1 = random.nextInt(7) - 3;
                int temp2 = random.nextInt(7) - 3;
                if (game.isEmpty(x+temp1, y+temp2)) {
                game.setElementAt(x + temp1, y + temp2, new SecretExplosive());}
                game.addPressureAt(x + temp1, y + temp2, 20000);
                game.addHeatAt(x+temp1, y+temp2, 500);
            }

            if (game.getHeatAt(x, y) > 700) {
                game.setElementAt(x, y, new Plasma());
            } else {
                game.setElementAt(x, y, new Fire());
            }
            game.addPressureAt(x, y, 20000);
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(0, 0, 0);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}