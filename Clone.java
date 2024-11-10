package thePowderToyJava;

import java.awt.*;

public class Clone extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;
        if (!game.isEmpty(x, y - 1)) {
            Element elementAbove = game.getElementAt(x, y - 1);

            if (!(elementAbove instanceof Clone)) {
                if (game.isEmpty(x, y + 1)) {
                    try {
                        Element clonedElement = elementAbove.getClass().getDeclaredConstructor().newInstance();
                        game.setElementAt(x, y + 1, clonedElement);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(57, 179, 106));
        g.fillRect(x, y, width, height);
    }
}