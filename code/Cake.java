package thePowderToyJava;

import java.awt.*;

public class Cake extends Element {
    private Color customColor = new Color(255, 245, 173);
    @Override
    public void update(FallingSandGame game, int x, int y) {



    	if (game.isEmpty(x,y-1)) {
            customColor = new Color(173, 213, 255);
        } else {
            customColor = new Color(255, 245, 173);
        }
    	
    	if (game.isEmpty(x,y-2)) {
            customColor = new Color(173, 213, 255);
        } else {
            customColor = new Color(255, 245, 173);
        }}

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        //Color customColor = new Color(217, 168, 41); // permanant cooking
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}