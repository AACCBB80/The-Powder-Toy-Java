package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Cake_Batter extends Element {
    private static final Random random = new Random();
        private Color customColor = new Color(247, 205, 96);
        private boolean cooked;


    public Cake_Batter() {
        this.cooked = false;
        weight = 7;
    }

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!cooked){
            if (!(random.nextInt(4) == 1)) {
                return;
            }
            
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
            if (game.getHeatAt(x, y) > 225) {
                game.setElementAt(x,y,new Cake());
            }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}