package thePowderToyJava; 
import java.awt. * ;
import java.util.Objects;
import java.util.Random;

public class Stickman extends Element {
  double updateRes = 1;
  @Override
  public void update(FallingSandGame game, int x, int y) {
    updateRes++;
    String aboba = game.keyPressed;
    Random random = new Random();
    if (game.isEmpty(x, y + 1)) {
      if (updateRes % 8 == 0) {
        game.swapElements(x, y, x, y + 1);
      }
    }
    if (Objects.equals(aboba, "W") && game.isEmpty(x, y - 7)) {
      if (updateRes % 2 == 0 || game.isEmpty(x, y + 1)) {
        return;
      }
      game.swapElements(x, y, x, y - 7);
    } else if (Objects.equals(aboba, "A") && game.isEmpty(x - 1, y)) {
      game.swapElements(x, y, x - 1, y);
    } else if (Objects.equals(aboba, "S") && game.isEmpty(x, y + 1)) {
      game.swapElements(x, y, x, y + 1);
    } else if (Objects.equals(aboba, "D") && game.isEmpty(x + 1, y)) {
      if (updateRes % 2 == 0) {
        return;
      }
      game.swapElements(x, y, x + 1, y);
    }
    if (game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
            if (random.nextInt(3) == 1)
            game.setElementAt(x, y, null);
        }
  }
  
  @Override public void draw(Graphics g, int x, int y, int width, int height) {
    g.setColor(Color.WHITE);
    g.fillRect(x - (2 * FallingSandGame.SIZE), y, width, height);
    g.fillRect(x - (1 * FallingSandGame.SIZE), y - (1 * FallingSandGame.SIZE), width, height);
    g.fillRect(x + (1 * FallingSandGame.SIZE), y - (1 * FallingSandGame.SIZE), width, height);
    g.fillRect(x, y - (2 * FallingSandGame.SIZE), width, height);
    g.fillRect(x, y - (3 * FallingSandGame.SIZE), width, height);
    g.fillRect(x, y - (4 * FallingSandGame.SIZE), width, height);
    g.fillRect(x + (1 * FallingSandGame.SIZE), y - (4 * FallingSandGame.SIZE), width, height);
    g.fillRect(x - (1 * FallingSandGame.SIZE), y - (4 * FallingSandGame.SIZE), width, height);
    g.fillRect(x + (1 * FallingSandGame.SIZE), y - (5 * FallingSandGame.SIZE), width, height);
    g.fillRect(x - (1 * FallingSandGame.SIZE), y - (5 * FallingSandGame.SIZE), width, height);
    g.fillRect(x + (1 * FallingSandGame.SIZE), y - (6 * FallingSandGame.SIZE), width, height);
    g.fillRect(x - (1 * FallingSandGame.SIZE), y - (6 * FallingSandGame.SIZE), width, height);
    g.fillRect(x, y - (6 * FallingSandGame.SIZE), width, height);
    g.fillRect(x + (2 * FallingSandGame.SIZE), y, width, height);
  }
}