package thePowderToyJava;

import java.awt.*;

public class PWire extends Element {
    private int life;

    public PWire() {
        this.life = 2;
    }

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;

        if (life == 0) {
            game.setElementAt(x, y, new Wire());
        } else {
            life--;
        }

        
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(new Color(145, 153, 171));
        g.fillRect(x, y, width, height);
    }
}