package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Seed extends Element {
    public Seed() {
        weight = 1;
    }
    private Color customColor = new Color(121, 237, 123);

    @Override
    public void update(FallingSandGame game, int x, int y) {
        if (!game.isRunning()) return;

        Random random = new Random();

        if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
            game.swapElements(x, y, x, y + 1);
        } else if (game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1) || 
                   game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) {
            int rndInt = 0;
            while (rndInt == 0) {
                rndInt = random.nextInt(3) - 1;
            }
            if (game.isEmpty(x + rndInt, y + 1) || game.canPush(x, y, x + rndInt, y + 1)) {
                game.swapElements(x, y, x + rndInt, y + 1);
            }
        }

        if (
            game.getElementAt(x, y+1) instanceof Dirt) {

                if (random.nextInt(2) == 0) {
                    for (int tmp=0; tmp<10; tmp++) {
                        game.setElementAt(x, y-tmp, new Wood());
                    }
                    for (int tmp1=0; tmp1<30; tmp1++) {
                        int temp1 = random.nextInt(7) - 3;
                        int temp2 = random.nextInt(7) - 3;
                        if (game.isEmpty(x+temp1, y+temp2-7)) {
                            game.setElementAt(x+temp1, y+temp2-7, new Plant());
                        }
                    }

                    for (int tmp1=0; tmp1<20; tmp1++) {
                        int temp1 = random.nextInt(9) - 4;
                        int temp2 = random.nextInt(9) - 4;
                        if (game.isEmpty(x+temp1, y+temp2-7)) {
                            game.setElementAt(x+temp1, y+temp2-7, new Plant());
                        }
                    }

                    for (int tmp1=0; tmp1<5; tmp1++) {
                        int temp1 = random.nextInt(11) - 5;
                        int temp2 = random.nextInt(11) - 5;
                        if (game.isEmpty(x+temp1, y+temp2-7)) {
                            game.setElementAt(x+temp1, y+temp2-7, new Plant());
                        }
                    }
                }else{

for (int tmp=0; tmp<10; tmp++) {
                    game.setElementAt(x, y-tmp, new Wood());
                }
                for (int tmp1=0; tmp1<30; tmp1++) {
                    int temp1 = random.nextInt(9) - 4;
                    int temp2 = random.nextInt(7) - 3;
                    if (game.isEmpty(x+temp1, y+temp2-7)) {
                        game.setElementAt(x+temp1, y+temp2-7, new BetterPlant());
                    }
                }

                for (int tmp1=0; tmp1<20; tmp1++) {
                    int temp1 = random.nextInt(11) - 5;
                    int temp2 = random.nextInt(9) - 4;
                    if (game.isEmpty(x+temp1, y+temp2-7)) {
                        game.setElementAt(x+temp1, y+temp2-7, new BetterPlant());
                    }
                }

                for (int tmp1=0; tmp1<5; tmp1++) {
                    int temp1 = random.nextInt(13) - 6;
                    int temp2 = random.nextInt(11) - 5;
                    if (game.isEmpty(x+temp1, y+temp2-7)) {
                        game.setElementAt(x+temp1, y+temp2-7, new BetterPlant());
                    }
                }



                }

                //game.setElementAt(x, y, new Fire());
            } else if (!(game.isEmpty(x+1, y - 1)  && 
            game.isEmpty(x, y - 1)  &&
            game.isEmpty(x-1, y - 1)  && 
            game.isEmpty(x+1, y )  &&
            game.isEmpty(x-1, y )  && 
            game.isEmpty(x+1, y + 1)  && 
            game.isEmpty(x, y + 1)  && 
            game.isEmpty(x-1, y + 1) )) {

                game.setElementAt(x,y,null);

            }

    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(customColor);
        g.fillRect(x, y, width, height);
    }
}