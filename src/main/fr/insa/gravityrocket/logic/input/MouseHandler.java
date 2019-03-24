package fr.insa.gravityrocket.logic.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe permettant de gérer les entrées souris, notamment pour palier au problème de Swing qui ne permet pas de gérer
 * la pression simultannée de deux boutons de la souris.
 */
public class MouseHandler extends MouseAdapter
{

    /**
     * Le nombre de boutons que Swing peut gérer (Cf. MouseEvent)
     */
    private static final int BUTTON_COUNT = 4;

    /**
     * Liste des boutons de souris pressés
     */
    private final boolean[] pressedButtons = new boolean[BUTTON_COUNT];

    /**
     * @param buttonCode Le bouton pour lequel on veut savoir si il est pressé ou non
     *
     * @return Vrai si le bouton correspondant à l'identifiant en paramètre est pressée
     */
    public boolean isButtonPressed(int buttonCode) {
        if (buttonCode >= 0 && buttonCode < BUTTON_COUNT) {
            return pressedButtons[buttonCode];
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() >= 0 && e.getButton() < BUTTON_COUNT) {
            pressedButtons[e.getButton()] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() >= 0 && e.getButton() < BUTTON_COUNT) {
            pressedButtons[e.getButton()] = false;
        }
    }

}
