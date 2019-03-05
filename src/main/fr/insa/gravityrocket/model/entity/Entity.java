package fr.insa.gravityrocket.model.entity;

import java.awt.*;

public abstract class Entity
{
    private double posX;
    private double posY;
    private double rotation;
    private double velocityX;
    private double velocityY;
    private double velocityRotation;
    private double mass;

    public Entity(double mass)
    {
        this(0, 0, 0, mass);
    }

    public Entity(double posX, double posY, double rotation, double mass)
    {
        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
        this.mass = mass;
    }

    public void update(double dt)
    {
        this.posX += this.velocityX * dt;
        this.posY += this.velocityY * dt;
        this.rotation += this.velocityRotation * dt;
    }

    /**
     * Calcule la norme de la force gravitationnelle entre l'entité actuelle et une autre
     *
     * @param entity L'autre entité a l'origine de la force gravitationnelle
     * @return Retourne la norme de la force gravitationnelle
     */
    public double gravitationalForce(Entity entity)
    {
        //Contante gravitationnelle
        double G = 6.67e-11;
        double squaredDistance = squaredDistance(entity);

        if (squaredDistance == 0)
        {
            return 0;
        }

        //Formule de Newton de la force gravitationnelle
        return G * (entity.mass * this.mass) / squaredDistance;
    }

    /**
     * @param entity L'autre entité depuis laquelle on veut la ditance
     * @return Retourne le carré de la distance entre l'entité courante et une autre entité
     */
    public double squaredDistance(Entity entity)
    {
        return (entity.posX - this.posX) * (entity.posY - this.posY) + (entity.posY - this.posY) * (entity.posY - this.posY);
    }

    public double distance(Entity entity)
    {
        return Math.sqrt(squaredDistance(entity));
    }

    /**
     * Fait le rendu de l'entité à l'écran
     *
     * @param g L'objet Graphics permettant de réaliser le rendu
     */
    public abstract void render(Graphics g);

    public double getPosX()
    {
        return posX;
    }

    public void setPosX(double posX)
    {
        this.posX = posX;
    }

    public double getPosY()
    {
        return posY;
    }

    public void setPosY(double posY)
    {
        this.posY = posY;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    public double getVelocityX()
    {
        return velocityX;
    }

    public void setVelocityX(double velocityX)
    {
        this.velocityX = velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public void setVelocityY(double velocityY)
    {
        this.velocityY = velocityY;
    }

    public double getVelocityRotation()
    {
        return velocityRotation;
    }

    public void setVelocityRotation(double velocityRotation)
    {
        this.velocityRotation = velocityRotation;
    }

    public double getMass()
    {
        return mass;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }
}