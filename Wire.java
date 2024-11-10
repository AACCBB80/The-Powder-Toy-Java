package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Wire extends Element {
    private boolean isPowered = false;
    private static final int POWER_DURATION = 20;
    private int powerTimer = 0;

    @Override
    public void update(FallingSandGame game, int x, int y) {
    	
    	Random random = new Random();
        //if (!(random.nextInt(3) == 1)) {
        //return;}
        if (powerTimer > 0) {
            powerTimer--;
        } else {
            isPowered = false;
        }


        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Wire) {
                    if (((Wire) neighbor).isPowered == false) {
                        ((Wire) neighbor).transferPower(isPowered);
                    }
                }
            }
        }
    }

    public void receiveElectricity() {
        isPowered = true;
        powerTimer = POWER_DURATION;
    }

    public void transferPower(boolean powered) {
        if (powered) {
            isPowered = true;
            powerTimer = POWER_DURATION;
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