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

            switch (keyText) {
                case "P":
                    FallingSandGame.replaceMode = !FallingSandGame.replaceMode;
                    break;
                case "L":
                    FallingSandGame.debugMode = !FallingSandGame.debugMode;
                    break;
                case "H":
                    JOptionPane.showMessageDialog(null,
                            "Hotkeys:\n" +
                                    "P - Toggle Replace Mode\n" +
                                    "L - Toggle Debug Mode\n\n" +
                                    "Help/FAQ:\n" +
                                    "- Cell size alters the size of cells, not the simulation\n\n" +
                                    "Coming Soon:\n" +
                                    "- Save browser instead of having to type in save name\n\n" +
                                    "Bugs/Suggestions:\n" +
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