package thePowderToyJava;

import java.awt.*;

public class Heat_Conductor extends Element {
    @Override
    public void update(FallingSandGame game, int x, int y) {
        double highestHeat = 0;
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {

                if (dx == 0 && dy == 0) continue;

                Element neighbor = game.getElementAt(x + dx, y + dy);
                if (neighbor instanceof Heat_Conductor) {
                    if ((game.getHeatAt(x+dx,y+dy)) > highestHeat) {
                        highestHeat = game.getHeatAt(x+dx,y+dy);
                    }
                }
            }
        }
        game.setHeatAt(x,y,highestHeat);
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        Color customColor = new Color(135, 51, 23);
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}