package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Flesh extends Element {
    private Color customColor = new Color(204, 116, 102);

    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (game.getHeatAt(x, y) > 250) {
            customColor = new Color(125, 79, 52); // permanant cooking
        }


           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}