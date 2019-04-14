package fr.insa.gravityrocket.logic;

import fr.insa.gravityrocket.logic.level.*;

public class GravityRocketModel
{

    public static final Level1 LEVEL_1 = new Level1();
    public static final Level2 LEVEL_2 = new Level2();
    public static final Level3 LEVEL_3 = new Level3();
    public static final Level4 LEVEL_4 = new Level4();

    private Level   currentLevel;
    private boolean paused = true;

    public void setPaused(boolean paused) {
        this.paused = paused;

        if (paused) {
            this.currentLevel.stopAllSounds();
        }
    }

    public void update(double dt) {
        if (getCurrentLevel() != null && !paused) {
            getCurrentLevel().update(dt);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void startLevel(Level level) {
        this.currentLevel = level;
        this.currentLevel.resetLevel();
        setPaused(false);
    }

}
