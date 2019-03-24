package fr.insa.gravityrocket.logic.collision;

public class CircularCollisionBox extends CollisionBox
{

    private final double radius;

    public CircularCollisionBox(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean collidesWith(CollisionBox otherCollisionBox) {

        if (otherCollisionBox instanceof RectangularCollisionBox) {

        } else if (otherCollisionBox instanceof CircularCollisionBox) {

        }

        return false;
    }

}
