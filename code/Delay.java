package thePowderToyJava;

import java.awt.*;

public class Delay extends Element {
    private int timer = 0;
    private final int delayDuration = 100;

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (game.getElementAt(x, y + 1) instanceof Wire) {

                    //System.out.println(timer);
            if (timer >= delayDuration) {
                                    //System.out.println("test");
                    ((Wire) game.getElementAt(x, y + 1) ).receiveElectricity();
                    //System.out.println("........"+i);
                   timer = 0; 
                   


            } else {
                timer++;
            }
        } else {
            timer = 0;
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.orange);
        g.fillRect(x, y, width, height);
    }
}