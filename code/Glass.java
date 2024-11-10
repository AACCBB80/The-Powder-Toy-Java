package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Glass extends Element {
    private Color customColor = new Color(67, 67, 67);

    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (game.getHeatAt(x, y) > 1000) {
            customColor = new Color(189, 120, 53);
            if ((random.nextInt(4) == 0)) {
                if (game.isEmpty(x, y + 1)) {
                    game.swapElements(x, y, x, y + 1);
                }
                int rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(3) - 1;
                }

                if (game.isEmpty(x + rndInt, y + 0)) {
                    game.swapElements(x, y, x + rndInt, y + 0);
                }
                rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(5) - 3;
                }
                if (game.isEmpty(x + rndInt, y + 1)) {
                    game.swapElements(x, y, x + rndInt, y + 1);
                }
            }
        } else {
            customColor = new Color(67, 67, 67);
            if (game.getPressureAt(x, y) > 7) {
                game.setElementAt(x, y, new Broken_Glass());
            }
        }


           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}