package fr.insa.gravityrocket.logic.collision;

/**
 * Boite de collision circulaire
 */
public class CircularCollisionBox extends CollisionBox
{

    private final double centerX;
    private final double centerY;
    private final double radius;

    public CircularCollisionBox(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public boolean collidesWith(CollisionBox otherCollisionBox) {

        if (otherCollisionBox instanceof RectangularCollisionBox) {
            return collidesWithRectangular((RectangularCollisionBox) otherCollisionBox);
        } else if (otherCollisionBox instanceof CircularCollisionBox) {
            return collidesWithCircular((CircularCollisionBox) otherCollisionBox);
        } else {

            return false;
        }
    }

    private boolean collidesWithRectangular(RectangularCollisionBox r) {
        return r.collidesWithCircular(this);
    }

    public boolean collidesWithCircular(CircularCollisionBox c) {
        double interCenterDistance = Math.sqrt((centerX - c.centerX) * (centerX - c.centerX) + (centerY - c.centerY) * (centerY - c.centerY));
        return interCenterDistance <= this.radius + c.radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

}
