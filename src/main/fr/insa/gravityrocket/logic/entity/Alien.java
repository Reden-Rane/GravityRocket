package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.level.Level;

public class Alien extends Entity
{

    public Alien(Level level) {
        this(level, 0, 0, 0);
    }

    public Alien(Level level, double posX, double posY, double rotation) {
        super(level, posX, posY, 16, 16, rotation);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        //TODO
    }

    @Override
    public double getMass() {
        //TODO
        return 0;
    }

}