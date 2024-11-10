package thePowderToyJava;

import java.awt.*;

public class Battery extends Element {

    private int timer = 0;
    private final int delayDuration = 19;
    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (timer >= delayDuration) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    Element neighbor = game.getElementAt(x + dx, y + dy);
                    if (neighbor instanceof Wire) {
                        ((Wire) neighbor).receiveElectricity();
                    }
                }
            }
        } else {
            timer++;
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(81, 173, 0));
        g.fillRect(x, y, width, height);
    }
}
