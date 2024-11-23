package thePowderToyJava;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyListenerHandler implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

        if (focusOwner instanceof JTextComponent) {
            return false;
        }

        if (e.getID() == KeyEvent.KEY_PRESSED) {
            int keycode = e.getKeyCode();
            String keyText = KeyEvent.getKeyText(keycode).toUpperCase();

            FallingSandGame.keyPressed = keyText;
            Debug.print(keyText);
            switch (keyText) {
                case "P":
                    FallingSandGame.replaceMode = !FallingSandGame.replaceMode;
                    break;
                case "L":
                    FallingSandGame.debugMode = !FallingSandGame.debugMode;
                    break;
                case "M":
                    FallingSandGame.tempType = !FallingSandGame.tempType;
                    break;
                case "O":
                    FallingSandGame.displayMode++;
                    break;
                case "SPACE":
                    FallingSandGame.paused = !FallingSandGame.paused;
                    break;
                /*case "EQUALS":
                    FallingSandGame.hudsize++;
                    break;
                case "MINUS":
                    FallingSandGame.hudsize--;
                    break;*/
                case "H":
                    JOptionPane.showMessageDialog(null,
                            "Hotkeys:\n" +
                                    "P - Toggle Replace Mode\n" +
                                    "M - Toggle Imperial/Metric\n" +
                                    "O - Change Render Mode\n" +
                                    "L - Toggle Debug Mode\n" +
                                    "Space - Toggle Pause\n" +
                                    "W/A/S/D - Control Stickman\n\n" +
                                    "Help:\n" +
                                    "- Cell size alters the size of cells, not the simulation\n" +
                                    "- Delay does not require a power source\n\n" +
                                    "Coming Soon:\n" +
                                    "- More languages (maybe)\n"+
                                    "- More electrical components, such as Piston, Radio, and\n  Liquid Crystal\n" +
                                    "- Copy tool and paste tool\n\n" +
                                    "- Undo (only 5 undos due to memory consumption)\n"+
                                    "Bugs/Suggestions:\n" +
                                    "- Simulation size is known to bug out when loading a           \n  simulation (fixed?)\n"+
                                    "- Old save versions break in new versions (fixing)\n"+
                                    "- Spark tool breaks sometimes (confused)\n"+
                                    "- DM me on Discord at aaccbb80 or on TPT at aaccbb",
                            "Hotkeys/Help/Coming Soon/Bugs/Suggestions",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:

                    break;
            }
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            FallingSandGame.keyPressed = null;
        }

        return false;
    }
}