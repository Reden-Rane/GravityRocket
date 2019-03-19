package fr.insa.gravityrocket;

public abstract class CollisionBox
{

    /**
     * @param otherCollisionBox Une autre boite avec laquelle on fait le test de collision
     *
     * @return Vrai si les boites entrent en collision
     */
    public abstract boolean collidesWith(CollisionBox otherCollisionBox);

}
