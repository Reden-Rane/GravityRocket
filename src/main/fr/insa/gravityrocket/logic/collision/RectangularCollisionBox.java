package fr.insa.gravityrocket.logic.collision;

public class RectangularCollisionBox extends CollisionBox
{
    private final double width;
    private final double height;

    public RectangularCollisionBox(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean collidesWith(CollisionBox otherCollisionBox) {

        if (otherCollisionBox instanceof RectangularCollisionBox) {

        } else if (otherCollisionBox instanceof CircularCollisionBox) {

        }

        return false;
    }

}
