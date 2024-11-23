package thePowderToyJava;

import java.awt.*;

public class Bread extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(217, 168, 41); // permanant cooking
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}