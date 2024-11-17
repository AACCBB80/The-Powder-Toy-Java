package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Abobium extends Element {
    public Abobium() {
        weight = 69420;
    }
    private Color customColor = new Color(17, 102, 0);

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;
        Random random = new Random();

        customColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        if (1 == 1 && random.nextInt(5) == 0) {
            for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                if (dx == 0 && dy == 0) continue;
                //Debug.print(Element.elementClasses.size());
                //Element element = Element.getElementById(random.nextInt(Element.elementClasses.size()+1));
                //game.setElementAt(x + dx, y + dy, game.createElementInstance(element.getSimpleName()));
                int randomIndex = random.nextInt(Element.elementClasses.size()+1);
Class<? extends Element> elementClass = Element.getElementById(randomIndex);
if (elementClass != null) {
    Element elementInstance = game.createElementInstance(elementClass.getSimpleName());
    if (elementInstance != null && !(elementClass.getSimpleName().equals("Abobium")) && !(elementClass.getSimpleName().equals("Virus"))) {
        game.setElementAt(x + dx, y + dy, elementInstance);
    } else {
    }
} else {
}

            }
        }
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}