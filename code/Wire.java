package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Wire extends Element {
     private int life;

    boolean isPowered = false;

    public Wire() {
        this.life = 5;
    }
    @Override
    public void update(FallingSandGame game, int x, int y) {
    	
    	Random random = new Random();
        //if (!(random.nextInt(3) == 1)) {
        //return;}
        if (life != 0) {
            life--;
        } else {
            life = 5;
            return;
        }

        if (isPowered) {
            game.addHeatAt(x, y, 15);
            game.setElementAt(x,y,new PWire());
        }


        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Wire) {
                    if (!((Wire) neighbor).isPowered) {
                        ((Wire) neighbor).transferPower(isPowered);
                    }
                }
            }
        }
    }

    public void receiveElectricity() {
            isPowered = true;
    }

    public void transferPower(boolean powered) {
        if (powered) {
            isPowered = true;
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {

        if (isPowered) {
            g.setColor(new Color(169, 169, 230));
        } else {
            g.setColor(Color.DARK_GRAY);
        }
        g.fillRect(x, y, width, height);
    }
}