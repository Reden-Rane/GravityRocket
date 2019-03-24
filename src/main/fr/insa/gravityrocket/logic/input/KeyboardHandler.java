package fr.insa.gravityrocket.logic.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe permettant de gérer les entrées claviers, notamment pour palier au problème de Swing qui ne permet pas de
 * gérer la pression simultannée de deux touches du clavier.
 */
public class KeyboardHandler extends KeyAdapter
{

    /**
     * Le nombre de touches du clavier que Swing peut gérer (Cf. KeyEvent)
     */
    private static final int KEY_COUNT = 65490;

    /**
     * Liste des touches du claviers pressées
     */
    private final boolean[] pressedKeys = new boolean[KEY_COUNT];

    /**
     * @param keyCode La touche pour laquelle on veut savoir si elle est pressée ou non
     *
     * @return Vrai si la touche correspondant à l'identifiant en paramètre est pressée
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < KEY_COUNT) {
            return pressedKeys[keyCode];
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < KEY_COUNT) {
            pressedKeys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < KEY_COUNT) {
            pressedKeys[e.getKeyCode()] = false;
        }
    }

}
