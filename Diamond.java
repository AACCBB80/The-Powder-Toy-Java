package thePowderToyJava;

import java.awt.*;

public class Diamond extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(225, 225, 255);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}