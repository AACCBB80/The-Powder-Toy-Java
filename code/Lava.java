package thePowderToyJava;

import java.awt.*;
import java.util.Random;

public class Lava extends Element {

    private int life;

    // PROPERTIES //

        // IMPORTANT: you have to register element in class FallingSandGame marked by comment that says "ELEMENT INIT"

        // random update frequency of particle. 1 to update every frame, >= 2 to update at roughly every X frames; ex.: Water is 1, Oxygen is 2
        int RANDOM_UPDATE_FREQUENCY = 1;

        // state of element. SOLID/LIQUID/POWDER/GAS are valid states; ex.: Wood is SOLID, Oxygen is GAS
        String TYPE = "LIQUID";

        // liquid viscosity. only works when TYPE is LIQUID; ex.: Water is 1, Goop is 4
        int LIQUID_VISCOSITY = 6;

        // air resistance of particle. -1 is none; only works if TYPE is POWDER; ex.: -1 for Broken_Coal, 0.5 for dust, 1 for sawdust
        double AIR_RESISTANCE = 1;

        // gas buoyancy. FLOAT/NEUTRAL/SINK are valid states; only works if TYPE is GAS; ex.: -1 for Broken_Coal, 0.5 for dust, 1 for sawdust
        String GAS_BUOYANCY = "NEUTRAL";

        // how flammable the particle is. NONE/LOW/MEDIUM/HIGH/EXPLOSIVELOW/EXPLOSIVEHIGH are valid states; ex.: Water is NONE, Nitroglycerin is EXPLOSIVEHIGH, Wood is MEDIUM
        String FLAMMABILITY = "NONE";

        // what color the element should be in R, G, B format; ex.: 171, 125, 0 for Wood, 80, 67, 125 for Deuterium
        Color ELEMENT_COLOR = new Color(218, 144, 58);

        // pressure at which particle breaks. -1 is unbreakable; >= 0 is breakable at X pressure; ex.: Wood at 15, Stone at 20
        int BREAK_PRESSURE = -1;

        // what the particle turns into when it's broken. not required when BREAK_PRESSURE = -1; ex.: Wood to Sawdust, Stone to Gravel
        Element brokenElement = null;

        // temperature at which particle melts/evaporates. -1 is unmeltable; >= 0 is meltable at X temperature; ex.: Water at 200
        int MELT_TEMPERATURE = -1;

        // what the particle turns into when it's melted/evaporated. not required when MELT_TEMPERATURE = -1; ex.: Water_Vapor from Water
        Element meltedElement = null;

        // temperature at which particle freezes. -1 is unfreezable; >= 0 is freezable at X temperature; ex.: Water at 20
        int FREEZE_TEMPERATURE = 590;

        // what the particle turns into when it's frozen. not required when FREEZE_TEMPERATURE = -1; ex.: Ice from Water
        Element frozenElement = new Stone();

        // starting air resistance of particle. only works if TYPE is POWDER; ex.: 1 for dust, 1 for sawdust
        double airRes = 1;

        public Lava() {

            // starting life of particle in frames. -1 does not despawn; >= 0 lasts X frames; ex.: 100 for fire, 50 for smoke
            this.life = -1;

            // weight of particle. -1 for solids and >= 1 for regular weights; ex.: 1 for gas, 2 for dust, 4 for water
            weight = 10;
        
        }
    
    // PROPERTIES //


    // WARNING!
    // SPOOKY CODE AHEAD! ONLY FOR PEOPLE WHO CAN ABOBA.

    @Override
    public void update(FallingSandGame game, int x, int y) {

        if (!game.isRunning()) return;

        Random random = new Random();
        if (!(random.nextInt(RANDOM_UPDATE_FREQUENCY) == 0)) {
        return;
        }

        if (life !=-1) {
        if (life == 0) {
            game.setElementAt(x, y, null);
        } else {
            life--;
        }}
        
        int rndInt = 0;

        switch (TYPE) {
            case "SOLID":
                break;
            case "LIQUID":
                if ((random.nextInt(LIQUID_VISCOSITY) == 0)) {
                    break;
                }

                if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
                    game.swapElements(x, y, x, y + 1);
                }
                rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(3) - 1;
                }

                if (game.isEmpty(x + rndInt, y)) {
                    game.swapElements(x, y, x + rndInt, y);
                }
                rndInt = 0;
                while (rndInt == 0) {
                    rndInt = random.nextInt(5) - 3;
                }
                if (game.isEmpty(x + rndInt, y + 1) || game.canPush(x, y, x + rndInt, y + 1)) {
                    game.swapElements(x, y, x + rndInt, y + 1);
                }
                break;
            case "POWDER":
                if (AIR_RESISTANCE == -1) {
                    if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
                        game.swapElements(x, y, x, y + 1);
                    } else if ((game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1)) || (game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1))) {
                        rndInt = 0;
                        while (rndInt == 0) {
                            rndInt = random.nextInt(3) - 1;
                        }
                        game.swapElements(x, y, x + rndInt, y + 1);
                    }
                    game.swapElements(x, y, x + rndInt, y + 1);
                } else {
                    airRes = airRes + 1;
                    if (game.isEmpty(x, y + 1)) {
                        rndInt = 0;
                        while (rndInt == 0) {
                            rndInt = random.nextInt(3) - 1;
                        }
                        game.swapElements(x, y, x + rndInt, y + (int) Math.floor(airRes % 4 % 3 % 2));
                    } else if (game.isEmpty(x - 1, y + 1) || game.isEmpty(x + 1, y + 1)) {
                        rndInt = 0;
                        while (rndInt == 0) {
                            rndInt = random.nextInt(3) - 1;
                        }

                        game.swapElements(x, y, x + rndInt, y + (int) Math.floor(airRes % 4 % 3 % 2));
                    }
                }
                break;
            case "GAS":
                switch (GAS_BUOYANCY) {
                    case "NEUTRAL":
                        switch (random.nextInt(8)) {
                            case 0:
                                if (game.isEmpty(x - 1, y - 1) || game.canPush(x, y, x - 1, y - 1)) {
                                    game.swapElements(x, y, x - 1, y - 1);
                                }
                                break;
                            case 1:
                                if (game.isEmpty(x, y - 1) || game.canPush(x, y, x, y - 1)) {
                                    game.swapElements(x, y, x, y - 1);
                                }
                                break;
                            case 2:
                                if (game.isEmpty(x + 1, y - 1) || game.canPush(x, y, x + 1, y - 1)) {
                                    game.swapElements(x, y, x + 1, y - 1);
                                }
                                break;
                            case 3:
                                if (game.isEmpty(x - 1, y) || game.canPush(x, y, x - 1, y)) {
                                    game.swapElements(x, y, x - 1, y);
                                }
                                break;
                            case 4:
                                if (game.isEmpty(x, y) || game.canPush(x, y, x, y)) {
                                    game.swapElements(x, y, x, y);
                                }
                                break;
                            case 5:
                                if (game.isEmpty(x + 1, y) || game.canPush(x, y, x + 1, y)) {
                                    game.swapElements(x, y, x + 1, y);
                                }
                                break;
                            case 6:
                                if (game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1)) {
                                    game.swapElements(x, y, x - 1, y + 1);
                                }
                                break;
                            case 7:
                                if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
                                    game.swapElements(x, y, x, y + 1);
                                }
                                break;
                            case 8:
                                if (game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) {
                                    game.swapElements(x, y, x + 1, y + 1);
                                }
                                break;
                        }
                        break;
                    case "FLOAT":
                        switch (random.nextInt(5)) {
                            case 0:
                                if (game.isEmpty(x - 1, y - 1) || game.canPush(x, y, x - 1, y - 1)) {
                                    game.swapElements(x, y, x - 1, y - 1);
                                }
                                break;
                            case 1:
                                if (game.isEmpty(x, y - 1) || game.canPush(x, y, x, y - 1)) {
                                    game.swapElements(x, y, x, y - 1);
                                }
                                break;
                            case 2:
                                if (game.isEmpty(x + 1, y - 1) || game.canPush(x, y, x + 1, y - 1)) {
                                    game.swapElements(x, y, x + 1, y - 1);
                                }
                                break;
                            case 3:
                                if (game.isEmpty(x - 1, y) || game.canPush(x, y, x - 1, y)) {
                                    game.swapElements(x, y, x - 1, y);
                                }
                                break;
                            case 4:
                                if (game.isEmpty(x + 1, y) || game.canPush(x, y, x + 1, y)) {
                                    game.swapElements(x, y, x + 1, y);
                                }
                                break;
                        }
                        break;
                    case "SINK":
                        switch (random.nextInt(5)) {
                            case 0:
                                if (game.isEmpty(x - 1, y + 1) || game.canPush(x, y, x - 1, y + 1)) {
                                    game.swapElements(x, y, x - 1, y + 1);
                                }
                                break;
                            case 1:
                                if (game.isEmpty(x, y + 1) || game.canPush(x, y, x, y + 1)) {
                                    game.swapElements(x, y, x, y + 1);
                                }
                                break;
                            case 2:
                                if (game.isEmpty(x + 1, y + 1) || game.canPush(x, y, x + 1, y + 1)) {
                                    game.swapElements(x, y, x + 1, y + 1);
                                }
                                break;
                            case 3:
                                if (game.isEmpty(x - 1, y) || game.canPush(x, y, x - 1, y)) {
                                    game.swapElements(x, y, x - 1, y);
                                }
                                break;
                            case 4:
                                if (game.isEmpty(x + 1, y) || game.canPush(x, y, x + 1, y)) {
                                    game.swapElements(x, y, x + 1, y);
                                }
                                break;
                        }
                        break;
                }
                break;
        }

        switch (FLAMMABILITY) {
            case "NONE":
                break;
            case "LOW":
                if (!(random.nextInt(750) == 1)) {
                    return;
                }
                if (game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                        | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                    if (game.getHeatAt(x,y) > 700) {
                        game.setElementAt(x, y, new Plasma());
                    } else {
                        game.setElementAt(x, y, new Fire());
                    }
                }
                break;
            case "MEDIUM":
                if (!(random.nextInt(60) == 1)) {
                    return;
                }
                if (game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                        | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                    if (game.getHeatAt(x,y) > 700) {
                        game.setElementAt(x, y, new Plasma());
                    } else {
                        game.setElementAt(x, y, new Fire());
                    }
                }
                break;
            case "HIGH":
                if (!(random.nextInt(5) == 1)) {
                    return;
                }
                if (game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                        | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                    if (game.getHeatAt(x,y) > 700) {
                        game.setElementAt(x, y, new Plasma());
                    } else {
                        game.setElementAt(x, y, new Fire());
                    }
                }
                break;
            case "EXPLOSIVELOW":
                if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                        |
                        game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                    // loop through and set air fire
                    if ((random.nextInt(12) == 0)) {
                        int temp1 = random.nextInt(7) - 3;
                        int temp2 = random.nextInt(7) - 3;
                        game.setElementAt(x+temp1, y+temp2, new SecretExplosive());
                        game.addPressureAt(x+temp1, y+temp2, 20000);


                    }}
                break;
            case "EXPLOSIVEHIGH":
                if (	game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                        |
                        game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
                    // loop through and set air fire
                    if ((random.nextInt(3) == 0)) {
                        int temp1 = random.nextInt(7) - 3;
                        int temp2 = random.nextInt(7) - 3;
                        if (game.isEmpty(x+temp1, y+temp2)) {
                        game.setElementAt(x+temp1, y+temp2, new SecretExplosive());
                        game.addPressureAt(x+temp1, y+temp2, 20000);}


                    }}
                break;
        }

        if ((random.nextInt(12) == 0)) {
            int temp1 = random.nextInt(7) - 3;
            int temp2 = random.nextInt(7) - 3;
            if (game.isEmpty(x+temp1, y+temp2) && temp1 != 0 && temp2 != 0) {
                if (game.getHeatAt(x,y) > 700) {
                    //game.setElementAt(x + temp1, y + temp2, new Plasma());
                } else {
                    //game.setElementAt(x + temp1, y + temp2, new Fire());
                }}
            }


        if (MELT_TEMPERATURE != -1) {
            if (game.getHeatAt(x, y) >= MELT_TEMPERATURE) {
                game.setElementAt(x,y,new Stone());
            }}

        if (FREEZE_TEMPERATURE != -1) {
            if (game.getHeatAt(x, y) <= FREEZE_TEMPERATURE) {
                game.setElementAt(x,y,frozenElement);
            }}


        /*if (game.getElementAt(x+1, y - 1) instanceof Plasma | game.getElementAt(x+0, y - 1) instanceof Plasma | game.getElementAt(x-1, y - 1) instanceof Plasma | game.getElementAt(x+1, y ) instanceof Plasma |game.getElementAt(x+0, y ) instanceof Plasma | game.getElementAt(x-1, y ) instanceof Plasma | game.getElementAt(x+1, y + 1) instanceof Plasma | game.getElementAt(x+0, y + 1) instanceof Plasma | game.getElementAt(x-1, y + 1) instanceof Plasma
                | game.getElementAt(x+1, y - 1) instanceof Fire | game.getElementAt(x+0, y - 1) instanceof Fire | game.getElementAt(x-1, y - 1) instanceof Fire | game.getElementAt(x+1, y ) instanceof Fire |game.getElementAt(x+0, y ) instanceof Fire | game.getElementAt(x-1, y ) instanceof Fire | game.getElementAt(x+1, y + 1) instanceof Fire | game.getElementAt(x+0, y + 1) instanceof Fire | game.getElementAt(x-1, y + 1) instanceof Fire) {
        	if (game.getHeatAt(x,y) > 700) {
                game.setElementAt(x, y, new Plasma());
                } else {
                game.setElementAt(x, y, new Fire());
                }
        }*/
        if (BREAK_PRESSURE != -1) {
        if (game.getPressureAt(x, y) > BREAK_PRESSURE) {
            game.setElementAt(x, y, brokenElement);
        }}

        //if (game.getHeatAt(x, y) > 150) {
        //    game.setElementAt(x, y, new Fire());
       // }
           
    }

    @Override
    public void draw(Graphics g, int x, int y, int width, int height) {
        g.setColor(ELEMENT_COLOR);
        g.fillRect(x, y, width, height);
    }
}