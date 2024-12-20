package thePowderToyJava;

import java.awt.*;

public class Void extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

Element elementAbove = game.getElementAt(x, y - 1);

            if (!(elementAbove instanceof Void || elementAbove instanceof Abobium)) {
        if (!game.isEmpty(x, y - 1)) {

        	game.setElementAt(x, y - 1, null);
        }}
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}